package be.ehb.medialabloanapp.services

import be.ehb.medialabloanapp.dto.CreateLoanRequest
import be.ehb.medialabloanapp.models.Item
import be.ehb.medialabloanapp.models.Loan
import be.ehb.medialabloanapp.repositories.LoanRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class LoanService {


    @Autowired
    lateinit var loanRepository: LoanRepository

    fun getAllLoans(): MutableList<Loan>{
        return loanRepository.findAll()
    }


    fun saveLoan(l : CreateLoanRequest) : Loan{
        LoanService.logger.info("Saving loan: $l")
        try {
            val newLoan = Loan(userId = l.userId, itemId = l.itemId, startDate = l.startDate, endDate = l.endDate)
            val savedItem = loanRepository.save(newLoan)
            LoanService.logger.info("Item saved successfully: $savedItem")
            return savedItem
        } catch (ex: Exception) {
            LoanService.logger.error("Error saving item: $l, error: $ex")
            throw ex
        }
    }

    fun getLoansByUserId(userId: Long): List<Loan> {
        // Use the loanRepository to fetch loans by user id
        return loanRepository.findByUserId(userId)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(LoanService::class.java)
    }
}