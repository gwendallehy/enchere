package fr.eni.tp.projet.bo;

public class Article {
    private int idArticle;
    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private long betAPrice;
    private long salePrice;
    private String status;
    private String picture;
    private User user;

    private Categories category;
    private Pickup pickup;

    public Article() {
    }

    public Article(int idArticle, String name, String description, String startDate, String endDate, long betAPrice, long salePrice, String status, String picture, User user, Categories category, Pickup pickup) {
        this.idArticle = idArticle;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.betAPrice = betAPrice;
        this.salePrice = salePrice;
        this.status = status;
        this.picture = picture;
        this.user = user;
        this.category = category;
        this.pickup = pickup;
    }

    public int getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public long getBetAPrice() {
        return betAPrice;
    }

    public void setBetAPrice(long betAPrice) {
        this.betAPrice = betAPrice;
    }

    public long getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(long salePrice) {
        this.salePrice = salePrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public Pickup getPickup() {
        return pickup;
    }

    public void setPickup(Pickup pickup) {
        this.pickup = pickup;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Article{");
        sb.append("idArticle=").append(idArticle);
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", startDate='").append(startDate).append('\'');
        sb.append(", endDate='").append(endDate).append('\'');
        sb.append(", betAPrice=").append(betAPrice);
        sb.append(", salePrice=").append(salePrice);
        sb.append(", status='").append(status).append('\'');
        sb.append(", picture='").append(picture).append('\'');
        sb.append(", user=").append(user);
        sb.append(", category=").append(category);
        sb.append(", pickup=").append(pickup);
        sb.append('}');
        return sb.toString();
    }
}
