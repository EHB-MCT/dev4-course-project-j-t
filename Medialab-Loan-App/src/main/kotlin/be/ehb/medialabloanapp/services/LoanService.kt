package be.ehb.medialabloanapp.services

import be.ehb.medialabloanapp.models.HistoryLoan
import be.ehb.medialabloanapp.models.Loan
import be.ehb.medialabloanapp.repositories.HistoryLoanRepository
import be.ehb.medialabloanapp.repositories.ItemRepository
import be.ehb.medialabloanapp.repositories.LoanRepository
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class LoanService(private val itemService: ItemService) {

    @Autowired
    private lateinit var historyLoanRepository: HistoryLoanRepository

    @Autowired
    private lateinit var itemRepository: ItemRepository

    @Autowired
    lateinit var loanRepository: LoanRepository


    fun getLoan(id: Long): Loan{
        val loan = loanRepository.findById(id)
        if (loan.isPresent){
            return loan.get()
        }else {
            val errorMessage = "Loan not found with id: $id"
            LoanService.logger.error(errorMessage)
            throw RuntimeException(errorMessage)
        }
    }


    fun createLoan(loan: Loan): Loan {
        val itemId = loan.item!!.id!!
        val itemToLend = itemService.getItem(itemId)
        if (itemToLend.availability == false){
            val errorMessage = "sorry, this item is already lent out"
            logger.error(errorMessage)
            throw RuntimeException(errorMessage)
        }
        itemToLend.availability = false
        itemService.updateItem(itemToLend)
        return loanRepository.save(loan)


    }


    @Transactional
    fun deleteLoan(id: Long) {
        val loanToDelete = loanRepository.findById(id)
        if (loanToDelete.isPresent) {
            val loan = loanToDelete.get()
            val item = loan.item
            if (item != null) {
                item.loan = null
                item.availability = true // update availability
                itemService.updateItem(item) // update item in database
            }

            // Create a new history loan record using the existing loan's data
            val historyLoan = HistoryLoan(

                user = loan.user,
                item = loan.item,
                description = loan.description,
                startDate = loan.startDate,
                endDate = loan.endDate
            )

            historyLoanRepository.save(historyLoan)
            loanRepository.delete(loan)

        } else {
            val errorMessage = "Loan with id $id not found"
            logger.error(errorMessage)
            throw RuntimeException(errorMessage)
        }
    }

    fun  getAllLoans(): List<Loan> {
        return loanRepository.findAll()
    }

    fun getAllHistory(): List<HistoryLoan>{
        return historyLoanRepository.findAll()
    }

    fun getAllLoansFromUser(id:Long) : List<Loan>{
        return loanRepository.findAll().filter { loan ->
            loan.user?.id == id
        }
    }

    fun getLoansWithExpiredEndDate(): List<Loan> {
        val currentDate = LocalDate.now()
        return loanRepository.findAll().filter { loan ->
            loan.endDate?.isBefore(currentDate) ?: false
        }
    }

    fun getLoanByItemId(itemId:Long): Loan?{
        return loanRepository.findByItemId(itemId)

    }



    companion object {
        private val logger = LoggerFactory.getLogger(LoanService::class.java)
    }
}