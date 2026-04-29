package Enfrasys.SOLID.L;

public class SparrowForLiskov extends BirdForLiskov implements FlyingForLiskov {
    @Override
    public void fly() {
        System.out.println("Sparrow For Liskov");
    }
}
