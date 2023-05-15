package be.ehb.medialabloanapp.repositories

import be.ehb.medialabloanapp.models.HistoryLoan
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface HistoryLoanRepository : JpaRepository<HistoryLoan, Long> {
    // Add any custom query methods if needed
    // ...
}