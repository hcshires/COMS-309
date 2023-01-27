package com.cs309.tutorial.tests;

public class TestData {

	/*
	When performing a POST with a RequestBody, the RequestBody data JSON should contain references to class data members
	in the object specified after the @RequestBody annotation.
	For example, to handle a POST with @RequestBody expectinggit  object TestData, you would have the following raw Body:
		{
		"name":"Sample name",
		"reqID":"12345",
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
