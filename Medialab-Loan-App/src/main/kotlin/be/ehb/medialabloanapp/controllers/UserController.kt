package be.ehb.medialabloanapp.controllers

import be.ehb.medialabloanapp.dto.CreateLoginRequest
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

    @PostMapping("register")
    fun saveUser(@RequestBody u: CreateUserRequest): User {
        logger.info("Received user to save: $u")
        try {
            val savedUser = service.saveUser(u)
            logger.info("User saved successfully: $savedUser")
            return savedUser
        }catch (ex: Exception) {
            logger.error("Error saving user: $u, error: $ex")
            throw ex
        }
    }

    @PostMapping("login")
    fun loginUser(@RequestBody loginRequest: CreateLoginRequest): Boolean{
        val user = service.getUserByEmail(loginRequest.email)
        if (user != null && user.password == loginRequest.password){
            logger.info("Uer logged in succesfully: $user")
            return true
        }else {
            logger.info("Invalid login attempt for email : ${loginRequest.email}")
            return false
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(UserController::class.java)
    }
}