package fr.eni.tp.projet.bo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Article {
    private long idArticle;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private long betAPrice;
    private long salePrice;
    private String status;
    private String picture;
    private long user_id;

    private Categories category;
    private Pickup pickup;

    public Article() {
    }

    public Article(long idArticle, String name, String description, LocalDate startDate, LocalDate endDate, long betAPrice, long salePrice, String status, String picture, long user_id, Categories category, Pickup pickup) {
        this.idArticle = idArticle;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.betAPrice = betAPrice;
        this.salePrice = salePrice;
        this.status = status;
        this.picture = picture;
        this.user_id = user_id;
        this.category = category;
        this.pickup = pickup;
    }

    public long getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(long idArticle) {
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
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

    public long getUser() {
        return user_id;
    }

    public void setUser(long user_id) {
        this.user_id = user_id;
    }

    public String getFormattedStartDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE d MMMM yyyy", Locale.FRENCH);

        return this.startDate.format(formatter);
    }

    public String getFormattedEndDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE d MMMM yyyy", Locale.FRENCH);

        return this.endDate.format(formatter);
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
        sb.append(", user=").append(user_id);
        sb.append(", category=").append(category);
        sb.append(", pickup=").append(pickup);
        sb.append('}');
        return sb.toString();
    }
}
