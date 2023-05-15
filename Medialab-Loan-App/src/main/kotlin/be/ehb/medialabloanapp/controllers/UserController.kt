package be.ehb.medialabloanapp.controllers

import be.ehb.medialabloanapp.dto.LoginCredentialsDto
import be.ehb.medialabloanapp.dto.UserDto
import be.ehb.medialabloanapp.models.User
import be.ehb.medialabloanapp.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
@CrossOrigin
@RestController
@RequestMapping("/users")
class UserController {

    @Autowired
    lateinit var userService: UserService

    // get user by id.
    @GetMapping("/{id}")
    fun getUser(@PathVariable("id") id: Long): ResponseEntity<UserDto>{
        val user = userService.getUser(id)
        val userDto = UserDto(
            id = user.id,
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            isAdmin = user.isAdmin

        )
        return ResponseEntity.ok(userDto)
    }

    // create (register) a user
    @PostMapping
    fun createUser(@RequestBody userDto: UserDto):ResponseEntity<UserDto>{
        val user = User(
            firstName = userDto.firstName,
            lastName = userDto.lastName,
            password = userDto.password,
            email = userDto.email,
            isAdmin = userDto.isAdmin,
        )
        val createdUser = userService.createUser(user)
        val createdUserDto = UserDto(
            id = createdUser.id,
            firstName = createdUser.firstName,
            lastName = createdUser.lastName,
            isAdmin = createdUser.isAdmin,
            email = createdUser.email,
        )
        return ResponseEntity.created(URI.create("/users/${createdUserDto.id}")).body(createdUserDto)
    }

    // login in the user by email and password
    @PostMapping("/login")
    fun login(@RequestBody loginCredentialsDto: LoginCredentialsDto):ResponseEntity<UserDto>{
        val user = userService.login(loginCredentialsDto.email, loginCredentialsDto.password)
        val userDto = UserDto(
            id = user.id,
            firstName = user.firstName,
            lastName = user.lastName,
            isAdmin = user.isAdmin,
            email = user.email,
        )
        return ResponseEntity.ok(userDto)
    }

    // get all the users.
    @GetMapping
    fun getAllUser(): ResponseEntity<List<UserDto>>{
        val users = userService.getAllUsers().map{user ->
            UserDto(
                id= user.id,
                firstName = user.firstName,
                lastName = user.lastName,
                email = user.email,
                isAdmin = user.isAdmin
            )
        }
        return ResponseEntity.ok(users)
    }

    // update the isAdmin state to give rights to the user.
    @PutMapping("/{id}/admin")
    fun updateUserAdminStatus(@PathVariable("id") id: Long, @RequestParam("isAdmin") isAdmin: Boolean): ResponseEntity<UserDto> {
        val updatedUser = userService.updateUserAdminStatus(id, isAdmin)
        val userDto = UserDto(
            id = updatedUser.id,
            firstName = updatedUser.firstName,
            lastName = updatedUser.lastName,
            email = updatedUser.email,
            isAdmin = updatedUser.isAdmin
        )
        return ResponseEntity.ok(userDto)
    }


}