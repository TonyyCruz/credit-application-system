package com.tonyycruz.credit.application.system.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.tonyycruz.credit.application.system.dto.request.CustomerDto
import com.tonyycruz.credit.application.system.mocks.MockEntities
import com.tonyycruz.credit.application.system.repository.CustomerRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.math.BigDecimal

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
class CustomerControllerTest : MockEntities() {
    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    companion object {
        const val URL: String = "/api/customers"
    }

    @BeforeEach
    fun setup() = customerRepository.deleteAll()

    @AfterEach
    fun tearDown() = customerRepository.deleteAll()

    @Test
    fun `Should create a customer and receive status code 201`() {
        val customerDto: CustomerDto = fakeCustomerDto()
        val valueAsString: String = objectMapper.writeValueAsString(customerDto)
        mockMvc.perform(MockMvcRequestBuilders
                .post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(customerDto.firstName))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(customerDto.lastName))
            .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value(customerDto.cpf))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(customerDto.email))
            .andExpect(MockMvcResultMatchers.jsonPath("$.income").value(customerDto.income))
            .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value(customerDto.zipCode))
            .andExpect(MockMvcResultMatchers.jsonPath("$.street").value(customerDto.street))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Should not save a customer with an used cpf and receive status code 409`() {
        val customerDto: CustomerDto = fakeCustomerDto()
        customerRepository.save(customerDto.toEntity())
        val valueAsString: String = objectMapper.writeValueAsString(customerDto)
        mockMvc.perform(MockMvcRequestBuilders
                .post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isConflict)
            .andExpect(MockMvcResultMatchers
                    .jsonPath("$.title")
                    .value("Conflicting data, received data already exists in the database.")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(409))
            .andExpect(MockMvcResultMatchers
                    .jsonPath("$.exception")
                    .value("class org.springframework.dao.DataIntegrityViolationException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Should not save a customer with first name empty and receive status code 400`() {
        val customerDto: CustomerDto = customerToDto(fakeCustomer(firstName = ""))
        val valueAsString: String = objectMapper.writeValueAsString(customerDto)
        mockMvc.perform(MockMvcRequestBuilders
                .post(URL)
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
            .andExpect(
                MockMvcResultMatchers
                    .jsonPath("$.exception")
                    .value("class org.springframework.web.bind.MethodArgumentNotValidException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andExpect(MockMvcResultMatchers
                .jsonPath("$.details[*]")
                .value("Field 'First Name' cannot be empty.")
            )
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Should not save a customer with last name empty and receive status code 400`() {
        val customerDto: CustomerDto = customerToDto(fakeCustomer(lastName = ""))
        val valueAsString: String = objectMapper.writeValueAsString(customerDto)
        mockMvc.perform(MockMvcRequestBuilders
            .post(URL)
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
            .andExpect(
                MockMvcResultMatchers
                    .jsonPath("$.exception")
                    .value("class org.springframework.web.bind.MethodArgumentNotValidException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andExpect(MockMvcResultMatchers
                .jsonPath("$.details[*]")
                .value("Field 'Last Name' cannot be empty.")
            )
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Should not save a customer with cpf empty and receive status code 400`() {
        val customerDto: CustomerDto = customerToDto(fakeCustomer(cpf = ""))
        val valueAsString: String = objectMapper.writeValueAsString(customerDto)
        mockMvc.perform(MockMvcRequestBuilders
            .post(URL)
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
            .andExpect(
                MockMvcResultMatchers
                    .jsonPath("$.exception")
                    .value("class org.springframework.web.bind.MethodArgumentNotValidException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andExpect(MockMvcResultMatchers
                .jsonPath("$.details[*]")
                .value("Field 'CPF' cannot be empty.")
            )
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Should not save a customer with invalid cpf and receive status code 400`() {
        val customerDto: CustomerDto = customerToDto(fakeCustomer(cpf = "111111111"))
        val valueAsString: String = objectMapper.writeValueAsString(customerDto)
        mockMvc.perform(MockMvcRequestBuilders
            .post(URL)
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
            .andExpect(
                MockMvcResultMatchers
                    .jsonPath("$.exception")
                    .value("class org.springframework.web.bind.MethodArgumentNotValidException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andExpect(MockMvcResultMatchers
                .jsonPath("$.details[*]")
                .value("Invalid CPF.")
            )
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Should not save a customer with empty email and receive status code 400`() {
        val customerDto: CustomerDto = customerToDto(fakeCustomer(email = ""))
        val valueAsString: String = objectMapper.writeValueAsString(customerDto)
        mockMvc.perform(MockMvcRequestBuilders
            .post(URL)
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
            .andExpect(
                MockMvcResultMatchers
                    .jsonPath("$.exception")
                    .value("class org.springframework.web.bind.MethodArgumentNotValidException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andExpect(MockMvcResultMatchers
                .jsonPath("$.details[*]")
                .value("Field 'Email' cannot be empty.")
            )
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Should not save a customer with invalid email and receive status code 400`() {
        val customerDto: CustomerDto = customerToDto(fakeCustomer(email = "test"))
        val valueAsString: String = objectMapper.writeValueAsString(customerDto)
        mockMvc.perform(MockMvcRequestBuilders
            .post(URL)
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
            .andExpect(
                MockMvcResultMatchers
                    .jsonPath("$.exception")
                    .value("class org.springframework.web.bind.MethodArgumentNotValidException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andExpect(MockMvcResultMatchers
                .jsonPath("$.details[*]")
                .value("Invalid Email.")
            )
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Should save a customer with 40 characters password and receive status code 201`() {
        val customerDto: CustomerDto = customerToDto(fakeCustomer(password = "a".repeat(40)))
        val valueAsString: String = objectMapper.writeValueAsString(customerDto)
        mockMvc.perform(MockMvcRequestBuilders
            .post(URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Should not save a customer with empty password and receive status code 400`() {
        val customerDto: CustomerDto = customerToDto(fakeCustomer(password = ""))
        val valueAsString: String = objectMapper.writeValueAsString(customerDto)
        mockMvc.perform(MockMvcRequestBuilders
            .post(URL)
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
            .andExpect(
                MockMvcResultMatchers
                    .jsonPath("$.exception")
                    .value("class org.springframework.web.bind.MethodArgumentNotValidException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andExpect(MockMvcResultMatchers
                .jsonPath("$.details[*]")
                .value("Field 'Password' cannot be empty.")
            )
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Should not save a customer with password smaller than 8 and receive status code 400`() {
        val customerDto: CustomerDto = customerToDto(fakeCustomer(password = "1234567"))
        val valueAsString: String = objectMapper.writeValueAsString(customerDto)
        mockMvc.perform(MockMvcRequestBuilders
            .post(URL)
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
            .andExpect(
                MockMvcResultMatchers
                    .jsonPath("$.exception")
                    .value("class org.springframework.web.bind.MethodArgumentNotValidException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andExpect(MockMvcResultMatchers
                .jsonPath("$.details[*]")
                .value("The password must be between 8 and 40 characters.")
            )
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Should not save a customer with password bigger than 40 and receive status code 400`() {
        val customerDto: CustomerDto = customerToDto(fakeCustomer(password = "a".repeat(41)))
        val valueAsString: String = objectMapper.writeValueAsString(customerDto)
        mockMvc.perform(MockMvcRequestBuilders
            .post(URL)
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
            .andExpect(
                MockMvcResultMatchers
                    .jsonPath("$.exception")
                    .value("class org.springframework.web.bind.MethodArgumentNotValidException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andExpect(MockMvcResultMatchers
                .jsonPath("$.details[*]")
                .value("The password must be between 8 and 40 characters.")
            )
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Should not save a customer with empty street and receive status code 400`() {
        val customerDto: CustomerDto = customerToDto(fakeCustomer(street = ""))
        val valueAsString: String = objectMapper.writeValueAsString(customerDto)
        mockMvc.perform(MockMvcRequestBuilders
            .post(URL)
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
            .andExpect(
                MockMvcResultMatchers
                    .jsonPath("$.exception")
                    .value("class org.springframework.web.bind.MethodArgumentNotValidException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andExpect(MockMvcResultMatchers
                .jsonPath("$.details[*]")
                .value("Field 'Street' cannot be empty.")
            )
            .andDo(MockMvcResultHandlers.print())
    }
}