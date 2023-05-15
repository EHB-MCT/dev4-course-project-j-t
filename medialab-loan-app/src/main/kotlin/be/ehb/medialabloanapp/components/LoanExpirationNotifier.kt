package be.ehb.medialabloanapp.components

import be.ehb.medialabloanapp.services.EmailService
import be.ehb.medialabloanapp.services.LoanService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class LoanExpirationNotifier(private val loanService: LoanService, private val emailService: EmailService) {

    @Scheduled(fixedRate = 600000) // Execute daily at midnight
    fun notifyLoanExpiration() {
        val loans = loanService.getLoansWithExpiredEndDate()
        for (loan in loans) {
            val user = loan.user
            val userEmail = user?.email


            if (userEmail != null) {
                val loanDescription = loan.description
                val emailContent = "Dear ${user.firstName} ${user.lastName},\n\nYour loan with description '$loanDescription' has exceeded its end date. Please return the item as soon as possible.\n\nRegards,\nThe Medialab Inventory Admin Team"
                emailService.sendEmail(userEmail, "Loan Expiration Notification", emailContent)
            }
        }
    }

}