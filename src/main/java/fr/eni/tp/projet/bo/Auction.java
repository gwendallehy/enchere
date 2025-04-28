package fr.eni.tp.projet.bo;

import java.util.List;

public class Auction {
    private int idAuction;
    private Article article;
    private List<Bid> bids;

    public Auction(Article article, List<Bid> bids) {
        this.article = article;
        this.bids = bids;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
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
