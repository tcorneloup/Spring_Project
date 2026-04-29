package Enfrasys.SOLID.L;

public class BadClassPenguin extends BadClassBird {
    @Override
    public void fly() {
        throw new UnsupportedOperationException();
    }
}
