package com.cs309.tutorial.tests;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

@RestController
public class TestController {

	private TestData putDeleteData = new TestData();
	
	@GetMapping("/getTest")
	public String getTest(@RequestParam(value = "username", defaultValue = "World") String message) {
		return String.format("Hello, %s! You sent a get request with a parameter!", message);
	}
	
	@PostMapping("/postTest1")
	public String postTest1(@RequestParam(value = "username", defaultValue = "World") String message) {
		//TODO
		return String.format("Hello, %s! You sent a post request with a parameter!", message);
	}
	
	@PostMapping("/postTest2")
	public String postTest2(@RequestBody TestData testData) {
		String tempString = String.format("Hello, %s! You sent a post request with a requestbody containing ID %d!", testData.getName(), testData.getReqID());
		tempString += "\nYour array of integers is: " + Arrays.toString(testData.getIntArray());
		return tempString;
	}
	
	@DeleteMapping("/deleteTest")
	public String deleteTest() {
		putDeleteData = null;
		return "Deleted data.";
	}
	
	@PutMapping("/putTest")
	public String putTest(@RequestBody TestData putData) {
		putDeleteData = putData;
		return "---- Put data ----\n" + putData.toString();
	}

	//Use a RequestBody instead of a parameter for better security
	//@ResponseBody indicates that the return object should be serialized into JSON for the HTTP Response
	@GetMapping("/getData")
	@ResponseBody
	public TestData getData(@RequestBody AccessLevel privilege) {
		//Check for empty object
		if (putDeleteData == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attempted to return null object data");
		}

		//Only return if we are an admin request it
		if (privilege.isAdmin() && putDeleteData != null) {
			return putDeleteData;
		}
		else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Insufficient user access level for resource");
		}
	}
}
