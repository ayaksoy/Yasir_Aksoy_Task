package com.insider.test.API.data;

import org.testng.annotations.DataProvider;

public class PetDataProvider {

    @DataProvider(name = "negativePetData")
    public static Object[][] provideNegativeData() {
        return new Object[][] {
                {"Empty Body",            "{}",                          405},
                {"Missing ID Field",      "{ \"name\": \"NoIdPet\" }",   200},
                {"Invalid ID Type",       "{ \"id\": \"abc\" }",         500},
                {"Null Body",             "",                            415}
        };
    }
}