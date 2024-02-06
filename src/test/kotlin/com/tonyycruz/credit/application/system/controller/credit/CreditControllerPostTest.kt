package com.tonyycruz.credit.application.system.controller.credit

import com.tonyycruz.credit.application.system.controller.TestBase
import com.tonyycruz.credit.application.system.dto.request.CreditDto
import com.tonyycruz.credit.application.system.dto.request.CustomerDto
import com.tonyycruz.credit.application.system.entity.Customer
import com.tonyycruz.credit.application.system.enums.Status
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.math.BigDecimal
import java.time.LocalDate

class CreditControllerPostTest: TestBase() {

    @Test
    fun `Should create a credit and receive status code 201`() {
        val customer: Customer = customerRepository.save(fakeCustomer())
        val creditDto: CreditDto = creditToDto(fakeCredit(customer = customer))
        val valueAsString: String = objectMapper.writeValueAsString(creditDto)
        mockMvc.perform(MockMvcRequestBuilders
            .post(CREDIT_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.creditCode").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.creditValue").value(creditDto.creditValue))
            .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfInstallments").value(creditDto.numberOfInstallments))
            .andExpect(MockMvcResultMatchers.jsonPath("$.dayFirstInstallment").value(creditDto.dayFirstInstallment.toString()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("IN_PROGRESS"))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Should not create a credit with the first payment on a past date and receive status code 400`() {
        val customer: Customer = customerRepository.save(fakeCustomer())
        val pastDate = LocalDate.now().minusDays(1)
        val creditDto: CreditDto = creditToDto(fakeCredit(customer = customer, dayFirstInstallment = pastDate))
        val valueAsString: String = objectMapper.writeValueAsString(creditDto)
        mockMvc.perform(MockMvcRequestBuilders
            .post(CREDIT_URL)
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
                .value("The first installment must be a future date")
            )
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Should not create a credit with the first payment greater than three months and receive status code 400`() {
        val customer: Customer = customerRepository.save(fakeCustomer())
        val firstInstallment = LocalDate.now().plusMonths(4)
        val creditDto: CreditDto = creditToDto(fakeCredit(customer = customer, dayFirstInstallment = firstInstallment))
        val valueAsString: String = objectMapper.writeValueAsString(creditDto)
        mockMvc.perform(MockMvcRequestBuilders
            .post(CREDIT_URL)
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
                .value("The first installment cannot exceed three months.")
            )
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Should not create a credit with number of installments less than one and receive status code 400`() {
        val customer: Customer = customerRepository.save(fakeCustomer())
        val installments = -1
        val creditDto: CreditDto = creditToDto(fakeCredit(customer = customer, numberOfInstallments = installments))
        val valueAsString: String = objectMapper.writeValueAsString(creditDto)
        mockMvc.perform(MockMvcRequestBuilders
            .post(CREDIT_URL)
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
                .value("Installments must be at least 1.")
            )
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Should not create a credit with number of installments greater than forty eight and receive status code 400`() {
        val customer: Customer = customerRepository.save(fakeCustomer())
        val installments = 49
        val creditDto: CreditDto = creditToDto(fakeCredit(customer = customer, numberOfInstallments = installments))
        val valueAsString: String = objectMapper.writeValueAsString(creditDto)
        mockMvc.perform(MockMvcRequestBuilders
            .post(CREDIT_URL)
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
                .value("Installment must be up to 48.")
            )
            .andDo(MockMvcResultHandlers.print())
    }
}