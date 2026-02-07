package com.insider.test.API;

import com.insider.test.API.models.Pet;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PetCRUDTests {


    private final int petId = 19998877;
    private Pet payload;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
        payload = createPetPayload(petId, "Sivas_Kangali", "available");
    }

    @Test(priority = 1, description = "Positive: Create a new pet with full nested JSON data")
    public void createPetPositive() {
        SoftAssert softAssert = new SoftAssert();
        Response response = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/pet");

        response.then().statusCode(200);

        softAssert.assertEquals(response.jsonPath().getLong("id"), petId);
        softAssert.assertEquals(response.jsonPath().getString("name"), "Sivas_Kangali");
        softAssert.assertEquals(response.jsonPath().getString("category.name"), "Dogs");
        softAssert.assertEquals(response.jsonPath().getString("tags[0].name"), "GuardDog");
        softAssert.assertAll();

        System.out.println("Pet Created Successfully: " + petId);
    }

    @Test(priority = 2, description = "Negative: Create pet with Invalid Input (Empty Body)")
    public void createPetNegative() {
        given()
                .contentType(ContentType.JSON)
                .body("{}")
                .when()
                .post("/pet")
                .then()
                .statusCode(anyOf(is(400), is(405), is(500)));

        System.out.println("Negative Create Test Passed");
    }

    @Test(priority = 3, description = "Positive: Get existing pet by ID")
    public void getPetPositive() {
        given()
                .pathParam("id", petId)
                .when()
                .get("/pet/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(petId))
                .body("status", equalTo("available"));

        System.out.println("Read Pet Successful");
    }

    @Test(priority = 4, description = "Negative: Get non-existing pet")
    public void getPetNegative() {
        long nonExistingId = 999999999;

        given()
                .pathParam("id", nonExistingId)
                .when()
                .get("/pet/{id}")
                .then()
                .statusCode(404)
                .body("message", equalTo("Pet not found"));

        System.out.println("Negative Read Test Passed (404 Confirmed)");
    }

    @Test(priority = 5, description = "Positive: Update pet status to 'sold'")
    public void updatePetPositive() {
        payload.setStatus("sold");
        payload.setName("Sivas_Sold");
        SoftAssert softAssert = new SoftAssert();

        Response response = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .put("/pet");

        response.then().statusCode(200);

        softAssert.assertEquals(response.jsonPath().getString("status"), "sold");
        softAssert.assertEquals(response.jsonPath().getString("name"), "Sivas_Sold");
        softAssert.assertAll();

        given()
                .pathParam("id", petId)
                .when()
                .get("/pet/{id}")
                .then()
                .body("status", equalTo("sold"));

        System.out.println("Update Pet Successful");
    }

    @Test(priority = 6, description = "Negative: Update with Invalid ID Format")
    public void updatePetNegative() {
        String invalidJson = "{ \"id\": \"abc\", \"name\": \"Test\" }";

        given()
                .contentType(ContentType.JSON)
                .body(invalidJson)
                .when()
                .put("/pet")
                .then()
                .statusCode(500);

        System.out.println("Negative Update Test Passed");
    }

    @Test(priority = 7, description = "Positive: Delete the pet")
    public void deletePetPositive() {
        given()
                .pathParam("id", petId)
                .when()
                .delete("/pet/{id}")
                .then()
                .statusCode(200);

        System.out.println("Pet Deleted Successfully");
    }

    @Test(priority = 8, description = "Negative: Try to delete already deleted pet")
    public void deletePetNegative() {
        given()
                .pathParam("id", petId)
                .when()
                .delete("/pet/{id}")
                .then()
                .statusCode(404);

        System.out.println("Negative Delete Test Passed (Idempotency check)");
    }

    private Pet createPetPayload(long id, String name, String status) {
        Pet pet = new Pet();
        pet.setId(id);
        pet.setName(name);
        pet.setStatus(status);

        Pet.Category category = new Pet.Category();
        category.setId(1);
        category.setName("Dogs");
        pet.setCategory(category);

        Pet.Tag tag = new Pet.Tag();
        tag.setId(100);
        tag.setName("GuardDog");

        List<Pet.Tag> tags = new ArrayList<>();
        tags.add(tag);
        pet.setTags(tags);

        List<String> photoUrls = new ArrayList<>();
        photoUrls.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRool17PKkXDIptJQChb0JvDrsXsSD4NEkuMQ&s");
        pet.setPhotoUrls(photoUrls);

        return pet;
    }
}