package com.insider.test.API;

import com.insider.test.API.data.PetDataProvider;
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


    private long petId;
    private Pet payload;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
        petId = System.currentTimeMillis();
        payload = createPetPayload(petId, "Sivas_Kangali_" + petId, "available");
    }

    @Test(priority = 1, description = "Positive: Create a new pet with dynamic ID")
    public void createPetPositive() {
        SoftAssert softAssert = new SoftAssert();
        Response response = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/pet");

        response.then().statusCode(200);

        softAssert.assertEquals(response.jsonPath().getLong("id"), petId);
        softAssert.assertEquals(response.jsonPath().getString("name"), payload.getName());
        softAssert.assertAll();

        System.out.println("Pet Created Successfully with ID: " + petId);
    }

    @Test(priority = 2,
            description = "Negative: Create pet with multiple invalid inputs",
            dataProvider = "negativePetData",
            dataProviderClass = PetDataProvider.class)
    public void createPetNegative(String scenario, String invalidPayload, int expectedStatusCode) {

        System.out.println("Running Negative Scenario: " + scenario);

        given()
                .contentType(ContentType.JSON)
                .body(invalidPayload)
                .when()
                .post("/pet")
                .then()
                .statusCode(anyOf(is(expectedStatusCode), is(400), is(500), is(415),is(405)));

        System.out.println("Scenario Passed: " + scenario);
    }

    @Test(priority = 3, description = "Positive: Get existing pet by ID")
    public void getPetPositive() {
        SoftAssert softAssert = new SoftAssert();

        Response response = given()
                .pathParam("id", petId)
                .when()
                .get("/pet/{id}");

        response.then().statusCode(200);

        softAssert.assertEquals(response.jsonPath().getLong("id"), petId);
        softAssert.assertEquals(response.jsonPath().getString("status"), "available");
        softAssert.assertAll();

        System.out.println("Read Pet Successful: " + petId);
    }

    @Test(priority = 4, description = "Negative: Get non-existing pet")
    public void getPetNegative() {
        long nonExistingId = System.currentTimeMillis();

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
        payload.setName("Sivas_Sold_" + petId);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .put("/pet");

        response.then().statusCode(200);

        Assert.assertEquals(response.jsonPath().getString("status"), "sold");

        given()
                .pathParam("id", petId)
                .when()
                .get("/pet/{id}")
                .then()
                .body("status", equalTo("sold"));

        System.out.println("Update Pet Successful: " + petId);
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

        System.out.println("Pet Deleted Successfully: " + petId);
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