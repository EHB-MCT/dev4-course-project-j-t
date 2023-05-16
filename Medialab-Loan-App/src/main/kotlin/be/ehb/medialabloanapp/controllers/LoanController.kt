package be.ehb.medialabloanapp.controllers

import be.ehb.medialabloanapp.dto.LoanDto
import be.ehb.medialabloanapp.models.HistoryLoan
import be.ehb.medialabloanapp.models.Loan
import be.ehb.medialabloanapp.services.ItemService
import be.ehb.medialabloanapp.services.LoanService
import be.ehb.medialabloanapp.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
@CrossOrigin
@RestController
@RequestMapping("/loans")
class LoanController {
    @Autowired
    lateinit var loanService: LoanService
    @Autowired
    lateinit var itemService: ItemService
    @Autowired
    lateinit var userService: UserService



    // create a new loan with specific item_id and user_id.
    @PostMapping
    fun createLoan(@RequestBody loanDto: LoanDto): ResponseEntity<LoanDto> {
        val loan = Loan(
            user = userService.getUser(loanDto.user!!),
            item = itemService.getItem(loanDto.item!!),
            description = loanDto.description,
            startDate = loanDto.startDate,
            endDate = loanDto.endDate
        )
        val createdLoan = loanService.createLoan(loan)
        val createdLoanDto = LoanDto(
            description = createdLoan.description,
            startDate = createdLoan.startDate,
            endDate = createdLoan.endDate,
            id = createdLoan.id,
            user = createdLoan.user?.id,
            item = createdLoan.item?.id




        )
        return ResponseEntity.created(URI.create("/loans/${createdLoanDto.id}"))
            .body(createdLoanDto)
    }

    // delete a loan byt its id.
    @DeleteMapping("/{id}")
    fun deleteLoan(@PathVariable("id") id:Long): ResponseEntity<String>{
        loanService.deleteLoan(id)
        return ResponseEntity.ok("The loan is succesfully deleted")
    }


    // get a loan by its id.
    @GetMapping("/{id}")
    fun getLoan(@PathVariable("id") id:Long): ResponseEntity<Loan>{
//        val loan = loanService.getLoan(id)
//        val loanDto = LoanDto(
//            description = loan.description,
//            startDate = loan.startDate,
//            endDate = loan.endDate,
//            id = loan.id,
//            user = loan.user?.id,
//            item = loan.item?.id,
//
//        )
//        return ResponseEntity.ok(loanDto)
        return ResponseEntity.ok(loanService.getLoan(id))
    }

    // get all loans.
    @GetMapping
    fun getAllLoans(): ResponseEntity<List<Loan>>{
//        val loans = loanService.getAllLoans().map{loan ->
//            LoanDto(
//                description = loan.description,
//                startDate = loan.startDate,
//                endDate = loan.endDate,
//                id = loan.id,
//                user = loan.user.id,
//                item = loan.item.id,
//            )
//        }
//        return ResponseEntity.ok(loans)
        return ResponseEntity.ok(loanService.getAllLoans())
    }


    // get all the loans that were made in the past (history)
    @GetMapping("/history")
    fun getAllHistory(): ResponseEntity<List<HistoryLoan>>{
//        val loans = loanService.getAllHistory().map{loan ->
//            HistoryLoanDto(
//                id = loan.id,
//                description = loan.description,
//                startDate = loan.startDate,
//                endDate = loan.endDate,
//                user = loan.user,
//                item = loan.item,
//
//            )
//        }
        return ResponseEntity.ok(loanService.getAllHistory())
    }


    // get all loans that are expired (by comparing the endDate of the loan with the currentDate)
    @GetMapping("/expired")
    fun getAllExpiredLoans(): ResponseEntity<List<Loan>>{
//        val loans = loanService.getLoansWithExpiredEndDate().map{loan ->
//            LoanDto(
//                description = loan.description,
//                startDate = loan.startDate,
//                endDate = loan.endDate,
//                id = loan.id,
//                user = loan.user?.id,
//                item = loan.item?.id,
//            )
//        }
        return ResponseEntity.ok(loanService.getLoansWithExpiredEndDate())
    }


    // get all the loans from a specific user.
    @GetMapping("/user/{id}")
    fun getAllLoansFromUser(@PathVariable("id") id:Long): ResponseEntity<List<Loan>>{
//        val loans = loanService.getAllLoansFromUser(id).map{loan ->
//            LoanDto(
//                description = loan.description,
//                startDate = loan.startDate,
//                endDate = loan.endDate,
//                id = loan.id,
//                user = loan.user?.id,
//                item = loan.item?.id,
//            )
//        }
//        return ResponseEntity.ok(loans)

        return ResponseEntity.ok(loanService.getAllLoansFromUser(id))
    }

}