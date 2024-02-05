package com.tonyycruz.credit.application.system.dto.request

import com.tonyycruz.credit.application.system.entity.Address
import com.tonyycruz.credit.application.system.entity.Customer
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.br.CPF
import java.math.BigDecimal

data class CustomerDto(
    @field:NotEmpty(message = "Field 'First Name' must not be empty.")
    val firstName: String,
    @field:NotEmpty(message = "Field 'Last Name' must not be empty.")
    val lastName: String,
    @field:CPF(message = "Invalid CPF.")
    val cpf: String,
    @field:NotNull(message = "Field 'Income' must not be null.")
    val income: BigDecimal,
    @field:NotEmpty(message = "Field 'Email' must not be empty.") @field:Email(message = "Invalid Email.")
    val email: String,
    @field:Length(min = 8,  max = 40, message = "The password must be between 8 and 40 characters.")
    val password: String,
    @field:NotEmpty(message = "Field 'Zip Code' must not be empty.")
    val zipCode: String,
    @field:NotEmpty(message = "Field 'Street' must not be empty.")
    val street: String
) {
    fun toEntity(): Customer = Customer(
        firstName = this.firstName,
        lastName = this.lastName,
        cpf = this.cpf,
        income = this.income,
        email = this.email,
        password = this.password,
        address = Address(zipCode = this.zipCode, street = this.street)
    )
}