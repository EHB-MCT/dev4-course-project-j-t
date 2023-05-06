package be.ehb.medialabloanapp.models

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(name="items")
data class Item(
        @Id
        @GeneratedValue
        var id: Long = -1,
        var name: String,
        var description: String,
        var availability: Boolean,
        var reserved: Boolean

)
