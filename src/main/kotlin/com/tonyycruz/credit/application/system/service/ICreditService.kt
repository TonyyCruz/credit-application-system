package com.tonyycruz.credit.application.system.service

import com.tonyycruz.credit.application.system.entity.Credit
import java.util.Optional
import java.util.UUID

interface ICreditService {
    fun save(credit: Credit): Credit
    fun findAllByCustomerId(customerId: Long): List<Credit>
    fun findByCreditCode(customerId: Long, creditCode: UUID): Credit
}