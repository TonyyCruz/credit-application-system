package com.tonyycruz.credit.application.system.controller

import com.tonyycruz.credit.application.system.dto.CustomerDto
import com.tonyycruz.credit.application.system.dto.CustomerUpdateDto
import com.tonyycruz.credit.application.system.dto.CustomerViewDto
import com.tonyycruz.credit.application.system.entity.Customer
import com.tonyycruz.credit.application.system.service.impl.CustomerService
import jakarta.websocket.server.PathParam
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/customers")
class CustomerController(private val customerService: CustomerService) {

    @PostMapping
    fun save(@RequestBody customerDto: CustomerDto): ResponseEntity<String> {
        val newCustomer: Customer = customerService.save(customerDto.toEntity())
        val createdMsg: String = "Customer ${newCustomer.email} saved!"
        return ResponseEntity.ok().body(createdMsg)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<CustomerViewDto> {
        val customer: Customer = customerService.findById(id)
        return ResponseEntity.ok().body(CustomerViewDto(customer))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): HttpEntity<*> {
        customerService.delete(id)
        return ResponseEntity.EMPTY
    }

    @PatchMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody customerUpdateDto: CustomerUpdateDto
    ): ResponseEntity<CustomerViewDto> {
        val customer: Customer = customerService.findById(id)
        val updatedCustom: Customer = customerService.save(customerUpdateDto.toEntity(customer))
        return ResponseEntity.ok().body(CustomerViewDto(updatedCustom))
    }
}