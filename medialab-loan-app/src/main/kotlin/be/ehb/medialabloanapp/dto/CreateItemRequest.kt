package be.ehb.medialabloanapp.dto

data class CreateItemRequest(
    var name: String,
    var description: String,
    var availability: Boolean,
    var reserved: Boolean
)
