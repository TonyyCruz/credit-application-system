package com.tonyycruz.credit.application.system.repository

import com.tonyycruz.credit.application.system.entity.Credit
import com.tonyycruz.credit.application.system.entity.Customer
import com.tonyycruz.credit.application.system.utils.FakeEntitiesBuild
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles
import java.util.Optional

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreditRepositoryTest: FakeEntitiesBuild() {
    @Autowired lateinit var creditRepository: CreditRepository
    @Autowired lateinit var testEntityManager: TestEntityManager
    private lateinit var customer: Customer
    private lateinit var credit1: Credit
    private lateinit var credit2: Credit

    @BeforeEach fun setup() {
        customer = testEntityManager.persist(fakeCustomer())
        credit1 = testEntityManager.persist(fakeCredit(customer = customer))
        credit2 = testEntityManager.persist(fakeCredit(customer = customer))
    }

    @Test
    fun `Should find credit by credit code`() {
        val current: Credit = creditRepository.findByCreditCode(credit1.creditCode).get()

        Assertions.assertThat(current).isNotNull
        Assertions.assertThat(current).isSameAs(credit1)

    }

    @Test
    fun `Should find all credits by customer id`() {
        val current: List<Credit> = creditRepository.findAllByCustomerId(customer.id!!)
        val expectSize: Int = 2

        Assertions.assertThat(current).isNotEmpty
        Assertions.assertThat(current.size).isEqualTo(expectSize)
        Assertions.assertThat(current).contains(credit1, credit2)
    }
}