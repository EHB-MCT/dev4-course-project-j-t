package be.ehb.medialabloanapp.dto

import java.time.LocalDate

data class LoanDto(
    var id: Long? = null,
    var user: Long? =null,
    var item: Long? = null,
    var description: String?= null,
    var startDate: LocalDate? = null,
    var endDate: LocalDate? = null
)
