package be.ehb.medialabloanapp.controllers


import be.ehb.medialabloanapp.dto.ItemDto
import be.ehb.medialabloanapp.models.Item
import be.ehb.medialabloanapp.services.ItemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@CrossOrigin
@RestController
@RequestMapping("/items")
class ItemController {

    @Autowired
    lateinit var itemService: ItemService

    // get item by its id.
    @GetMapping("/{id}")
    fun getItem(@PathVariable("id") id:Long): ResponseEntity<ItemDto>{
        val item = itemService.getItem(id)
        val itemDto = ItemDto(
            id = item.id,
            name = item.name,
            img = item.img,
            description = item.description,
            availability = item.availability,

        )
        return ResponseEntity.ok(itemDto)
    }

    // creating a new item.
    @PostMapping
    fun createItem(@RequestBody itemDto: ItemDto): ResponseEntity<ItemDto>{
        val item = Item(
            description = itemDto.description,
            name = itemDto.name,
            img = itemDto.img,
            availability = itemDto.availability,

        )
        val createdItem = itemService.createItem(item)
        val createdItemDto = ItemDto(
            id= createdItem.id,
            img = createdItem.img,
            description = createdItem.description,
            name = createdItem.name,
            availability = createdItem.availability,

        )
        return ResponseEntity.created(URI.create("/items/${createdItemDto.id}")).body(createdItemDto)
    }

    // deleting an item by its id.
    @DeleteMapping("/{id}")
    fun deleteItem(@PathVariable("id") id:Long): ResponseEntity<String>{
        itemService.deleteItem(id)
        return ResponseEntity.ok("The item is succesfully deleted, and also the corresponding loans")
    }



    @GetMapping
    fun getAllItems(): ResponseEntity<List<ItemDto>>{
        val items = itemService.getAllItems().map{item ->
            ItemDto(
                id= item.id,
                img = item.img,
                description = item.description,
                name = item.name,
                availability = item.availability,

            )
        }
        return ResponseEntity.ok(items)
    }

}