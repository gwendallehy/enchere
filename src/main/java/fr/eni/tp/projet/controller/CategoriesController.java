package fr.eni.tp.projet.controller;

import fr.eni.tp.projet.bll.*;
import fr.eni.tp.projet.bo.Categories;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CategoriesController {

    private final ArticleService articleService;
    private final AuctionsService auctionsService;
    private final UserService userService;
    private final PickUpService pickUpService;
    private final CategoriesService categoriesService;

    public CategoriesController(ArticleService articleService, AuctionsService auctionsService, UserService userService, PickUpService pickUpService, CategoriesService categoriesService) {
        this.articleService = articleService;
        this.auctionsService = auctionsService;
        this.userService = userService;
        this.pickUpService = pickUpService;
        this.categoriesService = categoriesService;
    }

    @GetMapping("/admin/categories")
    public String afficherListeCategories(Authentication authentication, Model model) {
        List<Categories> categories = categoriesService.getAllCategories();
        model.addAttribute("categories", categories);
        return "/admin/categoriesList";
    }

    @GetMapping("/admin/categories/form")
    public String afficherFormulaireCategorie(@RequestParam(value = "id", required = false) Long id, Model model) {
        Categories categorie = (id != null) ? categoriesService.getCategoryById(id) : new Categories();
        model.addAttribute("categorie", categorie);
        return "/admin/categoriesForm";
    }

    @PostMapping("/admin/categories/create")
    public String creerCategorie(@ModelAttribute Categories categorie) {
        categoriesService.createCategory(categorie);
        return "redirect:/admin/categories"; // Corrigé ici
    }

    @PostMapping("/admin/categories/update")
    public String modifierCategorie(@ModelAttribute Categories categorie) {
        categoriesService.updateCategory(categorie);
        return "redirect:/admin/categories"; // Corrigé ici aussi
    }

    @PostMapping("/admin/categories/delete")
    public String supprimerCategorie(@RequestParam("id") long id) {
        categoriesService.deleteCategory(id);
        return "redirect:/admin/categories"; // Corrigé
    }


    private boolean estAdmin(Authentication auth) {
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}
