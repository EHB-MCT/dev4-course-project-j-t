package be.ehb.medialabloanapp.repositories

import be.ehb.medialabloanapp.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long> {


}