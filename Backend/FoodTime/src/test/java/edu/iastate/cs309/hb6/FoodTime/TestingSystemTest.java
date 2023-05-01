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
                param("UID", "6bc95713-067d-4f72-ab2b-0180f759daad").
                param("day", "monday").
                body(mealBody.toString()).
                when().
                put("/meals/add");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);
    }

    @Test
    public void updateMealTest() throws JSONException {
        JsonObject mealBody = Json.createObjectBuilder()
                .add("name", "Quesadillas")
                .add("ingredients", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                                .add("name", "Beans")
                                .add("quantity", "4000")
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

        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                param("UID", "6bc95713-067d-4f72-ab2b-0180f759daad").
                param("day", "monday").
                param("mealName", "Quesadillas").
                body(mealBody.toString()).
                when().
                put("/meals/update");

        String responseBody = response.getBody().asString();
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);
        assertNotEquals(responseBody, "");
    }

    @Test
    public void getByDayTest() {
        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                param("UID", "6bc95713-067d-4f72-ab2b-0180f759daad").
                param("day", "monday").
                when().
                get("/meals/get/by-day");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        assertNotNull(returnString);
        assertNotEquals(returnString, "");
    }

    @Test
    public void getAllForUserTest() {
        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                param("UID", "6bc95713-067d-4f72-ab2b-0180f759daad").
                when().
                get("/meals/get/all");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);
        String returnString = response.getBody().asString();
        assertNotNull(returnString);
        assertNotEquals(returnString, "");
    }

    @Test
    public void removeMealTest() {
        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                param("UID", "6bc95713-067d-4f72-ab2b-0180f759daad").
                param("day", "monday").
                param("mealName", "Quesadillas").
                param("removeAll", "false").
                when().
                delete("/meals/remove");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);
    }

    @Test
    public void addToPantryTest() {
        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                param("UID", "6bc95713-067d-4f72-ab2b-0180f759daad").
                param("ingredientName", "Cereal").
                param("quantity", 1).
                param("unitsType", "box").
                when().
                put("/pantry/addToPantry");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);
        String returnString = response.getBody().asString();
        assertNotNull(returnString);
        assertNotEquals(returnString, "");
    }

    @Test
    public void removeFromPantryTest() {
        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                param("UID", "6bc95713-067d-4f72-ab2b-0180f759daad").
                param("ingredientName", "Cereal").
                when().
                delete("/pantry/removeFromPantry");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);
    }
}
