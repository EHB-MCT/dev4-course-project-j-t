package be.ehb.medialabloanapp.services

import be.ehb.medialabloanapp.models.User
import be.ehb.medialabloanapp.repositories.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {


    @Autowired
    lateinit var userRepository: UserRepository

    fun getUser(id: Long): User {
        val user = userRepository.findById(id)
        if (user.isPresent){
            return user.get()
        }else{
            val erroMessage = "User not found with id: $id"
            logger.error(erroMessage)
            throw RuntimeException(erroMessage)
        }
    }

    fun createUser(user: User): User {
        val email = user.email!!
        val existingUser = userRepository.findByEmail(email)
        if (existingUser != null) {
            val errorMessage = "User with this email already exists"
            logger.error(errorMessage)
            throw RuntimeException(errorMessage)
        }
        return userRepository.save(user)
    }

    fun login(email: String, password:String):User{
        val user = userRepository.findByEmail(email) ?: throw RuntimeException("User not found with email: $email")
        if (user.password != password) {
            throw RuntimeException("Invalid password")
        }else{

        }
        return user
    }

    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    fun updateUserAdminStatus(id: Long, isAdmin: Boolean): User {
        val user = userRepository.findById(id).orElseThrow { RuntimeException("User not found with id: $id") }
        user.isAdmin = isAdmin
        return userRepository.save(user)
    }

    companion object{
        private val logger = LoggerFactory.getLogger(UserService::class.java)
    }


}