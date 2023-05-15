package be.ehb.medialabloanapp.dto

data class ItemDto(
    var id: Long? = null,
    var name: String? = null,
    var img: String? = null,
    var description: String? = null,
    var availability: Boolean = false,

)
