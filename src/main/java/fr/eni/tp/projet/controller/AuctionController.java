package fr.eni.tp.projet.controller;

import fr.eni.tp.projet.bll.*;
import fr.eni.tp.projet.bo.Article;
import fr.eni.tp.projet.bo.Bid;
import fr.eni.tp.projet.bo.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
public class AuctionController {
    private final ArticleService articleService;
    private final AuctionsService auctionsService;
    private final UserService userService;
    private final PickUpService pickUpService;
    private final CategoriesService categoriesService;
    private final BidService bidService;

    public AuctionController(ArticleService articleService, AuctionsService auctionsService, UserService userService, PickUpService pickUpService, CategoriesService categoriesService, BidService bidService) {
        this.articleService = articleService;
        this.auctionsService = auctionsService;
        this.userService = userService;
        this.pickUpService = pickUpService;
        this.categoriesService = categoriesService;
        this.bidService = bidService;
    }


    @GetMapping("/bid/ongoing")
    public String allBid(Model model, Authentication authentication) {
        // Récupère l'utilisateur connecté via Spring Security
        String username = authentication.getName();
        // Exemple : récupération de l'utilisateur depuis le service
        User user = userService.findByUsername(username);
        // Exemple : récupérer les enchères de l'utilisateur selon le statut
        List<Article> bids = auctionsService.MyOpenBid(user.getIdUser());
        // Ajoute les données au modèle
        model.addAttribute("bids", bids);
        // Retourne la vue Thymeleaf
        return "/auctions/list";
    }

    @GetMapping("/bid/win")
    public String WinBid(Model model, Authentication authentication) {
        // Récupère l'utilisateur connecté via Spring Security
        String username = authentication.getName();

        // Exemple : récupération de l'utilisateur depuis le service
        User user = userService.findByUsername(username);

        // Exemple : récupérer les enchères de l'utilisateur selon le statut
        List<Article> bids = auctionsService.MyWinAuctions(user.getIdUser());

        // Ajoute les données au modèle
        model.addAttribute("bids", bids);

        // Retourne la vue Thymeleaf
        return "/auctions/list";
    }




}
