package be.ehb.medialabloanapp.models

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name="history_loans")
data class HistoryLoan(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var loanId: Long? = null,
    var userId: Long? = null,
    var itemId: Long? = null,
    var description: String? = null,
    var startDate: LocalDate? = null,
    var endDate: LocalDate? = null,
)
