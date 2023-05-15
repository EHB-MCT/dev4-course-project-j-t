package be.ehb.medialabloanapp.models

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*


@Entity
@Table(name="items")
data class Item(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,
        var name: String? = null,
        var img: String? = null,
        var description: String? = null,
        var availability: Boolean = false,

        @OneToOne(mappedBy = "item", cascade = [CascadeType.ALL],fetch= FetchType.LAZY, optional = false)
        @JsonBackReference
        var loan: Loan? = null,

){

}
