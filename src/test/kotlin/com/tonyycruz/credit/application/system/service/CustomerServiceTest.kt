package com.tonyycruz.credit.application.system.service

import com.tonyycruz.credit.application.system.entity.Address
import com.tonyycruz.credit.application.system.entity.Customer
import com.tonyycruz.credit.application.system.exception.NotFoundException
import com.tonyycruz.credit.application.system.repository.CustomerRepository
import com.tonyycruz.credit.application.system.service.impl.CustomerService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.util.*
import kotlin.random.Random

@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CustomerServiceTest {
    @MockK lateinit var customerRepository: CustomerRepository
    @InjectMockKs lateinit var customerService: CustomerService

    @Test
    fun `Should create a customer`() {
        val fakeCustomer: Customer = buildCustomer()
        every { customerRepository.save(fakeCustomer) } returns fakeCustomer
        val current: Customer = customerService.save(fakeCustomer)

        Assertions.assertThat(current).isNotNull()
        Assertions.assertThat(current).isSameAs(fakeCustomer)
        verify(exactly = 1) { customerRepository.save(fakeCustomer) }
    }

    @Test
    fun `Should find a customer by id`() {
        val fakeId: Long = Random.nextLong()
        val fakeCustomer: Customer = buildCustomer(id = fakeId)
        every { customerRepository.findById(fakeId) } returns Optional.of(fakeCustomer)
        val current: Customer = customerService.findById(fakeId)

        Assertions.assertThat(current).isNotNull
        Assertions.assertThat(current).isExactlyInstanceOf(Customer::class.java)
        Assertions.assertThat(current).isSameAs(fakeCustomer)
        verify(exactly = 1) { customerRepository.findById(fakeId) }
    }

    @Test
    fun `Should not find a customer with invalid id and throws an exception`() {
        val fakeId: Long = Random.nextLong()
        every { customerRepository.findById(fakeId) } returns Optional.empty()

        Assertions.assertThatExceptionOfType(NotFoundException::class.java)
            .isThrownBy { customerService.findById(fakeId) }
            .withMessage("Id '$fakeId' was not found.")
        verify(exactly = 1) { customerRepository.findById(fakeId) }
    }

    @Test
    fun `Should delete a customer by id`() {
        val fakeId: Long = Random.nextLong()
        val fakeCustomer: Customer = buildCustomer(id = fakeId)
        every { customerRepository.findById(fakeId) } returns Optional.of(fakeCustomer)
        every { customerRepository.delete(fakeCustomer) } just runs
        customerService.delete(fakeId)

        verify(exactly = 1) { customerRepository.delete(fakeCustomer) }
        verify(exactly = 1) { customerRepository.findById(fakeId) }
    }

    private fun buildCustomer(
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
