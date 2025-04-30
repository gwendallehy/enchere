package fr.eni.tp.projet.controller;

import fr.eni.tp.projet.bll.*;
import fr.eni.tp.projet.bll.impl.AuctionsServiceImpl;
import fr.eni.tp.projet.bo.Article;
import fr.eni.tp.projet.bo.User;
import fr.eni.tp.projet.dal.ArticleDAO;
import fr.eni.tp.projet.dal.UserDAO;
import fr.eni.tp.projet.dal.impl.UserDAOImpl;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AuctionController {
    private final static Logger logger = LoggerFactory.getLogger(AuctionController.class);

    private final ArticleService articleService;
    private final AuctionsService auctionsService;
    private final UserService userService;
    private final PickUpService pickUpService;
    private final CategoriesService categoriesService;

    public AuctionController(ArticleService articleService, AuctionsService auctionsService, UserService userService, PickUpService pickUpService, CategoriesService categoriesService) {
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

    //Mapping auctions

    @GetMapping("/auctions/list")
    public String auctions(Model model) {

        List<Article> articles = this.articleService.findAllArticles();
        model.addAttribute("articles", articles);

        System.out.println(articles);
        return "/auctions/list";
    }

    //Mapping auctions/view?id

    @GetMapping("/auctions/view")
    public String auctionsById(
            @RequestParam(name="id", required = true) int id,
            Model model
    ) {
        Article article =  this.articleService.findArticleById(id);
        model.addAttribute("article", article);
        return "/auctions/view";
    }

    //Mapping auctions/create

    @GetMapping("/auctions/create")
    public String auctionsCreate(
            @Valid @ModelAttribute("article") Article article,
            BindingResult bindingResult,
            Authentication authentication,
            Model model
    ) {
        if (!bindingResult.hasErrors()) {
            try {
                User user = (User) authentication.getPrincipal(); // Ensure User implements UserDetails
                long user_id = user.getIdUser();

                articleService.sellAnArticle(article, (int) user_id);
                return "redirect:/auctions"; // or wherever you want to go after successful creation

            } catch (BusinessException exception) {
                exception.getKeys().forEach(key -> {
                    ObjectError error = new ObjectError("globalError", key);
                    bindingResult.addError(error);
                });
            }
        }

        // In case of validation or business errors
        return "/auctions/create"; // replace with your actual form view name
    }


    //Mapping auctions/cancel?id

    @GetMapping("/auctions/cancel")
    public String auctionsCancel() {
        return "/auctions/cancel";
    }

    //Mapping auctions

}
