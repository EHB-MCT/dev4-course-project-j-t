package be.ehb.medialabloanapp.repositories

import be.ehb.medialabloanapp.models.Loan
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LoanRepository: JpaRepository<Loan, Long> {
    fun findByUserId(userId: Long): List<Loan>
}