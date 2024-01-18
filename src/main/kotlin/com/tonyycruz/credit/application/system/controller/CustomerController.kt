package com.tonyycruz.credit.application.system.controller

import com.tonyycruz.credit.application.system.dto.request.CustomerDto
import com.tonyycruz.credit.application.system.dto.request.CustomerUpdateDto
import com.tonyycruz.credit.application.system.dto.response.CustomerView
import com.tonyycruz.credit.application.system.entity.Customer
import com.tonyycruz.credit.application.system.service.impl.CustomerService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/customers")
class CustomerController(private val customerService: CustomerService) {

    @PostMapping
    fun save(@RequestBody @Valid customerDto: CustomerDto): ResponseEntity<String> {
        val newCustomer: Customer = customerService.save(customerDto.toEntity())
        val createdMsg: String = "Customer ${newCustomer.email} saved!"
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMsg)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<CustomerView> {
        val customer: Customer = customerService.findById(id)
        return ResponseEntity.ok(CustomerView(customer))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) = customerService.delete(id)

    @PatchMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody @Valid customerUpdateDto: CustomerUpdateDto
    ): ResponseEntity<CustomerView> {
        val customer: Customer = customerService.findById(id)
        val updatedCustom: Customer = customerService.save(customerUpdateDto.toEntity(customer))
        return ResponseEntity.ok(CustomerView(updatedCustom))
    }
}