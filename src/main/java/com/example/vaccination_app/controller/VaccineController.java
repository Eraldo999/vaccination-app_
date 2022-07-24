package com.example.vaccination_app.controller;

import com.example.vaccination_app.model.Vaccine;
import com.example.vaccination_app.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class VaccineController {

   private final VaccineService vaccineService;

   @Autowired
   public VaccineController(VaccineService vaccineService) {
      this.vaccineService = vaccineService;
   }

   @GetMapping("/")
     public String dashboard (Model model){
        model.addAttribute("vaccines",vaccineService.getAllVaccines());
        return "admin/dashboard";
   }

   @GetMapping("/vaccine-form")
   public String vaccineForm(Model model){
      Vaccine vaccine = new Vaccine();
      model.addAttribute("vaccine", vaccine);
      return "admin/vaccine-form";
   }

   @GetMapping("/update-form")
   public String updateForm (@RequestParam("id") long id, Model model){
      Optional<Vaccine> optVaccine = vaccineService.getVaccineById(id);
      Vaccine vaccine = optVaccine.get();
      model.addAttribute("vaccine", vaccine);
      return  "admin/vaccine-form";
   }

   @PostMapping("/save")
   public String saveVaccine (@ModelAttribute("vaccine") Vaccine vaccine){
      vaccineService.save(vaccine);
      return "redirect:/admin/";
   }

   @GetMapping("/delete")
   public String deleteVaccine (@RequestParam("id") long id){
      vaccineService.deleteVaccineById(id);
      return "redirect:/admin/";
   }


}
