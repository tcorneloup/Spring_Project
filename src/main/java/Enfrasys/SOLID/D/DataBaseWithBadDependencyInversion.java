package Enfrasys.SOLID.D;

public class DataBaseWithBadDependencyInversion {
}

class App1 {
    private MySQLDatabase db = new MySQLDatabase();
}
