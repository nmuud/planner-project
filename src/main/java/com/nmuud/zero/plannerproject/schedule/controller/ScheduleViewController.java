package com.nmuud.zero.plannerproject.schedule.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ScheduleViewController {

    @GetMapping("/schedules/view")
    public String showSchedulePage(Model model) {
        return "schedule";
    }
}
