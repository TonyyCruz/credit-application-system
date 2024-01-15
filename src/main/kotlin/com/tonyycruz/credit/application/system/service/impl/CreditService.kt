package com.tonyycruz.credit.application.system.service.impl

import com.tonyycruz.credit.application.system.entity.Credit
import com.tonyycruz.credit.application.system.entity.Customer
import com.tonyycruz.credit.application.system.repository.CreditRepository
import com.tonyycruz.credit.application.system.repository.CustomerRepository
import com.tonyycruz.credit.application.system.service.ICreditService
import org.springframework.stereotype.Service
import java.util.*

@Service
class CreditService(
    private val creditRepository: CreditRepository,
    private val customerService: CustomerService, private val customerRepository: CustomerRepository
): ICreditService {

    override fun save(credit: Credit): Credit {
        credit.apply {
            customer = customerService.findById(credit.customer?.id!!)
        }
        return creditRepository.save(credit)
    }

    override fun findAllByCustomerId(id: Long): List<Credit> {
        return creditRepository.findAllByCustomerId(id)
    }

    override fun findByCreditCode(id: Long, creditCode: UUID): Credit {
        val credit: Credit = creditRepository.findByCreditCode(creditCode).orElseThrow {
            throw RuntimeException("You do not have permission to access this credit.")
        }
        if (id != credit.customer?.id) {
            throw RuntimeException("You do not have permission to access this credit.")
        }
        return credit
    }
}