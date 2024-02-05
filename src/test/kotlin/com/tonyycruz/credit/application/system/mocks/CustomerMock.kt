package com.tonyycruz.credit.application.system.mocks

import com.tonyycruz.credit.application.system.dto.request.CustomerDto
import com.tonyycruz.credit.application.system.dto.request.CustomerUpdateDto
import com.tonyycruz.credit.application.system.entity.Address
import com.tonyycruz.credit.application.system.entity.Customer
import com.tonyycruz.credit.application.system.utils.Fake
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.random.Random

interface CustomerMock {
    fun fakeCustomer(
        firstName: String = Fake().firstName(),
        lastName: String = Fake().lastName(),
        cpf: String = "28475934625",
        email: String = Fake().email(),
        password: String = Fake().password(),
        zipCode: String = Fake().zipCode(),
        street: String = Fake().street(),
        income: BigDecimal = Random.nextBigDecimal(0.0, 10000.0),
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

    fun customerToDto(customer: Customer): CustomerDto = CustomerDto(
        firstName = customer.firstName,
        lastName = customer.lastName,
        cpf = customer.cpf,
        email = customer.email,
        income = customer.income,
        password = customer.password,
        zipCode = customer.address.zipCode,
        street = customer.address.street
    )

    fun customerToUpdateDto(customer: Customer): CustomerUpdateDto = CustomerUpdateDto(
        firstName = customer.firstName,
        lastName = customer.lastName,
        income = customer.income,
        zipCode = customer.address.zipCode,
        street = customer.address.street
    )

    fun buildCustomer(id: Long = 1L, customer: Customer = fakeCustomer()): Customer {
        customer.id = id
        return customer
    }

    fun Random.nextBigDecimal(from: Double, until: Double): BigDecimal {
        return Random
            .nextDouble(from, until)
            .toBigDecimal().setScale(2, RoundingMode.DOWN)
            .stripTrailingZeros()
    }
}