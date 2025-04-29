package fr.eni.tp.projet.controller;

import fr.eni.tp.projet.bll.AuctionsService;
import fr.eni.tp.projet.bo.User;
import fr.eni.tp.projet.dal.UserDAO;
import fr.eni.tp.projet.dal.impl.UserDAOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuctionController {
    private final static Logger logger = LoggerFactory.getLogger(AuctionController.class);
    private final UserDAOImpl userDAOImpl;

    private final AuctionsService auctionsService;

    public AuctionController(UserDAOImpl userDAOImpl, AuctionsService auctionsService) {
        this.userDAOImpl = userDAOImpl;
        this.auctionsService = auctionsService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    //Mapping auctions

    @GetMapping("/auctions/list")
    public String auctions(Model model) {


        return "/auctions/list";
    }

    //Mapping auctions/view?id

    @GetMapping("/auctions/view")
    public String auctionsById() {
        return "/auctions/view";
    }

    //Mapping auctions/create

    @GetMapping("/auctions/create")
    public String auctionsCreate() {
        return "/auctions/create";
    }

    //Mapping auctions/cancel?id

    @GetMapping("/auctions/cancel")
    public String auctionsCancel() {
        return "/auctions/cancel";
    }

    //Mapping auctions

}
