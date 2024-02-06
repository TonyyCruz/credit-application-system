package com.tonyycruz.credit.application.system.service.impl

import com.tonyycruz.credit.application.system.entity.Credit
import com.tonyycruz.credit.application.system.exception.NotFoundException
import com.tonyycruz.credit.application.system.exception.UnauthorizedException
import com.tonyycruz.credit.application.system.repository.CreditRepository
import com.tonyycruz.credit.application.system.service.ICreditService
import org.springframework.stereotype.Service
import java.util.*

@Service
class CreditService(
    private val creditRepository: CreditRepository,
    private val customerService: CustomerService,
) : ICreditService {

    override fun save(credit: Credit): Credit {
        credit.apply {
            customer = customerService.findById(credit.customer?.id!!)
        }
        return creditRepository.save(credit)
    }

    override fun findAllByCustomerId(id: Long): List<Credit> {
        customerService.findById(id)
        return creditRepository.findAllByCustomerId(id)
    }

    override fun findByCreditCode(id: Long, creditCode: UUID): Credit {
        val credit: Credit = creditRepository.findByCreditCode(creditCode).orElseThrow {
            throw NotFoundException("Credit code: $creditCode was not found.")
        }
        if (id != credit.customer?.id) {
            throw UnauthorizedException(message = "You do not have permission to access this credit.")
        }
        return credit
    }
}