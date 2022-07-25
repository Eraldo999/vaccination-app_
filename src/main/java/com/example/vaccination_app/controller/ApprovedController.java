package com.example.vaccination_app.controller;

import com.example.vaccination_app.service.ApprovedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/approved")
public class ApprovedController {
    private final ApprovedService approvedService;

    @Autowired
    public ApprovedController(ApprovedService approvedService) {
        this.approvedService = approvedService;
    }

    @GetMapping("/")
    public String getAll(Model model){
        var approved = approvedService.getAll();
        model.addAttribute("approved", approved);
        return "admin/approved";
    }

    @RequestMapping("/second-appoitment")
    public String secondAppoitment(@RequestParam("id") long id){
        approvedService.getSecondAppointment(id);
        return "admin/approved";
    }

}
