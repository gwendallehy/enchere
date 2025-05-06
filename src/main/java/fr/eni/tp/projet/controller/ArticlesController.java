package fr.eni.tp.projet.controller;

import fr.eni.tp.projet.bll.*;
import fr.eni.tp.projet.bo.*;
import fr.eni.tp.projet.exception.BusinessException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        List<User> users = userService.getAllUsers();
        List<Categories> category = categoriesService.getAllCategories();

        Map<Long, User> userMap = users.stream()
                .collect(Collectors.toMap(User::getIdUser, user -> user));

        model.addAttribute("articles", articles);
        model.addAttribute("userMap", userMap);
        model.addAttribute("category", category);
        return "/auctions/list";
    }


        @PostMapping("/auctions/list/recherche")
        public String Filter(Model model,
                             @RequestParam("name") String filtre1,
                             @RequestParam(value = "category", defaultValue = "0") long filtre2
        ) {

        List<Article> articles = articleService.FindFilter(filtre1,filtre2);
        List<User> users = userService.getAllUsers();
        List<Categories> category = categoriesService.getAllCategories();

        Map<Long, User> userMap = users.stream()
                .collect(Collectors.toMap(User::getIdUser, user -> user));

        model.addAttribute("articles", articles);
        model.addAttribute("userMap", userMap);
        model.addAttribute("category", category);
        return "/auctions/list";
    }



    @GetMapping("/auctions/list/user")
    public String auctionsByUser(Model model, Authentication authentication) {
        long id = userService.findByUsername(authentication.getName()).getIdUser();
        List<Article> articles = articleService.findSalesByUser(id);
        List<User> users = userService.getAllUsers();

        Map<Long, User> userMap = users.stream()
                .collect(Collectors.toMap(User::getIdUser, user -> user));

        model.addAttribute("articles", articles);
        model.addAttribute("userMap", userMap);
        return "/auctions/list";
    }

    @GetMapping("/auctions/list/user/{status}")
    public String auctionsByUserAndStatus(@PathVariable("status") String status ,Model model, Authentication authentication) {
        List<String> validStatus = Arrays.asList("NC", "EC", "TR");
        if (!validStatus.contains(status.toUpperCase())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status");
        }

        long user_id = userService.findByUsername(authentication.getName()).getIdUser();

        List<Article> articles = articleService.findSalesByUserAndStatus(user_id, status.toUpperCase());
        List<User> users = userService.getAllUsers();

        // Create a map to associate user IDs with User objects
        Map<Long, User> userMap = users.stream()
                .collect(Collectors.toMap(User::getIdUser, user -> user));

        // Pass the articles and userMap to the template
        model.addAttribute("articles", articles);
        model.addAttribute("userMap", userMap);

        return "/auctions/list";
    }

    @GetMapping("/auctions/view")
    public String auctionsById(@RequestParam(name = "id") int id, Model model) {
        Article article = articleService.findArticleById(id);
        model.addAttribute("article", article);
        return "/auctions/view";
    }

    @GetMapping("/auctions/create")
    public String afficherFormulaireCreation(Model model, Authentication authentication) {
        AuctionForm form = new AuctionForm();
        form.setArticle(new Article());


        String username = authentication.getName();
        User user = userService.findByUsername(username);

        Pickup pickup = new Pickup();
        pickup.setStreet(user.getStreet());
        pickup.setCity(user.getCity());
        pickup.setPostalCode(user.getPostalCode());
        form.setPickup(pickup);

        List<Categories> categories = categoriesService.getAllCategories();
        model.addAttribute("auctionForm", form);
        model.addAttribute("categories", categories);
        model.addAttribute("user", user);

        return "/auctions/create";
    }

    @PostMapping("/auctions/create")
    public String creerArticle(
            @Valid @ModelAttribute("auctionForm") AuctionForm auctionForm,
            BindingResult bindingResult,
            Authentication authentication,
            Model model
    ) {
        if (!bindingResult.hasErrors()) {
            try {
                Article article = auctionForm.getArticle();
                Pickup pickup = auctionForm.getPickup();

                // User user = (User) authentication.getPrincipal(); // Assure-toi que User implémente UserDetails
                String username = authentication.getName();
                User user = userService.findByUsername(username);
                articleService.sellAnArticle(article, (int) user.getIdUser());

                pickup.setIdPickup(article.getIdArticle());
                pickUpService.createPickUp(pickup);
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
    public String auctionsCancel(Model model, Authentication authentication) {
        // Récupère le nom d'utilisateur (ou email, ou identifiant) de l'utilisateur connecté
        String username = authentication.getName();
        long user = userService.findByUsername(username).getIdUser();

        // Suppose que tu as un service qui permet de récupérer les articles de l'utilisateur
        List<Article> userArticles = articleService.findSalesByUser(user);
        System.out.println(userArticles);
        // Ajoute les articles au modèle pour les afficher dans la vue
        model.addAttribute("articles", userArticles);

        return "/auctions/cancel";
    }

    @PostMapping("/cancel-sell/{id}")
    public String cancelASell(@PathVariable("id") int id) {
        articleService.cancelASell(id); // méthode du service qui annule la vente
        return "redirect:/auctions/list"; // redirige vers la page des articles
    }




    private boolean estAdmin(Authentication auth) {
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}
