package com.insider.test.API;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseTestAPI {
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }
}