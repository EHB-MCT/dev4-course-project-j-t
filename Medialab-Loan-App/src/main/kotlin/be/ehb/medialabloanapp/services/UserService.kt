package be.ehb.medialabloanapp.services

import be.ehb.medialabloanapp.dto.CreateUserRequest
import be.ehb.medialabloanapp.models.User
import be.ehb.medialabloanapp.repositories.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    lateinit var userRepository: UserRepository
    fun getAllUsers(): MutableList<User>{
        return userRepository.findAll()
    }

    fun saveUser(u : CreateUserRequest): User {
        logger.info("Saving user: $u")
        try {
            val newUser = User(firstname = u.firstname, lastname = u.lastname, email = u.email, password = u.password, isAdmin = u.isAdmin)
            val savedUser = userRepository.save(newUser)
            logger.info("User saved successfully: $savedUser")
            return savedUser
        } catch (ex: Exception) {
            logger.error("Error saving user: $u, error: $ex")
            throw ex
        }
    }

    fun getUserByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(UserService::class.java)
    }
}