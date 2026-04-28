package Enfrasys.DDS;

public class CommandePublisherA1 {

    public static void main(String[] args) {
        // Création du particpant DSS
        DDSParticipant participant = new DDSParticipant();

        // Ici DDS vas crée un canal de communication 'CommandeN1" à partir de la classe Commande
        DDSTopic<Commande> topic = participant.createTopic("CommandeN1", Commande.class);

        // Création du publisher
        DDSPublisher publisher = participant.createPublisher();

        // création du writer qui va envouer les donnée
        DDSDataWriter<Commande> writer = publisher.createWriter(topic);

        // Publication
        Commande commande = new Commande("1", 99.99, "John");
        writer.write(commande);

        System.out.println("Commande publiée !");
    }
}
