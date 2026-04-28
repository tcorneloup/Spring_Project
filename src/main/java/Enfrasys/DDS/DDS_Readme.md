-> Qu'est ce que le DDS :

Le DDS (Data Distribution service) est une norme de l'OMG (Object Manager Group)
dont le rôle est de proposer une technologie d'échnage de données via un réseau.

DDS es tun middleware centré sur les données et offre un découplage spatio-temporel : Les applications n'ont pas
besoin d'être simultanément actives au moment d ela production et de la consommation de données.
La communication est donc asynchrone, ce qui permet un faible latence, une fiabilité et un découplage fort

Pour donner un exemple :
Sur JAVA, j'ai deux services : A1, B2 et un topic (ou canal de communication) Commande
Service A1 va être publisher et envoie une nouvelle commande
Comme service B2 est un Subscriber à Commande alors il va réagir et reçoit les informations