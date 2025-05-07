package fr.eni.tp.projet.controller;

import fr.eni.tp.projet.bll.UserService;
import fr.eni.tp.projet.bo.User;
import fr.eni.tp.projet.exception.BusinessException;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        return "/users/create";
    }

    // Traite la création
    @PostMapping("/create")
    public String createUser(@Valid @ModelAttribute("user") User user, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "/users/create";
        }

        try {
            userService.createUser(user);
            return "redirect:/login";
        } catch (BusinessException e) {
            redirectAttributes.addFlashAttribute("confirmPasswordError", String.join(", ", e.getKeys()));
            redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/users/create";
        }
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

    // Formulaire de changement de mot de passe
    @GetMapping("/change-password")
    public String showChangePasswordForm() {
        return "/users/change-password";
    }

    // Méthode pour traiter la soumission du formulaire de changement de mot de passe
    @PostMapping("/change-password")
    public String changePassword(@RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Authentication authentication, Model model) {

        System.out.println(currentPassword);
        System.out.println(newPassword);
        System.out.println(confirmPassword);

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Les mots de passe ne correspondent pas.");
            System.out.println("Les mots de passe ne correspondent pas.");
            return "/users/change-password";
        }

        String username = authentication.getName();

        try {
            userService.changePassword(username, currentPassword, newPassword);
            System.out.println("Password changed");
            return "redirect:/users/profile";  // Redirection vers le profil de l'utilisateur après un changement réussi
        } catch (BusinessException e) {
            model.addAttribute("error", e.getMessage());
            System.out.println("Password NON");
            return "/users/change-password";  // Retourner sur la page avec le message d'erreur
        }
    }

    // Formulaire mot de passe oublié
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "users/forgot-password";
    }

    // Traitement du formulaire
    @PostMapping("/forgot-password")
    public String resetPassword(@RequestParam("pseudo") String username,
                                @RequestParam("newPassword") String newPassword,
                                Model model, RedirectAttributes redirectAttributes) {
        try {
            userService.resetPassword(username, newPassword);
            model.addAttribute("success", "Mot de passe réinitialisé !");
            return "redirect:/login";
        } catch (BusinessException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "users/forgot-password";
    }

}
