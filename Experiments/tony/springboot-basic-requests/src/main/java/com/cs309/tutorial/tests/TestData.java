package com.cs309.tutorial.tests;

public class TestData {

	/*
	When performing a POST with a RequestBody, the RequestBody data JSON should contain references to class data members.
	For example, to send a POST with RequestBody containing info for intArray, you would have the following raw Body:
		{
		"intArray":[1, 2, 6, 8, 10]
		}
	*/
	private String name;
	private int reqID;
	private int[] intArray;
	
	public TestData() {}

	public TestData(String message, int reqID, int[] intArray) {
		this.name = message;
		this.reqID = reqID;
		this.intArray = intArray;
	}

	public String getName() {
		return name;
	}

	public int getReqID() { return reqID; }

	public int[] getIntArray() { return intArray; }
}
