-> Principes fondamentaux de la POO

   1) Une classe est un modèle, et un objet est une instance concrète de ce modèle. (Joueur)
   2) L'encapsulation permet de protégé des données ou de les rendre publiques avec des attibuts comme public, private ou protected + Getter et Setter
      Le but étant d'éviter l'accès direct aux variables. La logique métier reste dans la classe et on contrôle les données (Creatures)
   3) L'héritage permetune réutilisation du code et une mise en place d'une hiérarchie logique (Animaux et Chien)
   4) Le polymorphisme indique qu'une même méthode peut avoir plusieurs comportements (override - animaux et chien). Permet une plus grande flexibilité sans casser le code
   5) L'abstract est une classe incomplète qu'on ne peux pas instancier mais qui sert de base pour d'autres classes, elle définit un modèle commun (oiseaux et pigeon)
      L'objectif étant déviter la répétiion de code et de mettre un code obligatoire à implémenter