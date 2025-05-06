package fr.eni.tp.projet.bo;

public class AuctionForm {
    private Article article;
    private Pickup pickup;

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
