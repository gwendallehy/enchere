package fr.eni.tp.projet.controller;

import fr.eni.tp.projet.bll.*;
import fr.eni.tp.projet.bo.Article;
import fr.eni.tp.projet.bo.User;
import fr.eni.tp.projet.exception.BusinessException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ArticlesController {
    private static final Logger logger = LoggerFactory.getLogger(ArticlesController.class);

    private final ArticleService articleService;
    private final AuctionsService auctionsService;
    private final UserService userService;
    private final PickUpService pickUpService;
    private final CategoriesService categoriesService;

    public ArticlesController(ArticleService articleService, AuctionsService auctionsService, UserService userService, PickUpService pickUpService, CategoriesService categoriesService) {
        this.articleService = articleService;
        this.auctionsService = auctionsService;
        this.userService = userService;
        this.pickUpService = pickUpService;
        this.categoriesService = categoriesService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/auctions/list")
    public String auctions(Model model) {
        List<Article> articles = articleService.findAllArticles();
        model.addAttribute("articles", articles);
        return "/auctions/list";
    }

    @GetMapping("/auctions/view")
    public String auctionsById(@RequestParam(name = "id") int id, Model model) {
        Article article = articleService.findArticleById(id);
        model.addAttribute("article", article);
        return "/auctions/view";
    }

    @GetMapping("/auctions/create")
    public String afficherFormulaireCreation(Model model) {
        model.addAttribute("article", new Article());
        model.addAttribute("categories", categoriesService.getAllCategories());
        return "/auctions/create";
    }

    @PostMapping("/auctions/create")
    public String creerArticle(
            @Valid @ModelAttribute("article") Article article,
            BindingResult bindingResult,
            Authentication authentication,
            Model model
    ) {
        if (!bindingResult.hasErrors()) {
            try {
                User user = (User) authentication.getPrincipal(); // Assure-toi que User implémente UserDetails
                articleService.sellAnArticle(article, (int) user.getIdUser());
                return "redirect:/auctions/list";

            } catch (BusinessException exception) {
                exception.getKeys().forEach(key ->
                        bindingResult.addError(new ObjectError("globalError", key))
                );
            }
        }

        // En cas d'erreurs
        model.addAttribute("categories", categoriesService.getAllCategories());
        return "/auctions/create";
    }

    @GetMapping("/auctions/cancel")
    public String auctionsCancel() {
        return "/auctions/cancel";
    }

    private boolean estAdmin(Authentication auth) {
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}
