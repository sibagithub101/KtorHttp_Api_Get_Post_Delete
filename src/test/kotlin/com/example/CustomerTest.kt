package com.example

import com.example.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*

class CustomerTest {
    @Test
    fun testCustomer() = testApplication {
        application {
            configureRouting()
        }
        val customer = client.get("/customer").apply {
            assertEquals(HttpStatusCode.OK, status)
        }

    }
}
