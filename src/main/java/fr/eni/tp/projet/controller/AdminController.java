package fr.eni.tp.projet.controller;

import fr.eni.tp.projet.bll.ArticleService;
import fr.eni.tp.projet.bll.CategoriesService;
import fr.eni.tp.projet.bll.UserService;
import fr.eni.tp.projet.bo.Article;
import fr.eni.tp.projet.bo.Categories;
import fr.eni.tp.projet.bo.Categories;
import fr.eni.tp.projet.bo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ArticleService articleService;
    private final CategoriesService categoryService;
    private final UserService userService;

    public AdminController(ArticleService articleService, CategoriesService categoryService, UserService userService) {
        this.articleService = articleService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    // Page principale d'administration avec les 6 options
    @GetMapping
    public String adminHome() {
        return "admin/dashboard"; // Vue avec 6 options
    }

    // Rediriger vers création d'article
    @GetMapping("/addArticle")
    public String redirectToAddArticle() {
        return "redirect:/auctions/create";
    }

    // Rediriger vers création d'utilisateur
    @GetMapping("/addUser")
    public String redirectToAddUser() {
        return "redirect:/users/create";
    }

    // Affiche la vue de création de catégorie
    @GetMapping("/addCategory")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new Categories());
        return "admin/addCategory";
    }

    @PostMapping("/addCategory")
    public String addCategory(@ModelAttribute Categories category) {
        categoryService.createCategory(category);
        return "redirect:/admin";
    }

    // Page pour supprimer des articles, utilisateurs, et catégories
    @GetMapping("/manage")
    public String manageDeletions(Model model) {
        model.addAttribute("articles", articleService.findAllArticles());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("users", userService.getAllUsers());
        return "admin/teamlyf";
    }

    @PostMapping("/deleteArticle")
    public String deleteArticle(@RequestParam int id) {
        articleService.cancelASell(id);
        return "redirect:/admin/manage";
    }

    @PostMapping("/deleteCategory")
    public String deleteCategory(@RequestParam int id) {
        categoryService.deleteCategory(id);
        return "redirect:/admin/manage";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam long id) {
        userService.deleteUser(id);
        return "redirect:/admin/manage";
    }
}
