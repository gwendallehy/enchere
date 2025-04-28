package fr.eni.tp.projet.bo;

import java.util.ArrayList;

public class Bid {
    private String auctionDate;
    private long auctionAmount ;

    public Bid(String auctionDate, long auctionAmount) {
        this.auctionDate = auctionDate;
        this.auctionAmount = auctionAmount;
    }

    public String getAuctionDate() {
        return auctionDate;
    }

    public void setAuctionDate(String auctionDate) {
        this.auctionDate = auctionDate;
    }

    public long getAuctionAmount() {
        return auctionAmount;
    }

    public void setAuctionAmount(long auctionAmount) {
        this.auctionAmount = auctionAmount;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Bid{");
        sb.append("auctionDate='").append(auctionDate).append('\'');
        sb.append(", auctionAmount=").append(auctionAmount);
        sb.append('}');
        return sb.toString();
    }
}
