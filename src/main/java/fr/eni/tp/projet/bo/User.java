package fr.eni.tp.projet.bo;

public class User {
    private long noUtilisateur;
    private Personne personne;
    private Adresse adresse;
    private String motDePasse;
    private long credit;
    private boolean administrateur;

    public User(long noUtilisateur, Personne personne, Adresse adresse, String motDePasse, long credit, boolean administrateur) {
        this.noUtilisateur = noUtilisateur;
        this.personne = personne;
        this.adresse = adresse;
        this.motDePasse = motDePasse;
        this.credit = credit;
        this.administrateur = administrateur;
    }

    public long getNoUtilisateur() {
        return noUtilisateur;
    }

    public void setNoUtilisateur(long noUtilisateur) {
        this.noUtilisateur = noUtilisateur;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public long getCredit() {
        return credit;
    }

    public void setCredit(long credit) {
        this.credit = credit;
    }

    public boolean isAdministrateur() {
        return administrateur;
    }

    public void setAdministrateur(boolean administrateur) {
        this.administrateur = administrateur;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("noUtilisateur=").append(noUtilisateur);
        sb.append(", personne=").append(personne);
        sb.append(", adresse=").append(adresse);
        sb.append(", motDePasse='").append(motDePasse).append('\'');
        sb.append(", credit=").append(credit);
        sb.append(", administrateur=").append(administrateur);
        sb.append('}');
        return sb.toString();
    }
}
