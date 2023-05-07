package be.ehb.medialabloanapp.models

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate


@Entity
@Table(name="loans")
data class Loan(
    @Id
    @GeneratedValue
    var id: Long = -1,
    val userId: Long,
    val itemId: Long,
    val startDate: LocalDate,
    val endDate: LocalDate
)
