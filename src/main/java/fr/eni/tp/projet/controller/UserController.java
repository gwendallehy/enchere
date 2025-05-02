package fr.eni.tp.projet.controller;

import fr.eni.tp.projet.bll.UserService;
import fr.eni.tp.projet.bo.User;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    // Affiche le profil connecté
    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        model.addAttribute("user", user);
        return "/users/profile";
    }


    // Affiche le détail d’un utilisateur via ID
    @GetMapping("/viewProfile")
    public String checkProfile(@RequestParam("id") long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "/users/viewProfile";
    }

    // Affiche le formulaire de création
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("user", new User());
        return "/users/create";
    }

    // Traite la création
    @PostMapping("/create")
    public String createUser(@Valid @ModelAttribute("user") User user, BindingResult result) {
        if (result.hasErrors()) {
            return "/users/create";
        }
        userService.createUser(user);
        return "redirect:/users";
    }

    // Supprime un utilisateur
    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}
