package com.example.routes

import com.example.model.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.SerializationException

fun Route.customerRouting() {
    route("/customer") {


        /**
         * Get all the customer data
         */

        get {
            if (customerList.isNotEmpty()) {
                call.respond(
                    status = HttpStatusCode.OK,
                    ResponseClass(1, "Successfully Data fetched!", customerList.size, customerList)
                )
            } else {
                call.respond(
                    status = HttpStatusCode.OK,
                    ResponseClass(0, "No Customer found!", customerList.size, customerList),
                )
            }
        }

        /**
         * get customer id according to ID
         */

        get("{id?}") {
            val id = call.parameters["id"]?.toInt() ?: return@get call.respond(
                status = HttpStatusCode.BadRequest,
                ResponseClass(0, "Missing parameter!", 0, emptyList())
            )
            val customer = customerList.find { it.id == id }
            if (customer != null) {
                call.respond(
                    HttpStatusCode.OK,
                    ResponseClass(1, "Successfully Data fetched!", listOf(customer).size, listOf(customer))
                )
            } else {
                call.respond(
                    status = HttpStatusCode.NotFound,
                    ResponseClass(0, "No customer found with this id $id", 0, emptyList())
                )
            }

        }

        /**
         * Using post method for pass data to server and get data
         */
        post {
            try {
                val customer = call.receive<Customer>()

                val validationResult = customer.validate()

                if (validationResult != null) {
                    call.respond(
                        status = HttpStatusCode.BadRequest,
                        PostResponse(status = 0, validationResult.toString().replace("[", "").replace("]", "").replace(", ", ", "))
                    )
                } else if (customer.id in idSet) {
                    call.respond(
                        status = HttpStatusCode.BadRequest,
                        PostResponse(status = 0, "Customer with ID ${customer.id} already exists.")
                    )
                } else {
                    customerList.add(customer)
                    idSet.add(customer.id)
                    call.respond(status = HttpStatusCode.Created, PostResponse(status = 1, "Customer created successfully"))
                }
            } catch (e: SerializationException) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    PostResponse(status = 0, "Invalid request data format")
                )
            }

        }


        /**
         *  Delete Data using Id
         */
        delete("{id?}") {
            val id = call.parameters["id"]?.toInt() ?: return@delete call.respond(
                status = HttpStatusCode.BadRequest,
                ResponseClass(0, "Missing parameter!", 0, emptyList())
            )
            if (customerList.removeIf({it.id==id})) {
                call.respond(
                    HttpStatusCode.OK,
                    PostResponse(1, "Customer removed successfully!")
                )
            } else {
                call.respond(
                    status = HttpStatusCode.NotFound,
                    ResponseClass(0, "No customer found with this id $id", 0, emptyList())
                )
            }
        }
    }
}