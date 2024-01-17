package com.tonyycruz.credit.application.system.validation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass


@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [MaxMonthValidator::class])
@MustBeDocumented
annotation class MaxMonth(
    val message: String = "The date received is greater than expected.",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = [],
    val max: Long,
)