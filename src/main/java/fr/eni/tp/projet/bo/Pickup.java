package fr.eni.tp.projet.bo;

public class Pickup {
    private long idPickup;
    private String street;
    private String city;
    private long postalCode;

    public Pickup(long idPickup, String street, String city, long postalCode) {
        this.idPickup = idPickup;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
    }

    public long getIdPickup() {
        return idPickup;
    }

    public void setIdPickup(long idPickup) {
        this.idPickup = idPickup;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public long getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(long postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Pickup{");
        sb.append("idPickup=").append(idPickup);
        sb.append(", street='").append(street).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", postalCode=").append(postalCode);
        sb.append('}');
        return sb.toString();
    }
}

