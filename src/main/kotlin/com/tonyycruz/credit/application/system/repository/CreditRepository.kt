package com.tonyycruz.credit.application.system.repository

import com.tonyycruz.credit.application.system.entity.Credit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional
import java.util.UUID

interface CreditRepository : JpaRepository<Credit, Long> {
    fun findByCreditCode(creditCode: UUID): Optional<Credit>

    @Query(value = "SELECT * FROM CREDIT WHERE CUSTOMER_ID = ?1", nativeQuery = true)
    fun findAllByCustomerId(customerId: Long): List<Credit>
}