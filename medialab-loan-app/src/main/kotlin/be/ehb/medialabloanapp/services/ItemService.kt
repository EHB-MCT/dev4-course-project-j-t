package be.ehb.medialabloanapp.services

import be.ehb.medialabloanapp.dto.CreateItemRequest
import be.ehb.medialabloanapp.models.Item
import be.ehb.medialabloanapp.repositories.ItemRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ItemService {

    @Autowired
    lateinit var itemRepository: ItemRepository
    fun getAllItems(): MutableList<Item>{
        return itemRepository.findAll()
    }

    fun saveItem(i: CreateItemRequest): Item{
        logger.info("Saving user: $i")
        try {
            val newItem = Item(name = i.name, description = i.description, availability = i.availability, reserved = i.reserved)
            val savedItem = itemRepository.save(newItem)
            ItemService.logger.info("Item saved successfully: $savedItem")
            return savedItem
        } catch (ex: Exception) {
            ItemService.logger.error("Error saving item: $i, error: $ex")
            throw ex
        }
    }
    companion object {
        private val logger = LoggerFactory.getLogger(ItemService::class.java)
    }
}