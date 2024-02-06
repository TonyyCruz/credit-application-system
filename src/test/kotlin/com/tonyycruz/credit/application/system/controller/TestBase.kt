package com.tonyycruz.credit.application.system.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.tonyycruz.credit.application.system.mocks.MockEntities
import com.tonyycruz.credit.application.system.repository.CreditRepository
import com.tonyycruz.credit.application.system.repository.CustomerRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
class TestBase : MockEntities() {

    @Autowired
    protected lateinit var customerRepository: CustomerRepository

    @Autowired
    protected lateinit var creditRepository: CreditRepository

    @Autowired
    protected lateinit var mockMvc: MockMvc

    @Autowired
    protected lateinit var objectMapper: ObjectMapper

    companion object {
        const val CUSTOMER_URL: String = "/api/customers"
        const val CREDIT_URL: String = "/api/credits"
    }

    @BeforeEach
    fun setup() = customerRepository.deleteAll()

    @AfterEach
    fun tearDown() = customerRepository.deleteAll()
}