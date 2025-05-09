package fr.eni.tp.projet.controller;

import fr.eni.tp.projet.bll.*;
import fr.eni.tp.projet.bo.Article;
import fr.eni.tp.projet.bo.Bid;
import fr.eni.tp.projet.bo.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class AuctionController {

    private final ArticleService articleService;
    private final AuctionsService auctionsService;
    private final UserService userService;
    private final PickUpService pickUpService;
    private final CategoriesService categoriesService;
    private final BidService bidService;

    public AuctionController(ArticleService articleService, AuctionsService auctionsService, UserService userService,
                             PickUpService pickUpService, CategoriesService categoriesService, BidService bidService) {
        this.articleService = articleService;
        this.auctionsService = auctionsService;
        this.userService = userService;
        this.pickUpService = pickUpService;
        this.categoriesService = categoriesService;
        this.bidService = bidService;
    }

    // Méthode utilitaire pour associer chaque Bid à son Article
    private List<Map.Entry<Bid, Article>> getBidArticleEntries(List<Bid> bids, List<Article> articles) {
        Map<Long, Article> articleMap = articles.stream()
                .collect(Collectors.toMap(Article::getIdArticle, a -> a));

        return bids.stream()
                .filter(bid -> articleMap.containsKey(bid.getBidIdItem()))
                .map(bid -> Map.entry(bid, articleMap.get(bid.getBidIdItem())))
                .collect(Collectors.toList());
    }

    @GetMapping("/bid/all")
    public String allBid(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        List<Bid> bids = auctionsService.MyAllBid(user.getIdUser());
        List<Article> articles = articleService.findAllArticles();

        List<Map.Entry<Bid, Article>> bidArticleEntries = getBidArticleEntries(bids, articles);

        model.addAttribute("bidArticleEntries", bidArticleEntries);
        return "/bid/list";
    }

    @GetMapping("/bid/ongoing")
    public String ongoingBid(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        List<Bid> bids = auctionsService.MyOpenBid(user.getIdUser());
        List<Article> articles = articleService.findAllArticles();

        List<Map.Entry<Bid, Article>> bidArticleEntries = getBidArticleEntries(bids, articles);

        model.addAttribute("bidArticleEntries", bidArticleEntries);
        return "/bid/list";
    }

    @GetMapping("/bid/won")
    public String winBid(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        List<Bid> bids = auctionsService.MyWinAuctions(user.getIdUser());
        List<Article> articles = articleService.findAllArticles();

        List<Map.Entry<Bid, Article>> bidArticleEntries = getBidArticleEntries(bids, articles);

        model.addAttribute("bidArticleEntries", bidArticleEntries);
        return "/bid/list";
    }

    @GetMapping("/bid/bid")
    public String bidPage(@RequestParam("itemId") int itemId, Model model) {
        // Récupérer l'article avec l'ID
        Article article = articleService.findArticleById(itemId);

        // Récupérer la mise actuelle si elle existe
        Optional<Bid> currentBid = Optional.ofNullable(bidService.getBidByItemId(itemId));

        // Ajouter l'utilisateur qui a placé l'enchère dans le modèle
        currentBid.ifPresent(bid -> {
            User user = userService.getUserById(bid.getBidIdUser());
            model.addAttribute("user", user);  // Ajouter l'utilisateur au modèle
        });

        // Ajouter les attributs au modèle pour qu'ils soient accessibles dans la vue Thymeleaf
        model.addAttribute("article", article);  // Assurez-vous que l'article est ajouté au modèle
        model.addAttribute("currentBid", currentBid.orElse(null));  // Enchère actuelle, ou null si aucune enchère

        return "bid/bid";  // Retourner la vue Thymeleaf
    }

    @PostMapping("/bid/bid")
    public String placeBid(
            @RequestParam("itemId") Long itemId,
            @RequestParam("choiceBid") long bidPrice,
            Authentication authentication,
            RedirectAttributes redirectAttributes
    ) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        Article article = articleService.findArticleById(Math.toIntExact(itemId));
        LocalDateTime now = LocalDateTime.now();

        // ⛔ Règle 1 : l'utilisateur ne peut pas enchérir sur son propre article
        if (article.getUser() == user.getIdUser()) {
            redirectAttributes.addFlashAttribute("error", "Vous ne pouvez pas enchérir sur votre propre article.");
            return "redirect:/bid/bid?itemId=" + itemId;
        }

        // Vérification des dates d'enchère
        if (article.getStartDate() != null && article.getEndDate() != null) {
            LocalDateTime startDateTime = article.getStartDate().atStartOfDay();
            LocalDateTime endDateTime = article.getEndDate().atTime(23, 59, 59);

            if (now.isBefore(startDateTime) || now.isAfter(endDateTime)) {
                redirectAttributes.addFlashAttribute("error", "La date de l'enchère n'est pas valide.");
                return "redirect:/bid/bid?itemId=" + itemId;
            }
        }

        Optional<Bid> currentBidOpt = Optional.ofNullable(bidService.getBidByItemId(itemId));

        // ⛔ Règle 2 : l'utilisateur ne peut pas enchérir deux fois de suite
        if (currentBidOpt.isPresent() && currentBidOpt.get().getBidIdUser() == user.getIdUser()) {
            redirectAttributes.addFlashAttribute("error", "Vous ne pouvez pas enchérir deux fois de suite.");
            return "redirect:/bid/bid?itemId=" + itemId;
        }

        // Vérification du montant
        long minBid = currentBidOpt.map(Bid::getBidAmount).orElse(article.getBetAPrice());
        if (bidPrice <= minBid) {
            redirectAttributes.addFlashAttribute("error", "Votre mise doit être supérieure à " + minBid + " €.");
            return "redirect:/bid/bid?itemId=" + itemId;
        }

        // Création ou mise à jour de l'enchère
        Bid bid = new Bid();
        bid.setBidIdUser(user.getIdUser());
        bid.setBidIdItem(article.getIdArticle());
        bid.setBidAmount(bidPrice);
        String formattedDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        bid.setBidDate(formattedDate);

        bidService.createBid(bid);
        redirectAttributes.addFlashAttribute("success", "Votre enchère a été enregistrée !");
        return "redirect:/bid/bid?itemId=" + itemId;
    }

}
