package com.tonyycruz.credit.application.system.repository

import com.tonyycruz.credit.application.system.entity.Credit
import org.springframework.data.jpa.repository.JpaRepository

interface CreditRepository: JpaRepository<Credit, Long>