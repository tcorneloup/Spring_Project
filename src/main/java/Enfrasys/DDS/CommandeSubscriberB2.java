package Enfrasys.DDS;

public class CommandeSubscriberB2 {

    public static void main(String[] args) {
        // Création du participant
        DDSParticipant participant = new DDSParticipant();

        // Définition du topic, on s'abonne aux message de CommandeN1
        DDSTopic<Commande> topic = participant.createTopic("CommandeN1", Commande.class);

        // Création du subscriber, responsable de la récèption des données
        DDSSubscriber subscriber = participant.createSubscriber();

        // Création du reader qui lit les messages publiés dans le topic
        DDSDataReader<Commande> reader = subscriber.createReader(topic);

        // Écoute des messages
        while (true) {
            Commande commande = reader.take();

            if (commande != null) {
                System.out.println("Commande reçue : " + commande.getId());
            }
        }
    }
}
