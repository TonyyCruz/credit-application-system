package com.tonyycruz.credit.application.system.dto.response

import com.tonyycruz.credit.application.system.entity.Credit
import com.tonyycruz.credit.application.system.enums.Status
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.util.*

class CreditView(
    val creditCode: UUID,
    val creditValue: BigDecimal,
    val numberOfInstallments: Int,
    val status: Status,
    val dayFirstInstallment: LocalDate
) {
    constructor(credit: Credit): this(
        creditCode = credit.creditCode,
        creditValue = credit.creditValue.setScale(2, RoundingMode.DOWN),
        numberOfInstallments = credit.numberOfInstallments,
        dayFirstInstallment = credit.dayFirstInstallment,
        status = credit.status,
    )
}
