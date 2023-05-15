package be.ehb.medialabloanapp.dto

import java.time.LocalDate

data class HistoryLoanDto(
    var id: Long? = null,
    var userId: Long? =null,
    var itemId: Long? = null,
    var loanId: Long? = null,
    var description: String?= null,
    var startDate: LocalDate? = null,
    var endDate: LocalDate? = null
)
