package event.observability.zeebe.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class UserController {

    @GetMapping("/login")
    fun showLoginForm(): String {
        return "login"
    }

}