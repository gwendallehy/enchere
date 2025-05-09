package fr.eni.tp.projet.form;

import fr.eni.tp.projet.bo.Article;
import fr.eni.tp.projet.bo.Pickup;
import org.springframework.web.multipart.MultipartFile;

    public class AuctionForm {
        private Article article;
        private Pickup pickup;
        private MultipartFile picture;

        public Article getArticle() {
            return article;
        }

        public void setArticle(Article article) {
            this.article = article;
        }

        public Pickup getPickup() {
            return pickup;
        }

        public void setPickup(Pickup pickup) {
            this.pickup = pickup;
        }

        public MultipartFile getPicture() {
            return picture;
        }

        public void setPicture(MultipartFile picture) {
            this.picture = picture;
        }
    }
