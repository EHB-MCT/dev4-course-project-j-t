package be.ehb.medialabloanapp.dto

data class UserDto(
    var id: Long? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var isAdmin: Boolean = false,
    var password: String? = null,
)
