package be.ehb.medialabloanapp.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service


@Service
class EmailService(@Autowired private val javaMailSender: JavaMailSender) {



    fun sendEmail(recipient: String, subject: String, content: String) {
        // Add your email sending logic here
        val message = SimpleMailMessage()
        message.setTo(recipient)
        message.setSubject(subject)
        message.setText(content)
        javaMailSender.send(message)
    }

}