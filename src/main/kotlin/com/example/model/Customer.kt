package com.example.model


import kotlinx.serialization.Serializable

@Serializable
data class Customer(val id: Int, val firstName: String, val lastName: String, val email: String){
  val validArrayList: ArrayList<String> = arrayListOf()

  fun validate(): ArrayList<String>? {
    if (id <= 0) {
      validArrayList.add("ID must be greater than 0")
    }
    if (firstName.isBlank()) {
      validArrayList.add("First name cannot be blank")
    }
    if (lastName.isBlank()) {
      validArrayList.add("Last name cannot be blank")
    }
    if (email.isBlank()) {
      validArrayList.add("Email cannot be blank")
    }
    if (!email.isValidEmail()) {
      validArrayList.add("Invalid email format")
    }

    // Check if the validArrayList is empty, and return null if there are no errors.
    return if (validArrayList.isEmpty()) null else validArrayList
  }

  fun String.isValidEmail(): Boolean {
    // Implement email validation logic here (e.g., regex or other checks)
    // For simplicity, we'll just check if it contains an "@" symbol
    return "@" in this
  }
}


/**
 * This is main response class getting user response Get Method
 */
@Serializable
data class ResponseClass(val status: Int, val message: String,val length:Int, val customerList: List<Customer>? = null)

  val customerList = arrayListOf(Customer(1,"Siba","Siba","siba5@gmail.com"),Customer(2,"Anand","Silu","silu09@gmail.com")) //success case
  //val customerList = emptyList<Customer>() // failure case
  val idSet = mutableSetOf(1,2)

/**
 * This is main response for post method
 */
@Serializable
data class PostResponse(val status: Int,val message:String)
