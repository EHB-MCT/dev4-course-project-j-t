package be.ehb.medialabloanapp.controllers

import be.ehb.medialabloanapp.dto.CreateLoanRequest
import be.ehb.medialabloanapp.models.Loan
import be.ehb.medialabloanapp.services.LoanService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("loan")
class LoanController {

    @Autowired
    lateinit var service : LoanService

    @PostMapping
    fun loanItem(@RequestBody l: CreateLoanRequest): Loan {
        logger.info("Received item to save: $l")
        try {
            val savedLoan = service.saveLoan(l)
            logger.info("item saved successfully: $savedLoan")
            return savedLoan
        } catch (ex: Exception) {
            logger.error("Error saving item: $l, error: $ex")
            throw ex
        }
    }


    companion object {
        private val logger = LoggerFactory.getLogger(LoanController::class.java)
    }
}