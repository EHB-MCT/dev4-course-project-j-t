package be.ehb.medialabloanapp.repositories

import be.ehb.medialabloanapp.models.Item
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface ItemRepository: JpaRepository<Item,Long> {


}