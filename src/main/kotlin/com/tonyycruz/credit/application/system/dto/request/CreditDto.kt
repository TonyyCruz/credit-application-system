package com.tonyycruz.credit.application.system.dto.request

import com.tonyycruz.credit.application.system.entity.Credit
import com.tonyycruz.credit.application.system.entity.Customer
import com.tonyycruz.credit.application.system.validation.MaxMonth
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.time.LocalDate

data class CreditDto(
    @field:NotNull(message = "Field 'Credit Value' cannot be null.")
    val creditValue: BigDecimal,
    @MaxMonth(max = 10, message = "The first installment cannot exceed three months.")
    @field:Future
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
