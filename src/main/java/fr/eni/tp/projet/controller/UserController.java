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
import java.util.Optional;

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
    @GetMapping("/viewProfile/{id}")
    public String checkProfile(@PathVariable("id") Long id, Model model) {
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
        try{
            userService.deleteUser(id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return "redirect:/login";
    }


    // Méthode pour afficher le formulaire de mise à jour
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id); // Assurez-vous que cette méthode retourne Optional<User>
        if (user != null) {
            model.addAttribute("user", user);
            return "users/update"; // vue Thymeleaf pour afficher le formulaire
        } else {
            return "redirect:/error";
        }
    }



    // Méthode pour traiter la mise à jour du formulaire
    @PostMapping("/update")
    public String processUpdate(@ModelAttribute("user") User user) {
        userService.updateUser(user); // Met à jour l'utilisateur dans la base de données
        return "redirect:/users/profile"; // Redirige vers une page de profil, par exemple
    }


}
