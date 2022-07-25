package com.example.vaccination_app.controller;

import com.example.vaccination_app.dto.BookingCreateDto;
import com.example.vaccination_app.exception.BadResourceException;
import com.example.vaccination_app.exception.ResourceNotFoundException;
import com.example.vaccination_app.service.BookingService;
import com.example.vaccination_app.service.VaccinationCenterService;
import com.example.vaccination_app.service.VaccineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@Slf4j
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;
    private final VaccineService vaccineService;
    private final VaccinationCenterService vaccinationCenterService;

    @Autowired
    public BookingController(BookingService bookingService, VaccineService vaccineService, VaccinationCenterService vaccinationCenterService) {
        this.bookingService = bookingService;
        this.vaccineService = vaccineService;
        this.vaccinationCenterService = vaccinationCenterService;
    }

    @GetMapping("/list")
    public String getAllBokings(Model model) {
        model.addAttribute("bookings", bookingService.getAllBookings());

        return "admin/booking-list";
    }

    @RequestMapping("/delete")
    public String deleteBookingById (@RequestParam("id") long id){
        bookingService.deleteBookingById(id);
        return "redirect:/";
    }

    @RequestMapping("/approved")
    public String deleteAprovedBookingById (@RequestParam("id") long id){
        bookingService.acceptBooking(id);
        return "redirect:/";
    }

    @GetMapping("/new")
    public String newBooking(Model model, Principal principal) {

        var vaccines = vaccineService.getAllVaccines();
        var vaccinationCenters = vaccinationCenterService.getAllVaccinationCenters();
        var req = new BookingCreateDto();

        model.addAttribute("req", req);
        model.addAttribute("vaccines", vaccines);
        model.addAttribute("centers", vaccinationCenters);
        return "user/book";
    }

    @PostMapping("/create")
    public String newBooking(@Valid
                             @ModelAttribute("req") BookingCreateDto req,
                             BindingResult bindingResult,
                             Principal principal
    ) {

        if (bindingResult.hasErrors()) {
            return "redirect:/user/book";
        }

    try {
        bookingService.createBooking(req, principal, req.getVaccinationCenterId(), req.getVaccineId());
        return "redirect:/";
    }
    catch (ResourceNotFoundException | BadResourceException ex) {
        log.error(ex.getMessage());
        return "user/book";
    }

    }


}
