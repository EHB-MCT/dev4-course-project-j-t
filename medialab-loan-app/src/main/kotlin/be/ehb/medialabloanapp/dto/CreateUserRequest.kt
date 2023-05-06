package be.ehb.medialabloanapp.dto

data class CreateUserRequest( var firstname: String,
                              var lastname: String,
                              var email: String,
                              var password: String,
                              var isAdmin: Boolean)
