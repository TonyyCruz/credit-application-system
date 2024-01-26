package com.tonyycruz.credit.application.system.mocks

import com.tonyycruz.credit.application.system.dto.request.CustomerDto
import com.tonyycruz.credit.application.system.entity.Address
import com.tonyycruz.credit.application.system.entity.Customer
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.random.Random

interface CustomerMock {

    fun fakeCustomer(
        firstName: String = "Tony",
        lastName: String = "Cruz",
        cpf: String = "28475934625",
        email: String = "tony@email.com",
        password: String = "123456789",
        zipCode: String = "123456789",
        street: String = "Alameda dos Anjos",
        income: BigDecimal = Random.nextDouble(0.0, 10000.0).toBigDecimal().setScale(2, RoundingMode.DOWN),
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

    fun customerToDto(customer: Customer) = CustomerDto(
        firstName = customer.firstName,
        lastName = customer.lastName,
        cpf = customer.cpf,
        email = customer.email,
        income = customer.income,
        password = customer.password,
        zipCode = customer.address.zipCode,
        street = customer.address.street
    )

    fun fakeCustomerDto() = customerToDto(fakeCustomer())

    fun buildCustomer(id: Long = 1L, customer: Customer = fakeCustomer()): Customer {
        customer.id = id
        return customer
    }
}