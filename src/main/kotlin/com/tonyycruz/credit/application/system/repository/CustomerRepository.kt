package com.tonyycruz.credit.application.system.repository

import com.tonyycruz.credit.application.system.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository: JpaRepository<Customer, Long>