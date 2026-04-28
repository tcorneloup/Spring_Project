package Enfrasys.DDS;

import java.util.Date;

public class Commande {
    private String id;
    private double price;
    private String name;

    public Commande(String id, double price, String name) {
        this.id = id;
        this.price = price;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}
