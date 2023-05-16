package be.ehb.medialabloanapp.models

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name="history_loans")
data class HistoryLoan(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,


    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    var user: User? = null,

    @ManyToOne(optional = false)
    @JoinColumn(name ="item_id")
    @JsonManagedReference
    var item: Item? = null,

    var description: String? = null,
    var startDate: LocalDate? = null,
    var endDate: LocalDate? = null,
)
