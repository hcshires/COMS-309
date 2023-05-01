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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


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


}
