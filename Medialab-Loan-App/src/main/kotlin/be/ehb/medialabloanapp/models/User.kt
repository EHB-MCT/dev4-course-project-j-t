package be.ehb.medialabloanapp.models

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var password: String? = null,
    var isAdmin: Boolean = false,


    @OneToMany(mappedBy = "user" , cascade = [CascadeType.ALL])
    @JsonBackReference
    var loans: List<Loan> = emptyList(),
){

}
