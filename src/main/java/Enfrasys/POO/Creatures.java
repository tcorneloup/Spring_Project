package Enfrasys.POO;

public class Creatures {

    private int taille;
    private int poids;
    private boolean danger;

    public int getPoids() {
        return poids;
    }

    public void setPoids(int poids) {
        this.poids = poids;
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public void danger() {
        if (taille > 200 && poids >300) {
            danger = true;
        }
    }
}
