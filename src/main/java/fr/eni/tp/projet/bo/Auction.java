package fr.eni.tp.projet.bo;

import java.util.List;

public class Auction {
    private int idAuction;
    private Article article;
    private Bid bids;

    public int getIdAuction() {
        return idAuction;
    }

    public void setIdAuction(int idAuction) {
        this.idAuction = idAuction;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Bid getBids() {
        return bids;
    }

    public void setBids(Bid bids) {
        this.bids = bids;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Auction{");
        sb.append("article=").append(article);
        sb.append(", bids=").append(bids);
        sb.append('}');
        return sb.toString();
    }
}
