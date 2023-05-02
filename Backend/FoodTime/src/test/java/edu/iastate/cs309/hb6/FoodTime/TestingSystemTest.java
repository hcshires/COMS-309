package edu.iastate.cs309.hb6.FoodTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.json.Json;
import javax.json.JsonObject;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class TestingSystemTest {
    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    //TODO so I can remember where this is
    //makes testing easier and more consistent
    String testIngredientName = "testFood1234";
    int testQuantity = 5;
    String testUnitType = "count";
    String UID = "bd66484d-ae81-492a-9457-9be427aa9998";


    @Test
    public void loginUserTest() throws JSONException {
        //JSONObject requestBody = new JSONObject()
//            requestBody.put("username", "SystemTestUser@example.com");
//            requestBody.put("password", "test");
//            requestBody.put("accessLevel", "PARENT");

        // Send request and receive response
        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                param("username", "SystemTestUser@COMS309.com").
                param("password", "root").
                //body(requestBody.toString()).
                when().
                get("/users/login");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();

//      JSONArray returnArr = new JSONArray(returnString);
//      JSONObject returnObj = returnArr.getJSONObject(returnArr.length()-1);
        //Check that the UID is not null as that is the most important part of creating a user
        assertNotNull(returnString);

    }

    @Test
    public void addMealTest() throws JSONException {
        JsonObject mealBody = Json.createObjectBuilder()
                .add("name", "Quesadillas")
                .add("ingredients", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                                .add("name", "Beans")
                                .add("quantity", "2")
                                .add ("quantityType", "cans"))
                        .add(Json.createObjectBuilder()
                                .add("name", "Black beans")
                                .add("quantity", "69")
                                .add ("quantityType", "cans"))
                        .add(Json.createObjectBuilder()
                                .add("name", "Sour cream")
                                .add("quantity", "1")
                                .add ("quantityType", "tub")))
                .build();

        System.out.println(mealBody);

        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                param("UID", UID).
                param("day", "monday").
                body(mealBody.toString()).
                when().
                put("/meals/add");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);
    }

    @Test
    public void getByDayTest() {
        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                param("UID", UID).
                param("day", "monday").
                when().
                get("/meals/get/by-day");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        assertNotNull(returnString);
    }

    @Test
    public void getAllForUserTest() {
        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                param("UID", UID).
                when().
                get("/meals/get/all");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);
    }

    @Test
    public void removeMealTest() {
        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                param("UID", UID).
                param("day", "monday").
                param("mealName", "Quesadillas").
                param("removeAll", "false").
                when().
                delete("/meals/remove");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);
    }

    //pantry and ingredient tests
    //Im going to assume these run in order

    @Test
    public void addToPantryTest(){
        //add an item to a given user's pantry
        //PutMapping(path = "/pantry/addToPantry"
        //Param String UID, Param String ingredientName, Param int quantity, Param String unitsType

        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                param("UID", UID).
                param("ingredientName", testIngredientName).
                param("quantity", testQuantity).
                param("unitsType", testUnitType).
                when().
                put("/pantry/addToPantry");

        System.out.println(response.getBody().asString());

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void getPantryTest(){
        //return a user's whole pantry
        //GetMapping(path = "/pantry/getUserPantry")
        //@RequestParam String UID

        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                param("UID", UID).
                when().
                get("/pantry/getUserPantry");

        assertTrue(response.getBody().asString().contains(testIngredientName)); //check if ingredient you just added is in the list
        assertEquals(200, response.getStatusCode()); //check if successful
    }

    @Test
    public void changeQuantityTest(){

        //PutMapping(path = "/pantry/setQuantity"
        //RequestParam String UID, @RequestParam String ingredientName, @RequestParam int quantity

        //change ingredient quantity value
        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                param("UID", UID).
                param("ingredientName", testIngredientName).
                param("quantity", 6). //!= original value
                        when().
                put("/pantry/setQuantity");

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void changeUnitTest(){

        //PutMapping(path = "/pantry/setQuantityType
        //Param String UID, @RequestParam String ingredientName, @RequestParam String quantityType

        //change unit type
        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                param("UID", UID).
                param("ingredientName", testIngredientName).
                param("quantityType", "uncountable").
                when().
                put("/pantry/setQuantityType");

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void changeQuantTest(){

        //GetMapping(path = "/pantry/getQuantity"
        //Param String UID, @RequestParam String ingredientName

        //check for new value
        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                param("UID", UID).
                param("ingredientName", testIngredientName).
                when().
                get("/pantry/getQuantity");

        assertEquals(200, response.getStatusCode());
        assertEquals(true, response.getBody().asString().contains("6"));
    }

    @Test
    public void changeUnitTypeTest(){

        //GetMapping(path = "/pantry/getQuantityType"
        //Param String UID, @RequestParam String ingredientName

        //check for new unit type
        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                param("UID", UID).
                param("ingredientName", testIngredientName).
                when().
                get("/pantry/getQuantityType");

        assertEquals(200, response.getStatusCode());
        assertEquals(true, response.getBody().asString().contains("uncountable"));
    }

    @Test
    public void removeFromPantryTest(){
        //remove items by name from user's pantry
        //Delete /pantry/removeFromPantry
        //Param String UID, Param String ingredientName

        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                param("UID", UID).
                param("ingredientName", testIngredientName).
                when().
                delete("/pantry/removeFromPantry");

        assertEquals(200, response.getStatusCode()); //note that the ingredient does actually need to be present to be removed
    }
}
