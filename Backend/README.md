# FoodTime SpringBoot Server API - 1_HB_6

Documentation for the API endpoints for the FoodTime app backend. All tests for each endpoint can be found on the Postman Workspace.

## User Auth

Allows for user creation, login, deletion, and modification.

### Creating a user: `POST /users/create`
Send this URL a POST request along with a JSON-formatted RequestBody (see below) to create a user  
`{"username":"tonyman2", "password":"verysecure"}`  
Returns a JSON-formatted response body containing username, password, and UUID, as well as HTTP 200 or 409 if the user was successfully created or already existed, respectively.

### Authenticating: `GET /users/login`
Send this URL a GET request along with a JSON-formatted RequestBody (see below) to verify a password and return a UID as authentication (the UID should be referenced in other parts of the program to handle data pertaining to a specific user)  
`{"username":"tonyman2", "password":"verysecure"}`  
Returns a string with the UID associated with the username and HTTP 200, if the provided password and password in the database match. Else, it returns null and an HTTP 404.

### Deleting a user: `DELETE /users/delete`
Send this URL a DELETE request along with a JSON-formatted RequestBody (see below) to delete a user
`{"username":"tonyman2", "password":"verysecure"}`  
Returns a JSON-formatted User object, with username, password, and UID of the user that was removed. HTTP 200 if password is valid and user exists, HTTP 404 otherwise.  

### Updating a password: `PUT /users/reset-password`
Send this URL a PUT request along with `username` and `newPassword` RequestParams to update the password of a given user.  
`?username=tonyman2&newPassword=updatedPW`  
Returns a JSON-formatted User object with username, new password, and UID. HTTP 200 if the user exists, HTTP 404 if the user does not exist.

## User Preferences
User preferences can include things like dark mode (included at the moment), and any other options we deem necessary to be able to configure on a per-user basis.

There is only one endpoint for this feature at the moment. It provides the ability for the frontend to request to update a user's preferences. The rest (creation, deletion) are done automatically when a user creates or deletes an account.

### Endpoint: `PUT /preferences/update`

Send this URL a PUT request along with a JSON-formatted RequestBody to update preferences associated with a UID.
`{"UID":"6bcb5c7a-0054-4c90-bc56-7c87d19e6d2a", "darkMode":true}`   
The request must contain all preference options in the UserPreferences object, otherwise the request will not properly deserialize onto UserPreferences, resulting in an error.

## Pantry

### Endpoint: `GET /pantry/getUserPantry`    
`@RequestParam String userID`   
For a given RequestParam containing a UID of a user, returns a Pantry object for that user.

### Endpoint: `GET /pantry/getUserPantryString`    
`@RequestParam String userID`    
For a given RequestParam containing a UID of a user, returns a String with all items in the Pantry for that user.

### Endpoint: `PUT /pantry/addToPantry`      
`@RequestParam String userID @RequestParam String ingredient`    
For a given String containing a UID of a user and a String containing the name of an ingredient, adds an item to the Pantry for that user and returns a String of all items in the Pantry for that user.

### Endpoint: `DELETE /pantry/removeFromPantry`    
`@RequestParam String userID @RequestParam String ingredientName`    
For a given String containing a UID of a user, and String containing the name of an ingredient in the user's Pantry, deletes the item from the Pantry, and returns a String of all items still in the Pantry after the delete.
