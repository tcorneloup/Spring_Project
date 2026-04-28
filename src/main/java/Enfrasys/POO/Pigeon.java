package Enfrasys.POO;

public class Pigeon extends abstractOiseaux {

    @Override
    public void voler() {
        System.out.println("flap");

        abstractOiseaux o = new Pigeon();
        o.voler(); //flap
    }
}
