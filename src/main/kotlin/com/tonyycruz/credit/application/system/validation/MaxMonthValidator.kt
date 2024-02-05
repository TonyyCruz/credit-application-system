package com.tonyycruz.credit.application.system.validation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import java.time.LocalDate

class MaxMonthValidator: ConstraintValidator<MaxMonth, LocalDate> {
    private var maxMonth: Long = 0L

    override fun initialize(maxMonth: MaxMonth) {
        this.maxMonth = maxMonth.max
    }

    override fun isValid(value: LocalDate, context: ConstraintValidatorContext?): Boolean {
        val limitDate: LocalDate = LocalDate.now().plusMonths(maxMonth)
        return value.isBefore(limitDate) || value.isEqual(limitDate)
    }
}