package com.tonyycruz.credit.application.system.controller

import com.tonyycruz.credit.application.system.dto.request.CreditDto
import com.tonyycruz.credit.application.system.dto.response.CreditView
import com.tonyycruz.credit.application.system.dto.response.CreditViewList
import com.tonyycruz.credit.application.system.entity.Credit
import com.tonyycruz.credit.application.system.service.impl.CreditService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/credits")
class CreditController(private val creditService: CreditService) {

    @PostMapping
    fun save(@RequestBody @Valid creditDto: CreditDto): ResponseEntity<CreditView> {
        val credit: Credit = creditService.save(creditDto.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED).body(CreditView(credit))
    }

    @GetMapping
    fun findAllByCustomerId(@RequestParam("customerId") customerId: Long): ResponseEntity<List<CreditViewList>> {
        val creditViewList: List<CreditViewList> = creditService
            .findAllByCustomerId(customerId)
            .map { credit: Credit -> CreditViewList(credit) }
        return ResponseEntity.ok(creditViewList)
    }

    @GetMapping("/{creditCode}")
    fun findByCreditCode(
        @RequestParam("customerId") customerId: Long,
        @PathVariable creditCode: UUID
    ): ResponseEntity<CreditView> {
        val credit: Credit = creditService.findByCreditCode(customerId, creditCode)
        return ResponseEntity.ok(CreditView(credit))
    }
}