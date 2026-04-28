package Enfrasys.POO;

public class Chien extends Animaux {
    public void aboyer() {
        System.out.println("Wouf");

        Chien chien = new Chien();
        chien.manger(); // héritage
        chien.aboyer();
    }

    @Override
    public void parler() {
        System.out.println("Wouf");

        Animaux a = new Chien();
        a.parler(); //wouf
    }


}