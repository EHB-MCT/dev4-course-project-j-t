package be.ehb.medialabloanapp.dto

import java.time.LocalDate

data class CreateLoanRequest(
    val userId: Long,
    val itemId: Long,
    val startDate: LocalDate,
    val endDate: LocalDate
)
