package fr.eni.tp.projet.bo;

public class User {
    private long idUser;
    private String pseudo;
    private String name;
    private String firstName;
    private String email;
    private long phone;
    private String street;
    private String city;
    private Long postalCode;
    private String password;
    private long credit;
    private int administrator;

    public User(long idUser, String pseudo, String name, String firstName, String email, long phone, String street, String city, Long postalCode, String password, long credit, int administrator) {
        this.idUser = idUser;
        this.pseudo = pseudo;
        this.name = name;
        this.firstName = firstName;
        this.email = email;
        this.phone = phone;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.password = password;
        this.credit = credit;
        this.administrator = administrator;
    }

    public User() {

    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
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

    public Long getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Long postalCode) {
        this.postalCode = postalCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getCredit() {
        return credit;
    }

    public void setCredit(long credit) {
        this.credit = credit;
    }

    public int getAdministrator() {
        return administrator;
    }

    public void setAdministrator(int administrator) {
        this.administrator = administrator;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("idUser=").append(idUser);
        sb.append(", pseudo='").append(pseudo).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", phone=").append(phone);
        sb.append(", street='").append(street).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", postalCode='").append(postalCode).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", credit=").append(credit);
        sb.append(", administrator=").append(administrator);
        sb.append('}');
        return sb.toString();
    }
}
