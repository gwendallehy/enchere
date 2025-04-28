package fr.eni.tp.projet.bo;

public class User {
    private long idUser;
    private Person person;
    private Adress adress;
    private String password;
    private long credit;
    private int administrator;

    public User(long idUser, Person person, Adress adress, String password, long credit, int administrator) {
        this.idUser = idUser;
        this.person = person;
        this.adress = adress;
        this.password = password;
        this.credit = credit;
        this.administrator = administrator;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
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
        sb.append(", person=").append(person);
        sb.append(", adress=").append(adress);
        sb.append(", password='").append(password).append('\'');
        sb.append(", credit=").append(credit);
        sb.append(", administrator=").append(administrator);
        sb.append('}');
        return sb.toString();
    }
}
