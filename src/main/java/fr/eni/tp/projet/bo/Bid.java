package fr.eni.tp.projet.bo;

public class Bid {
    private long bidIdUser;
    private long bidIdItem;
    private String bidDate;
    private long bidAmount;

    public Bid() {

    }

    public long getBidIdUser() {
        return bidIdUser;
    }

    public void setBidIdUser(long bidIdUser) {
        this.bidIdUser = bidIdUser;
    }

    public long getBidIdItem() {
        return bidIdItem;
    }

    public void setBidIdItem(long bidIdItem) {
        this.bidIdItem = bidIdItem;
    }

    public Bid(String auctionDate, long bidAmount) {
        this.bidDate = auctionDate;
        this.bidAmount = bidAmount;
    }

    public String getBidDate() {
        return bidDate;
    }

    public void setBidDate(String bidDate) {
        this.bidDate = bidDate;
    }

    public long getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(long bidAmount) {
        this.bidAmount = bidAmount;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Bid{");
        sb.append("bidDate='").append(bidDate).append('\'');
        sb.append(", bidAmount=").append(bidAmount);
        sb.append('}');
        return sb.toString();
    }
}
