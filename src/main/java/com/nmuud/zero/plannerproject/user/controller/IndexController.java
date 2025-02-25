package com.nmuud.zero.plannerproject.user.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.nmuud.zero.plannerproject.user.service.LoginService.LOGIN_SESSION_KEY;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model, HttpSession httpSession,
                        @RequestParam(required = false) String redirect) {
        if (httpSession.getAttribute(LOGIN_SESSION_KEY) != null) {
            return "redirect:" + (redirect != null ? redirect : "/schedules/view");
        }
        model.addAttribute("isSignIn", false);
        return "index";
    }
}
