package com.tonyycruz.credit.application.system.utils

import com.tonyycruz.credit.application.system.entity.Address
import com.tonyycruz.credit.application.system.entity.Credit
import com.tonyycruz.credit.application.system.entity.Customer
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

abstract class FakeEntitiesBuild {

    protected fun buildCustomer(
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

    protected fun buildCredit(
        id: Long = 1L,
        customerId: Long,
        creditValue: BigDecimal = BigDecimal.valueOf(100.0),
        dayFirstInstallment: LocalDate = LocalDate.now(),
        numberOfInstallments: Int = 5,
        customer: Customer = buildCustomer(id = customerId)
    ) = Credit(
        id = id,
        creditCode = UUID.randomUUID(),
        creditValue = creditValue,
        dayFirstInstallment = dayFirstInstallment,
        numberOfInstallments = numberOfInstallments,
        customer = customer
    )

    protected fun buildManyCredits(quantity: Int, customerId: Long = 1): List<Credit> {
        val fakeCreditList: MutableList<Credit> = mutableListOf()
        repeat(quantity) { fakeCreditList.add(buildCredit(customerId = customerId)) }
        return fakeCreditList
    }

    protected fun buildManyCredits(quantity: Int, customer: Customer): List<Credit> {
        val fakeCreditList: MutableList<Credit> = mutableListOf()
        repeat(quantity) { fakeCreditList.add(buildCredit(customerId = customer.id as Long, customer = customer)) }
        return fakeCreditList
    }
}