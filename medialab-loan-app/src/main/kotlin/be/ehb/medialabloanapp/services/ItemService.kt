package be.ehb.medialabloanapp.services

//import be.ehb.medialabloanapp.dto.CreateItemRequest

import be.ehb.medialabloanapp.models.Item
import be.ehb.medialabloanapp.repositories.ItemRepository
import be.ehb.medialabloanapp.repositories.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ItemService(private val userService: UserService) {



    @Autowired
    lateinit var itemRepository: ItemRepository
    @Autowired
    lateinit var userRepository: UserRepository

    fun getItem(id: Long): Item{
        val item = itemRepository.findById(id)
         if (item.isPresent){
             return item.get()
         }else {
             val errorMessage = "Item not found with id: $id"
             logger.error(errorMessage)
             throw RuntimeException(errorMessage)
         }
    }


    fun createItem(item: Item): Item{

        return itemRepository.save(item)
    }


    fun updateItem(item: Item): Item{
        val existingItem = itemRepository.findById(item.id!!)
            .orElseThrow { RuntimeException("Item with id ${item.id} not found") }
        existingItem.apply {
            img = item.img
            name = item.name
            description = item.description

            availability = item.availability
        }
        return itemRepository.save(existingItem)
    }

    fun deleteItem(id:Long){

        return itemRepository.deleteById(id)
    }

    fun  getAllItems(): List<Item> {
        return itemRepository.findAll()
    }


    companion object {
        private val logger = LoggerFactory.getLogger(ItemService::class.java)
    }
}