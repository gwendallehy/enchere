package fr.eni.tp.projet.bo;

import org.springframework.web.multipart.MultipartFile;

public class AuctionForm {
    private Article article;
    private Pickup pickup;
    private MultipartFile picture; //MultipartFile pour gèrer l'importation d'image


    public MultipartFile getPicture() {
        return picture;
    }
    public void setPicture(MultipartFile picture) {
        this.picture = picture;
    }




    public AuctionForm() {
    }

    public AuctionForm(Article article, Pickup pickup) {
        this.article = article;
        this.pickup = pickup;
    }

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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AuctionForm{");
        sb.append("article=").append(article);
        sb.append(", pickup=").append(pickup);
        sb.append('}');
        return sb.toString();
    }
}
