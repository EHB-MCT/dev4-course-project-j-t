package be.ehb.medialabloanapp.controllers

import be.ehb.medialabloanapp.dto.CreateItemRequest
import be.ehb.medialabloanapp.models.Item
import be.ehb.medialabloanapp.services.ItemService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("item")
class ItemController {

    @Autowired
    lateinit var service: ItemService

    @GetMapping
    fun index(): MutableList<Item>{
        return service.getAllItems()
    }

    @PostMapping
    fun saveUser(@RequestBody i: CreateItemRequest): Item {
        logger.info("Received item to save: $i")
        try {
            val savedItem = service.saveItem(i)
            logger.info("item saved successfully: $savedItem")
            return savedItem
        } catch (ex: Exception) {
            logger.error("Error saving item: $i, error: $ex")
            throw ex
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(ItemController::class.java)
    }
}