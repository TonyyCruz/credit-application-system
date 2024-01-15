package com.tonyycruz.credit.application.system.controller

import com.tonyycruz.credit.application.system.dto.CreditDto
import com.tonyycruz.credit.application.system.dto.CreditView
import com.tonyycruz.credit.application.system.dto.CreditViewList
import com.tonyycruz.credit.application.system.entity.Credit
import com.tonyycruz.credit.application.system.service.impl.CreditService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

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

    @GetMapping
    fun findAllByCustomerId(@RequestParam("customerId") customerId: Long): ResponseEntity<List<CreditViewList>> {
        val creditViewList: List<CreditViewList> = creditService
            .findAllByCustomerId(customerId)
            .map { credit: Credit -> CreditViewList(credit) }
        return ResponseEntity.ok().body(creditViewList)
    }

    @GetMapping("/{creditCode}")
    fun findByCreditCode(
        @RequestParam("customerId") customerId: Long,
        @PathVariable creditCode: UUID): ResponseEntity<CreditView> {
        val credit: Credit = creditService.findByCreditCode(customerId, creditCode)
        return ResponseEntity.ok().body(CreditView(credit))
    }
}