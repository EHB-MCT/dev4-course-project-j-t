package be.ehb.medialabloanapp.repositories

import be.ehb.medialabloanapp.models.Loan
import org.springframework.data.jpa.repository.JpaRepository

interface LoanRepository:JpaRepository<Loan,Long> {
    fun findByItemId(itemId: Long): Loan?
}