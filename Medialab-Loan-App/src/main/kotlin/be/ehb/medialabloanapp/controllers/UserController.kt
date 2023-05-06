package be.ehb.medialabloanapp.controllers

import be.ehb.medialabloanapp.dto.CreateUserRequest
import be.ehb.medialabloanapp.models.User
import be.ehb.medialabloanapp.services.UserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("user")
class UserController {

    @Autowired
    lateinit var service: UserService

    @GetMapping
    fun index(): MutableList<User>{
        return service.getAllUsers()
    }

    @PostMapping
    fun saveUser(@RequestBody u: CreateUserRequest): User{
        logger.info("Received user to save: $u")
        try {
            val savedUser = service.saveUser(u)
            logger.info("User saved successfully: $savedUser")
            return savedUser
        } catch (ex: Exception) {
            logger.error("Error saving user: $u, error: $ex")
            throw ex
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(UserController::class.java)
    }
}