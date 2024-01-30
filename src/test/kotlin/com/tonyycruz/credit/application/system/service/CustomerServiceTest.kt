package com.tonyycruz.credit.application.system.service

import com.tonyycruz.credit.application.system.entity.Customer
import com.tonyycruz.credit.application.system.exception.NotFoundException
import com.tonyycruz.credit.application.system.repository.CustomerRepository
import com.tonyycruz.credit.application.system.service.impl.CustomerService
import com.tonyycruz.credit.application.system.mocks.MockEntities
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
import java.util.*
import kotlin.random.Random

@ExtendWith(MockKExtension::class)
class CustomerServiceTest: MockEntities() {
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
}
