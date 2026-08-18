package an_paper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    // Nhận request /login và trả về giao diện file login.html
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // Chỏ tới src/main/resources/templates/login.html
    }

    // Nhận request /register và trả về giao diện file register.html
    @GetMapping("/register")
    public String registerPage() {
        return "register"; // Chỏ tới src/main/resources/templates/register.html
    }
}