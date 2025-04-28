package fr.eni.tp.projet.bo;

public class Adresse {
    private String rue;
    private String ville;
    private String codePostal;

    public Adresse(String rue, String ville, String codePostal) {
        this.rue = rue;
        this.ville = ville;
        this.codePostal = codePostal;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Adresse{");
        sb.append("rue='").append(rue).append('\'');
        sb.append(", ville='").append(ville).append('\'');
        sb.append(", codePostal='").append(codePostal).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
