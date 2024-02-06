package com.tonyycruz.credit.application.system.dto.response

import com.tonyycruz.credit.application.system.entity.Customer
import java.math.BigDecimal
import java.math.RoundingMode

data class CustomerView(
    val id: Long?,
    val firstName: String,
    val lastName: String,
    val cpf: String,
    val income: BigDecimal,
    val email: String,
    val zipCode: String,
    val street: String
) {
    constructor(customer: Customer) : this(
        id = customer.id,
        firstName = customer.firstName,
        lastName = customer.lastName,
        cpf = customer.cpf,
        income = customer.income.setScale(2, RoundingMode.DOWN),
        email = customer.email,
        zipCode = customer.address.zipCode,
        street = customer.address.street
    )
}
