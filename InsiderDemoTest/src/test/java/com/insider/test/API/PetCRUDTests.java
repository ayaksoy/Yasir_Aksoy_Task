package com.insider.test.API;

import com.insider.test.API.models.Pet;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PetCRUDTests {

    // Test süresince kullanacağımız ortak değişkenler
    private final int petId = 19998877; // Çakışmayı önlemek için benzersiz bir ID
    private Pet payload;

    @BeforeClass
    public void setup() {
        // Base URI tanımlaması
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        // Test datasını (Payload) hazırlıyoruz - Helper metot kullanımı
        payload = createPetPayload(petId, "Sivas_Kangali", "available");
    }

    // ==========================================
    // 1. CREATE OPERATIONS (POST)
    // ==========================================

    @Test(priority = 1, description = "Positive: Create a new pet with full nested JSON data")
    public void createPetPositive() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/pet");

        // Status Code Kontrolü
        response.then().statusCode(200);

        // Deep Validation: İç içe geçmiş objelerin kontrolü
        // Hem POJO'dan gelen veriyle hem de dönen veriyle eşleşmeli
        Assert.assertEquals(response.jsonPath().getLong("id"), petId);
        Assert.assertEquals(response.jsonPath().getString("name"), "Sivas_Kangali");
        Assert.assertEquals(response.jsonPath().getString("category.name"), "Dogs");
        Assert.assertEquals(response.jsonPath().getString("tags[0].name"), "GuardDog"); // Array içi kontrol

        System.out.println("✅ Pet Created Successfully: " + petId);
    }

    @Test(priority = 2, description = "Negative: Create pet with Invalid Input (Empty Body)")
    public void createPetNegative() {
        // Body göndermeden POST atarsak sunucu ne der?
        given()
                .contentType(ContentType.JSON)
                .body("{}") // Boş veya eksik body
                .when()
                .post("/pet")
                .then()
                // Swagger dokümanı "Invalid input" için 405 diyebilir ama genelde backendler 400/500 döner.
                // Burada API'nin davranışına göre esnek davranıyoruz.
                .statusCode(anyOf(is(400), is(405), is(500)));

        System.out.println("✅ Negative Create Test Passed");
    }

    // ==========================================
    // 2. READ OPERATIONS (GET)
    // ==========================================

    @Test(priority = 3, description = "Positive: Get existing pet by ID")
    public void getPetPositive() {
        given()
                .pathParam("id", petId)
                .when()
                .get("/pet/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(petId)) // ID Matcher kullanımı
                .body("status", equalTo("available"));

        System.out.println("✅ Read Pet Successful");
    }

    @Test(priority = 4, description = "Negative: Get non-existing pet")
    public void getPetNegative() {
        long nonExistingId = 999999999; // Olmayan bir ID

        given()
                .pathParam("id", nonExistingId)
                .when()
                .get("/pet/{id}")
                .then()
                .statusCode(404) // Not Found
                .body("message", equalTo("Pet not found")); // Hata mesajı doğrulaması

        System.out.println("✅ Negative Read Test Passed (404 Confirmed)");
    }

    // ==========================================
    // 3. UPDATE OPERATIONS (PUT)
    // ==========================================

    @Test(priority = 5, description = "Positive: Update pet status to 'sold'")
    public void updatePetPositive() {
        // Var olan payload'ı güncelle
        payload.setStatus("sold");
        payload.setName("Sivas_Kangali_Satildi");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .put("/pet");

        response.then().statusCode(200);

        // Güncellemenin response body'de göründüğünü doğrula
        Assert.assertEquals(response.jsonPath().getString("status"), "sold");
        Assert.assertEquals(response.jsonPath().getString("name"), "Sivas_Kangali_Satildi");

        // Double Check: Bir de GET atıp gerçekten veritabanında değişmiş mi bakalım (Senior Refleksi)
        given()
                .pathParam("id", petId)
                .when()
                .get("/pet/{id}")
                .then()
                .body("status", equalTo("sold"));

        System.out.println("✅ Update Pet Successful");
    }
    @Test(priority = 6, description = "Negative: Update with Invalid ID Format")
    public void updatePetNegative() {
        // String ID göndererek sistemi bozmaya çalışalım (Validation Check)
        String invalidJson = "{ \"id\": \"abc\", \"name\": \"Test\" }";

        given()
                .contentType(ContentType.JSON)
                .body(invalidJson)
                .when()
                .put("/pet")
                .then()
                .statusCode(500); // Swagger genelde Java NumberFormatException fırlatıp 500 dönüyor

        System.out.println("✅ Negative Update Test Passed");
    }

    // ==========================================
    // 4. DELETE OPERATIONS (DELETE)
    // ==========================================

    @Test(priority = 7, description = "Positive: Delete the pet")
    public void deletePetPositive() {
        given()
                .pathParam("id", petId)
                .when()
                .delete("/pet/{id}")
                .then()
                .statusCode(200);

        System.out.println("✅ Pet Deleted Successfully");
    }

    @Test(priority = 8, description = "Negative: Try to delete already deleted pet")
    public void deletePetNegative() {
        // Zaten silinmiş bir şeyi tekrar silmeye çalışırsak 404 dönmeli
        given()
                .pathParam("id", petId)
                .when()
                .delete("/pet/{id}")
                .then()
                .statusCode(404);

        System.out.println("✅ Negative Delete Test Passed (Idempotency check)");
    }

    // ==========================================
    // HELPER METHODS (Clean Code)
    // ==========================================

    // Karmaşık POJO'yu oluşturmak için yardımcı metot
    private Pet createPetPayload(long id, String name, String status) {
        Pet pet = new Pet();
        pet.setId(id);
        pet.setName(name);
        pet.setStatus(status);

        // Nested Category
        Pet.Category category = new Pet.Category();
        category.setId(1);
        category.setName("Dogs");
        pet.setCategory(category);

        // Nested Tags
        Pet.Tag tag = new Pet.Tag();
        tag.setId(100);
        tag.setName("GuardDog");

        List<Pet.Tag> tags = new ArrayList<>();
        tags.add(tag);
        pet.setTags(tags);

        // Photo URLs
        List<String> photoUrls = new ArrayList<>();
        photoUrls.add("https://kangal-images.com/img1.jpg");
        pet.setPhotoUrls(photoUrls);

        return pet;
    }
}