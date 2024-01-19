package com.tonyycruz.credit.application.system.service

import com.tonyycruz.credit.application.system.entity.Address
import com.tonyycruz.credit.application.system.entity.Customer
import com.tonyycruz.credit.application.system.repository.CustomerRepository
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal

@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CustomerServiceTest {
    @MockK lateinit var customerRepository: CustomerRepository
    @InjectMockKs lateinit var customerService: ICustomerService

    @Test
    fun `Should create a customer`() {
        // Given

        // When

        // Then
    }

    fun buildCustomer(
        firstName: String = "Tony",
        lastName: String = "Cruz",
        cpf: String = "28475934625",
        email: String = "tony@email.com",
        password: String = "123456789",
        zipCode: String = "123456789",
        street: String = "Alameda dos Anjos",
        income: BigDecimal = BigDecimal.valueOf(4500.0),
        id: Long = 1L
    ) = Customer(
        firstName = firstName,
        lastName = lastName,
        cpf = cpf,
        email = email,
        password = password,
        address = Address(
            zipCode = zipCode,
            street = street,
        ),
        income = income,
        id = id
    )
}
