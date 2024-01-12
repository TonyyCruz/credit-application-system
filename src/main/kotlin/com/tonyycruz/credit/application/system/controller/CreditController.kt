package com.tonyycruz.credit.application.system.controller

import com.tonyycruz.credit.application.system.dto.CreditDto
import com.tonyycruz.credit.application.system.entity.Credit
import com.tonyycruz.credit.application.system.service.impl.CreditService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/credits")
class CreditController(private val creditService: CreditService) {

    @PatchMapping
    fun save(@RequestBody creditDto: CreditDto): ResponseEntity<String> {
        val credit: Credit = creditService.save(creditDto.toEntity())
        val responseMsg = "Credit successfully created!" +
                "code: ${credit.creditCode}," +
                "customer: ${credit.customer?.firstName}," +
                "value: ${credit.creditValue}"
        return ResponseEntity.ok().body(responseMsg)
    }
}