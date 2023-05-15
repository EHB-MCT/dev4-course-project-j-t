package be.ehb.medialabloanapp.repositories

import be.ehb.medialabloanapp.models.Item
import org.springframework.boot.availability.ApplicationAvailability
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface ItemRepository: JpaRepository<Item,Long> {
//    fun findbyDescription(description: String): Item?
//    fun findByAvailability(availability :Boolean):List<Item>
//    fun findByReserved(reserved: Boolean): List<Item>
//    @Query("SELECT c from Item c where c.id = :id")
//    override fun findById(@Param("id") id: Long): Optional<Item>

}