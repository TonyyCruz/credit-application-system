package com.tonyycruz.credit.application.system.utils

import com.tonyycruz.credit.application.system.entity.Address
import com.tonyycruz.credit.application.system.entity.Credit
import com.tonyycruz.credit.application.system.entity.Customer
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.time.LocalDate
import java.util.*
import kotlin.random.Random

abstract class FakeEntitiesBuild {

    protected fun fakeCustomer(
        firstName: String = "Tony",
        lastName: String = "Cruz",
        cpf: String = "28475934625",
        email: String = "tony@email.com",
        password: String = "123456789",
        zipCode: String = "123456789",
        street: String = "Alameda dos Anjos",
        income: BigDecimal = Random.nextDouble(0.0, 10000.0).toBigDecimal().format(2),
    ) = Customer(
        firstName = firstName,
        lastName = lastName,
        cpf = cpf,
        email = email,
        password = password,
        income = income,
        address = Address(
            zipCode = zipCode,
            street = street,
        )
    )

    protected fun buildCustomer(id: Long = 1L, customer: Customer = fakeCustomer()): Customer {
        customer.id = id
        return customer
    }

    protected fun fakeCredit(
        creditValue: BigDecimal = Random.nextDouble(0.0, 5000.0).toBigDecimal().format(2),
        dayFirstInstallment: LocalDate = LocalDate.now().plusMonths(1),
        numberOfInstallments: Int = Random.nextInt(1, 48),
        customer: Customer
    ) = Credit(
        creditValue = creditValue,
        dayFirstInstallment = dayFirstInstallment,
        numberOfInstallments = numberOfInstallments,
        customer = customer
    )

    protected fun buildCredit(
        id: Long = 1L,
        customerId: Long = 1L,
        creditValue: BigDecimal = Random.nextDouble(0.0, 5000.0).toBigDecimal().format(2),
        dayFirstInstallment: LocalDate = LocalDate.now().plusMonths(1),
        numberOfInstallments: Int = Random.nextInt(1, 48),
    ) = Credit(
        id = id,
        creditCode = UUID.randomUUID(),
        creditValue = creditValue,
        dayFirstInstallment = dayFirstInstallment,
        numberOfInstallments = numberOfInstallments,
        customer = buildCustomer(id = customerId)
    )

    protected fun buildCredit(
        id: Long = Random.nextLong(),
        creditValue: BigDecimal = Random.nextDouble(0.0, 5000.0).toBigDecimal().format(2),
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

    protected fun buildManyCredits(quantity: Int, customerId: Long): List<Credit> {
        val fakeCreditList: MutableList<Credit> = mutableListOf()
        repeat(quantity) { fakeCreditList.add(buildCredit(customerId = customerId)) }
        return fakeCreditList
    }

    protected fun buildManyCredits(quantity: Int, customer: Customer): List<Credit> {
        val fakeCreditList: MutableList<Credit> = mutableListOf()
        repeat(quantity) { fakeCreditList.add(buildCredit(customer = customer)) }
        return fakeCreditList
    }

    private fun BigDecimal.format(decimals: Int) = this.setScale(decimals, RoundingMode.DOWN)
}