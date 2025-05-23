package fr.eni.tp.projet.controller;

import fr.eni.tp.projet.bll.*;
        import fr.eni.tp.projet.bo.*;
        import fr.eni.tp.projet.exception.BusinessException;

import jakarta.validation.Valid;
import jdk.jfr.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import fr.eni.tp.projet.form.AuctionForm;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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


        List<Pickup> pickups = pickUpService.findAllPickup();
        // Création d'une Map associant les ID d'article aux pickups
        Map<Long, Pickup> pickupMap = pickups.stream()
                .collect(Collectors.toMap(Pickup::getIdPickup, pickup -> pickup));

        model.addAttribute("articles", articles);
        model.addAttribute("userMap", userMap);
        model.addAttribute("category", category);
        model.addAttribute("pickupMap", pickupMap);
        return "/auctions/list";
    }


        @PostMapping("/auctions/list/recherche")
        public String Filter(Model model,
                             @RequestParam("name") String filtre1,
                             @RequestParam(value = "category", defaultValue = "0") long filtre2
        ) {

        List<Article> articles = articleService.findFilter(filtre1,filtre2);
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
        List<Categories> categories = categoriesService.getAllCategories();
        // Create a map to associate user IDs with User objects
        Map<Long, User> userMap = users.stream()
                .collect(Collectors.toMap(User::getIdUser, user -> user));

        // Pass the articles and userMap to the template
        model.addAttribute("articles", articles);
        model.addAttribute("userMap", userMap);
        model.addAttribute("categories", categories);
        return "/auctions/list";
    }

    @PostMapping("/auctions/search")
    public String rechercher(@RequestParam("name_search") String name_search,
                             @RequestParam(value = "category_search", defaultValue = "0") long category_search,
                             Model model) {
        List<Article> articles = articleService.findFilter(name_search, category_search);
        List<User> users = userService.getAllUsers();
        List<Categories> category = categoriesService.getAllCategories();
        Map<Long, User> userMap = users.stream()
                .collect(Collectors.toMap(User::getIdUser, user -> user));

        List<Pickup> pickups = pickUpService.findAllPickup();
        // Création d'une Map associant les ID d'article aux pickups
        Map<Long, Pickup> pickupMap = pickups.stream()
                .collect(Collectors.toMap(Pickup::getIdPickup, pickup -> pickup));

        model.addAttribute("articles", articles);
        model.addAttribute("userMap", userMap);
        model.addAttribute("category", category);
        model.addAttribute("pickupMap", pickupMap);
        return "/auctions/list";
    }

    @GetMapping("/auctions/view")
    public String auctionsById(@RequestParam(name = "id") int id, Model model) {
        Article article = articleService.findArticleById(id);
        Pickup pickup = pickUpService.getPickUpByItemId(id);
        User user = userService.getUserById(articleService.findArticleById(id).getUser());

        model.addAttribute("article", article);
        model.addAttribute("pickup", pickup);
        model.addAttribute("user", user);

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
                MultipartFile picture = auctionForm.getPicture(); // <-- on récupère le fichier

                // Gérer l'image
                if (picture != null && !picture.isEmpty()) {
                    String originalFilename = picture.getOriginalFilename();
                    String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                    String newFileName = UUID.randomUUID().toString() + extension;
                    String userHome = System.getProperty("user.home"); // Sélectionne le répertoire utilisateur
                    Path uploadDir = Paths.get(userHome, "uploads"); // ou configurer ailleurs
                    if (!Files.exists(uploadDir)) {
                        Files.createDirectories(uploadDir);
                    }

                    Path filePath = uploadDir.resolve(newFileName);
                    // Ajout des logs pour déboguer
                    logger.info("Image sauvegardée sous : {}", filePath);  // Affiche le chemin du fichier sauvegardé
                    logger.info("Nom enregistré en BDD : {}", newFileName);  // Affiche le nom du fichier à enregistrer en BDD
                    logger.info("Répertoire absolu de sauvegarde des images : {}", uploadDir.toAbsolutePath());
                    System.out.println(uploadDir.toAbsolutePath());
                    try {
                        picture.transferTo(filePath.toFile());

                            logger.info("Image sauvegardée sous : {}", filePath);
                        } catch (IOException e) {
                            logger.error("Erreur lors du transfert de l'image", e);

                    }
                    article.setPicture(newFileName);
                }

                String username = authentication.getName();
                User user = userService.findByUsername(username);

                articleService.sellAnArticle(article, (int) user.getIdUser());

                pickup.setIdPickup(article.getIdArticle());
                pickUpService.createPickUp(pickup);

                return "redirect:/auctions/list";
            } catch (BusinessException | IOException exception) {
                if (exception instanceof BusinessException businessException) {
                    businessException.getKeys().forEach(key ->
                            bindingResult.addError(new ObjectError("globalError", key))
                    );
                } else {
                    logger.error("Erreur lors de l'upload de l'image", exception);
                    bindingResult.addError(new ObjectError("globalError", "Erreur lors de l'upload de l'image."));
                }
            }
        }

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