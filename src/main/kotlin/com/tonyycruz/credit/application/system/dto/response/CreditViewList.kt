package com.tonyycruz.credit.application.system.dto.response

import com.tonyycruz.credit.application.system.entity.Credit
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.UUID

data class CreditViewList(
    val creditCode: UUID,
    val creditValue: BigDecimal,
    val numberOfInstallments: Int
) {
    constructor(credit: Credit) : this(
        creditCode = credit.creditCode,
        creditValue = credit.creditValue.setScale(2, RoundingMode.DOWN),
        numberOfInstallments = credit.numberOfInstallments
    )
}