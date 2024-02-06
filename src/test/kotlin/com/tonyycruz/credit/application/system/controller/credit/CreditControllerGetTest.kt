package com.tonyycruz.credit.application.system.controller.credit

import com.tonyycruz.credit.application.system.controller.TestBase
import com.tonyycruz.credit.application.system.dto.response.CreditViewList
import com.tonyycruz.credit.application.system.entity.Customer
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.UUID

class CreditControllerGetTest: TestBase() {

    @Test
    fun `Should find all credits by customer id and receive status code 200`() {
        val customer: Customer = customerRepository.save(fakeCustomer())
        val creditView1 =  CreditViewList(creditRepository.save(fakeCredit(customer = customer)))
        val creditView2 =  CreditViewList(creditRepository.save(fakeCredit(customer = customer)))
        val creditView3 =  CreditViewList(creditRepository.save(fakeCredit(customer = customer)))
        val creditViewList: List<CreditViewList> = listOf(creditView1, creditView2, creditView3)
        val valueAsString: String = objectMapper.writeValueAsString(creditViewList)
        mockMvc.perform(MockMvcRequestBuilders
            .get("$CREDIT_URL?customerId=${customer.id}")
            .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$[*]").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$[*]").isNotEmpty)
            .andExpect(MockMvcResultMatchers.content().string(valueAsString))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Should not find credits with a invalid customer id and status code 400`() {
        val invalidId = 9998
        mockMvc.perform(MockMvcRequestBuilders
            .get("$CREDIT_URL?customerId=$invalidId")
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
    fun `Should find a credit by credit code and receive status code 200`() {
        val customer: Customer = customerRepository.save(fakeCustomer())
        val credit =  creditRepository.save(fakeCredit(customer = customer))
        mockMvc.perform(MockMvcRequestBuilders
            .get("$CREDIT_URL/${credit.creditCode}?customerId=${customer.id}")
            .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.creditCode").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.creditCode").value(credit.creditCode.toString()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.creditValue").value(credit.creditValue))
            .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfInstallments")
                .value(credit.numberOfInstallments)
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.dayFirstInstallment")
                .value(credit.dayFirstInstallment.toString())
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("IN_PROGRESS"))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Should not find credits with a invalid credit code and status code 400`() {
        val invalidCreditCode = UUID.randomUUID()
        val invalidCustomerId = 9998
        mockMvc.perform(MockMvcRequestBuilders
            .get("$CREDIT_URL/$invalidCreditCode?customerId=$invalidCustomerId")
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
                .value("Credit code: $invalidCreditCode was not found.")
            )
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Should not find credits if is not the owner and status code 400`() {
        val customer: Customer = customerRepository.save(fakeCustomer())
        val credit =  creditRepository.save(fakeCredit(customer = customer))
        val invalidCustomerId = 999888
        mockMvc.perform(MockMvcRequestBuilders
            .get("$CREDIT_URL/${credit.creditCode}?customerId=$invalidCustomerId")
            .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers
                .jsonPath("$.title")
                .value("Bad request, not authorized.")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(MockMvcResultMatchers
                .jsonPath("$.exception")
                .value("class com.tonyycruz.credit.application.system.exception.UnauthorizedException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andExpect(MockMvcResultMatchers
                .jsonPath("$.details[*]")
                .value("You do not have permission to access this credit.")
            )
            .andDo(MockMvcResultHandlers.print())
    }
}