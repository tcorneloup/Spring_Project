package Enfrasys.SOLID.O;

class BadPriceCalculatorForOpenClose {
    public double calculate(String type) {
        if (type.equals("A")) return 10;
        if (type.equals("B")) return 20;
        return 0;

        // Ici on doit rajouter un type pour une nouvelle valeur, donc on doit change rle code de la classe
    }
}
