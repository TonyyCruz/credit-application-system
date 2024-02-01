package com.tonyycruz.credit.application.system.controller

import com.tonyycruz.credit.application.system.entity.Customer
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class CustomerControllerDeleteTest : TestBase() {

    @Test
    fun `Should delete a customer by id and return status code 204`() {
        val customer: Customer = customerRepository.save(fakeCustomer())
        mockMvc.perform(
            MockMvcRequestBuilders
            .delete("$URL/${customer.id}")
            .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isNoContent)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Should not delete a customer with invalid id and return status code 400`() {
        val invalidId: Long = 100L
        mockMvc.perform(
            MockMvcRequestBuilders
                .delete("$URL/$invalidId")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers
                .jsonPath("$.title")
                .value("Bad request, the received argument is incorrect.")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(MockMvcResultMatchers
                .jsonPath("$.exception")
                .value("class com.tonyycruz.credit.application.system.exception.NotFoundException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andExpect(MockMvcResultMatchers
                .jsonPath("$.details[*]")
                .value("Id '$invalidId' was not found.")
            )
            .andDo(MockMvcResultHandlers.print())
    }
}