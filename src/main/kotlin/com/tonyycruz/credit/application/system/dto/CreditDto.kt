package com.tonyycruz.credit.application.system.dto

import com.tonyycruz.credit.application.system.entity.Credit
import com.tonyycruz.credit.application.system.entity.Customer
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.FutureOrPresent
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.Length
import java.math.BigDecimal
import java.time.LocalDate

data class CreditDto(
    @field:NotNull(message = "Field 'Credit Value' cannot be null.")
    val creditValue: BigDecimal,
    @field:FutureOrPresent
    val dayFirstInstallment: LocalDate,
    @field:Min(1, message = "Installments must be at least 1.")
    @field:Max(48, message = "Installment must be up to 48.")
    val numberOfInstallments: Int,
    @field:NotNull(message = "Field 'Customer Id' cannot be null.")
    val customerId: Long
) {
    fun toEntity(): Credit = Credit(
        creditValue = this.creditValue,
        dayFirstInstallment = this.dayFirstInstallment,
        numberOfInstallments = this.numberOfInstallments,
        customer = Customer(id = this.customerId)
    )
}
