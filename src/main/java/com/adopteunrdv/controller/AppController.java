package com.adopteunrdv.controller;

import com.adopteunrdv.model.AppUser;
import com.adopteunrdv.model.Appointment;
import com.adopteunrdv.service.AppUserService;
import com.adopteunrdv.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class AppController {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(AppUser user) {
        appUserService.save(user);
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String loginUser(AppUser user) {
        // Logique de connexion ici (à implémenter)
        return "redirect:/calendar";
    }

    @GetMapping("/calendar")
    public String calendar(@RequestParam(required = false) LocalDate date, Model model) {
        if (date == null) {
            date = LocalDate.now();
        }
        List<Appointment> appointments = appointmentService.findByDate(date);
        model.addAttribute("date", date);
        model.addAttribute("appointments", appointments);
        return "calendar";
    }


    @PostMapping("/book")
    public String bookAppointment(Appointment appointment) {
        appointmentService.save(appointment);
        return "redirect:/calendar";
    }
}