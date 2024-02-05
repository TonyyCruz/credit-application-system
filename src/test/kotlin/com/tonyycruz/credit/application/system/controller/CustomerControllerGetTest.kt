package com.tonyycruz.credit.application.system.controller

import com.tonyycruz.credit.application.system.entity.Customer
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class CustomerControllerGetTest : TestBase()  {

    @Test
    fun `Should find by customer id and receive status code 200`() {
        val customer: Customer = fakeCustomer()
        customerRepository.save(customer)
        mockMvc.perform(MockMvcRequestBuilders
            .get("$URL/${customer.id}")
            .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(customer.firstName))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(customer.lastName))
            .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value(customer.cpf))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(customer.email))
            .andExpect(MockMvcResultMatchers.jsonPath("$.income").value(customer.income))
            .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value(customer.address.zipCode))
            .andExpect(MockMvcResultMatchers.jsonPath("$.street").value(customer.address.street))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(customer.id))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Should not find a customer with invalid id and receive status code 400`() {
        val invalidId: Long = 100L
        mockMvc.perform(MockMvcRequestBuilders
            .get("$URL/$invalidId")
            .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(MockMvcResultMatchers
                .jsonPath("$.exception")
                .value("class com.tonyycruz.credit.application.system.exception.NotFoundException")
            )
            .andExpect(MockMvcResultMatchers
                .jsonPath("$.title")
                .value("Bad request, the received argument is incorrect.")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andExpect(MockMvcResultMatchers
                .jsonPath("$.details[*]")
                .value("Id '$invalidId' was not found.")
            )
            .andDo(MockMvcResultHandlers.print())
    }
}