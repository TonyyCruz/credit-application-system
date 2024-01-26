package com.tonyycruz.credit.application.system.mocks

import com.tonyycruz.credit.application.system.dto.request.CreditDto
import com.tonyycruz.credit.application.system.entity.Credit
import com.tonyycruz.credit.application.system.entity.Customer
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.util.*
import kotlin.random.Random

interface CreditMock {

    fun fakeCredit(
        creditValue: BigDecimal = Random.nextDouble(0.0, 5000.0).toBigDecimal().setScale(2, RoundingMode.DOWN),
        dayFirstInstallment: LocalDate = LocalDate.now().plusMonths(1),
        numberOfInstallments: Int = Random.nextInt(1, 48),
        customer: Customer
    ) = Credit(
        creditValue = creditValue,
        dayFirstInstallment = dayFirstInstallment,
        numberOfInstallments = numberOfInstallments,
        customer = customer
    )

    fun buildCredit(
        id: Long = Random.nextLong(),
        creditValue: BigDecimal = Random.nextDouble(0.0, 5000.0).toBigDecimal().setScale(2, RoundingMode.DOWN),
        dayFirstInstallment: LocalDate = LocalDate.now().plusMonths(1),
        numberOfInstallments: Int = Random.nextInt(1, 48),
        customer: Customer
    ) = Credit(
        id = id,
        creditCode = UUID.randomUUID(),
        creditValue = creditValue,
        dayFirstInstallment = dayFirstInstallment,
        numberOfInstallments = numberOfInstallments,
        customer = customer
    )

    fun buildCredit(
        id: Long = Random.nextLong(),
        customerId: Long = 1L,
        creditValue: BigDecimal = Random.nextDouble(0.0, 5000.0).toBigDecimal().setScale(2, RoundingMode.DOWN),
        dayFirstInstallment: LocalDate = LocalDate.now().plusMonths(1),
        numberOfInstallments: Int = Random.nextInt(1, 48),
    ) = Credit(
        id = id,
        creditCode = UUID.randomUUID(),
        creditValue = creditValue,
        dayFirstInstallment = dayFirstInstallment,
        numberOfInstallments = numberOfInstallments,
        customer = Customer(id = id)
    )

    fun buildManyCredits(quantity: Int, customerId: Long): List<Credit> {
        val fakeCreditList: MutableList<Credit> = mutableListOf()
        repeat(quantity) { fakeCreditList.add(buildCredit(customerId = customerId)) }
        return fakeCreditList
    }

    fun buildManyCredits(quantity: Int, customer: Customer): List<Credit> {
        val fakeCreditList: MutableList<Credit> = mutableListOf()
        repeat(quantity) { fakeCreditList.add(buildCredit(customer = customer)) }
        return fakeCreditList
    }

    fun creditToDto(credit: Credit) = CreditDto(
        creditValue = credit.creditValue,
        dayFirstInstallment = credit.dayFirstInstallment,
        numberOfInstallments = credit.numberOfInstallments,
        customerId = credit.customer?.id!!
    )

    fun fakeCreditDto(customer: Customer) = creditToDto(fakeCredit(customer = customer))
}