package com.tonyycruz.credit.application.system.service

import com.tonyycruz.credit.application.system.entity.Customer
import org.apache.catalina.Service

interface ICustomerService {
    fun save(customer: Customer): Customer
    fun findById(customerId: Long): Customer
    fun delete(customerId: Long)
}