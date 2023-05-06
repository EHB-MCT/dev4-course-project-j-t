package be.ehb.medialabloanapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class MedialabLoanAppApplication

fun main(args: Array<String>) {
    runApplication<MedialabLoanAppApplication>(*args)


}



