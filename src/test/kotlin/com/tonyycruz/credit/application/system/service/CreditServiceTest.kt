package com.tonyycruz.credit.application.system.service

import com.tonyycruz.credit.application.system.entity.Credit
import com.tonyycruz.credit.application.system.entity.Customer
import com.tonyycruz.credit.application.system.repository.CreditRepository
import com.tonyycruz.credit.application.system.service.impl.CreditService
import com.tonyycruz.credit.application.system.service.impl.CustomerService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.hibernate.validator.constraints.Range
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CreditServiceTest {
    @MockK lateinit var creditRepository: CreditRepository
    @InjectMockKs lateinit var creditService: CreditService
    @MockK lateinit var customerService: CustomerService

    @Test
    fun `Should create a credit`() {
        val fakeCredit: Credit = buildCredit(customerId = 1)
        val fakeCustomer: Customer = Customer(id = 1)
        every { customerService.findById(fakeCredit.customer?.id as Long) } returns fakeCustomer
        every { creditRepository.save(fakeCredit) } returns fakeCredit
        val current = creditService.save(fakeCredit)

        Assertions.assertThat(current).isNotNull
        Assertions.assertThat(current).isSameAs(fakeCredit)
        verify(exactly = 1) { creditRepository.save(fakeCredit) }
    }

    @Test fun `should find all credits by customer id`() {
        val customerId: Long = 1120
        val fakeCreditList: MutableList<Credit> = mutableListOf()
        repeat(5) { fakeCreditList.add(buildCredit(customerId = customerId)) }
        every { creditRepository.findAllByCustomerId(customerId) } returns fakeCreditList
        val current = creditService.findAllByCustomerId(customerId)

        Assertions.assertThat(current).isNotEmpty
        Assertions.assertThat(current).size().isEqualTo(5)
        Assertions.assertThat(current).isSameAs(fakeCreditList)
        verify(exactly = 1) { creditRepository.findAllByCustomerId(customerId) }
    }

    private fun buildCredit(
        id: Long? = 1L,
        customerId: Long? = 1L,
        creditValue: BigDecimal = BigDecimal.valueOf(100.0),
        dayFirstInstallment: LocalDate = LocalDate.now(),
        numberOfInstallments: Int = 5
    ) = Credit(
        id = id,
        creditCode = UUID.randomUUID(),
        creditValue = creditValue,
        dayFirstInstallment = dayFirstInstallment,
        numberOfInstallments = numberOfInstallments,
        customer = Customer(id = customerId)
    )
}