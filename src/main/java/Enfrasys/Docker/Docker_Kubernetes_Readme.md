-> Qu'est ce que Docker et Kubernetes

1) Docker sert à empaquter une application et ses dépendances dans un conteneur isolé, qui peut être executé sur n'importe quel serveur. 
   La conteneurisation est plus légère que la virtualisation qui s'appuis sur la machine hôte pour son focntionnement.
   Cette approche permet d'accrôitre la flexibilité et la portabilité d'exécution d'une application. C'est donc une sorte d'image portable de l'application

2) Kuberntes est un orchestrateur qui va permettre de gérer une grande quantité de containeurs. Là où Docker = 1 application, Kubernetes = gestion de milliers Docker
   Kuberntes va donc permmetre un déploiement automatique, un scaling (afin d'augmenter ou de réduire les instance selon le besoin), redémarrer les application après crash, 
   un load balancing et une gestion réseau entres les services. C'est le langage YAML qui ets utiliser pour la configuration Docker