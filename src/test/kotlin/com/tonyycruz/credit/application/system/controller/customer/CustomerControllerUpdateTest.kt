package com.tonyycruz.credit.application.system.controller.customer

import com.tonyycruz.credit.application.system.controller.TestBase
import com.tonyycruz.credit.application.system.dto.request.CustomerUpdateDto
import com.tonyycruz.credit.application.system.entity.Customer
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class CustomerControllerUpdateTest : TestBase() {

    @Test
    fun `Should update a customer by id and return status code 204`() {
        val customer: Customer = customerRepository.save(fakeCustomer())
        val customerUpdateDto: CustomerUpdateDto = customerToUpdateDto(fakeCustomer())
        val valueAsString: String = objectMapper.writeValueAsString(customerUpdateDto)
        mockMvc.perform(MockMvcRequestBuilders
            .patch("$URL/${customer.id}")
            .contentType(MediaType.APPLICATION_JSON)
            .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(customerUpdateDto.firstName))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(customerUpdateDto.lastName))
            .andExpect(MockMvcResultMatchers.jsonPath("$.income").value(customerUpdateDto.income))
            .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value(customerUpdateDto.zipCode))
            .andExpect(MockMvcResultMatchers.jsonPath("$.street").value(customerUpdateDto.street))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(customer.id))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Should not delete a customer with invalid id and return status code 400`() {
        val invalidId: Long = 100L
        mockMvc.perform(MockMvcRequestBuilders
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

    @Test
    fun `Should not update a customer with first name empty and receive status code 400`() {
        val customer: Customer = customerRepository.save(fakeCustomer())
        val customerUpdateDto: CustomerUpdateDto = customerToUpdateDto(fakeCustomer(firstName = ""))
        val valueAsString: String = objectMapper.writeValueAsString(customerUpdateDto)
        mockMvc.perform(MockMvcRequestBuilders
            .patch("$URL/${customer.id}")
            .contentType(MediaType.APPLICATION_JSON)
            .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers
                .jsonPath("$.title")
                .value("Bad request, invalid argumentation.")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(MockMvcResultMatchers
                .jsonPath("$.exception")
                .value("class org.springframework.web.bind.MethodArgumentNotValidException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andExpect(MockMvcResultMatchers
                .jsonPath("$.details[*]")
                .value("Field 'First Name' must not be empty.")
            )
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Should not update a customer with last name empty and receive status code 400`() {
        val customer: Customer = customerRepository.save(fakeCustomer())
        val customerUpdateDto: CustomerUpdateDto = customerToUpdateDto(fakeCustomer(lastName = ""))
        val valueAsString: String = objectMapper.writeValueAsString(customerUpdateDto)
        mockMvc.perform(MockMvcRequestBuilders
            .patch("$URL/${customer.id}")
            .contentType(MediaType.APPLICATION_JSON)
            .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers
                .jsonPath("$.title")
                .value("Bad request, invalid argumentation.")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(MockMvcResultMatchers
                .jsonPath("$.exception")
                .value("class org.springframework.web.bind.MethodArgumentNotValidException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andExpect(MockMvcResultMatchers
                .jsonPath("$.details[*]")
                .value("Field 'Last Name' must not be empty.")
            )
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Should not update a customer with zip code empty and receive status code 400`() {
        val customer: Customer = customerRepository.save(fakeCustomer())
        val customerUpdateDto: CustomerUpdateDto = customerToUpdateDto(fakeCustomer(zipCode = ""))
        val valueAsString: String = objectMapper.writeValueAsString(customerUpdateDto)
        mockMvc.perform(MockMvcRequestBuilders
            .patch("$URL/${customer.id}")
            .contentType(MediaType.APPLICATION_JSON)
            .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers
                .jsonPath("$.title")
                .value("Bad request, invalid argumentation.")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(MockMvcResultMatchers
                .jsonPath("$.exception")
                .value("class org.springframework.web.bind.MethodArgumentNotValidException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andExpect(MockMvcResultMatchers
                .jsonPath("$.details[*]")
                .value("Field 'Zip Code' must not be empty.")
            )
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Should not update a customer with street empty and receive status code 400`() {
        val customer: Customer = customerRepository.save(fakeCustomer())
        val customerUpdateDto: CustomerUpdateDto = customerToUpdateDto(fakeCustomer(street = ""))
        val valueAsString: String = objectMapper.writeValueAsString(customerUpdateDto)
        mockMvc.perform(MockMvcRequestBuilders
            .patch("$URL/${customer.id}")
            .contentType(MediaType.APPLICATION_JSON)
            .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers
                .jsonPath("$.title")
                .value("Bad request, invalid argumentation.")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(MockMvcResultMatchers
                .jsonPath("$.exception")
                .value("class org.springframework.web.bind.MethodArgumentNotValidException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andExpect(MockMvcResultMatchers
                .jsonPath("$.details[*]")
                .value("Field 'Street' must not be empty.")
            )
            .andDo(MockMvcResultHandlers.print())
    }
}