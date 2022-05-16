package zatribune.example.activemq.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import zatribune.example.activemq.db.entities.User;
import zatribune.example.activemq.service.SecurityService;
import zatribune.example.activemq.service.UserService;

import javax.validation.Valid;


@Slf4j
@Controller
public class MainController {

    private final UserService userService;
    private final SecurityService securityService;

    @Autowired
    public MainController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @GetMapping({"/", "/home"})
    public String welcome(Model model) {

        return "home";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm")@Valid User userForm, BindingResult bindingResult) {

        //customized check
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())){
            log.error("Passwords don't match.");
            bindingResult.rejectValue("passwordConfirm","","Password Fields Must match.");
        }
        if (bindingResult.hasErrors()){
            return "registration";
        }
        userService.save(userForm);
        //todo: add account confirmation
        //securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());
        return "redirect:/home";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (securityService.isAuthenticated()) {
            log.debug("login status:User Already logged in.");
            // if already authenticated ->back to main page
            return "redirect:/";
        }

        if (error != null)//there's an error passed
            model.addAttribute("error", "Your username/password is invalid.");

        if (logout != null)//there's a message passed
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }
}
