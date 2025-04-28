package fr.eni.tp.projet.bo;

public class Categories {
    private long idCategory;
    private String wording;

    public Categories(long idCategory, String wording) {
        this.idCategory = idCategory;
        this.wording = wording;
    }

    public Categories() {

    }

    public long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(long idCategory) {
        this.idCategory = idCategory;
    }

    public String getWording() {
        return wording;
    }

    public void setWording(String wording) {
        this.wording = wording;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Categories{");
        sb.append("idCategory=").append(idCategory);
        sb.append(", wording='").append(wording).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
