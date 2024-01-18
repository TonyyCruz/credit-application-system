package com.tonyycruz.credit.application.system.dto.request

import com.tonyycruz.credit.application.system.entity.Address
import com.tonyycruz.credit.application.system.entity.Customer
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Length
import java.math.BigDecimal

data class CustomerUpdateDto(
    @field:NotEmpty(message = "Field 'First Name' cannot be empty.")
    @field:Length(min = 2, max = 50, message = "First name must be between 2 and 50 characters.")
    val firstName: String,
    @field:NotEmpty(message = "Field 'Last Name' cannot be empty.")
    @field:Length(min = 2, max = 100, message = "Last name must be between 2 and 100 characters.")
    val lastName: String,
    @field:NotNull(message = "Field 'Income' cannot be null.")
    val income: BigDecimal,
    @field:NotEmpty(message = "Field 'Zip Code' cannot be empty.")
    val zipCode: String,
    @field:NotEmpty(message = "Field 'Street' cannot be empty.")
    val street: String
) {
    fun toEntity(customer: Customer): Customer {
        customer.firstName = this.firstName
        customer.lastName = this.lastName
        customer.income = this.income
        customer.address.zipCode = this.zipCode
        customer.address.street = this.street
        return customer
    }
}
