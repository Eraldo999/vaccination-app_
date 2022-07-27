package com.example.vaccination_app.controller;

import com.example.vaccination_app.dto.BookingCreateDto;
import com.example.vaccination_app.exception.BadResourceException;
import com.example.vaccination_app.exception.ResourceNotFoundException;
import com.example.vaccination_app.model.Questions;
import com.example.vaccination_app.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Objects;

@Controller
@Slf4j
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;
    private final VaccineService vaccineService;
    private final VaccinationCenterService vaccinationCenterService;
    private final UserService userService;
    private final QuestionService questionService;

    @Autowired
    public BookingController(BookingService bookingService, VaccineService vaccineService,
                             VaccinationCenterService vaccinationCenterService, UserService userService, QuestionService questionService) {
        this.bookingService = bookingService;
        this.vaccineService = vaccineService;
        this.vaccinationCenterService = vaccinationCenterService;
        this.userService = userService;
        this.questionService = questionService;
    }

    @GetMapping("/list")
    public String getAllBokings(Model model) {
        model.addAttribute("questions", questionService.getQuestions(1L));
        model.addAttribute("bookings", bookingService.getAllBookings());

        return "admin/booking-list";
    }

    @RequestMapping("/dissaprove")
    public String dissaproveBookingById (@RequestParam("id") long id){
        bookingService.dissaproveBooking(id);
        return "redirect:/";
    }

    @RequestMapping("/approved")
    public String deleteAprovedBookingById (@RequestParam("id") long id){
        bookingService.acceptBooking(id);
        return "redirect:/";
    }

    @RequestMapping("/delete")
    public String deleteBookingById (@RequestParam("id") long id){
        bookingService.deleteBookingById(id);
        return "redirect:/";
    }

    @GetMapping("/new")
    public String newBooking(Model model, Principal principal) {
        var usr = (userService.getUserByPrincipal(principal)).get();
        if (Objects.isNull(usr.getAnswers())){
            return "redirect:/answers/";
        }
        if (bookingService.checkIfUserHasAnApprovedBooking(usr) == true){
            return "user/denied";
        }
        var userId = usr.getId();
        var vaccines = vaccineService.getVaccinesForUser(userId);
        var req = new BookingCreateDto();

        model.addAttribute("req", req);
        model.addAttribute("vaccines", vaccines);
        return "user/book";
    }

    @PostMapping("/create")
    public String newBooking(@Valid
                             @ModelAttribute("req") BookingCreateDto req,
                             BindingResult bindingResult,
                             Principal principal
    ) {

        if (bindingResult.hasErrors()) {
            return "redirect:/booking/new";
        }

    try {
        bookingService.createBooking(req, principal, req.getVaccineId());
        return "redirect:/";
    }
    catch (ResourceNotFoundException | BadResourceException ex) {
        log.error(ex.getMessage());
        return "user/book";
    }

    }

    @GetMapping("/getVaccinesOfCenter")
    public String getvaccines(@RequestParam("id") long id, Model model){
        var vaccines = vaccinationCenterService.getVaccinesForCenter(id);
        model.addAttribute("vaccines", vaccines);
        return "/";
    }

    @GetMapping("/get-bookings-user")
    public String getBookingsForUser (Model model, Principal principal){
        model.addAttribute("bookings",bookingService.getBookingsForUser(principal));
        return "user/bookings";
    }


}
