Projet Spring

Pour ce projet, j’ai créé un système de gestion d’abonnements afin de persister certaines informations :

Abonnement :

type d’abonnement (FIXE, MOBILE, FIBRE)
date de souscription
identifiant du client

Un abonnement peut avoir une ou plusieurs options :

nom (ROAMING, NETFLIX, MUSIC, HD)
date de souscription

Pour gérer ces abonnements, j’ai créé 3 routes :

Une pour récupérer tous les abonnements avec leurs options
Une pour ajouter une option à un abonnement existant
Une pour créer un abonnement

Règles spécifiques :

L’option ROAMING ne peut être ajoutée qu’à un abonnement MOBILE
L’option NETFLIX ne peut être ajoutée qu’à un abonnement FIBRE
L’option HD ne peut être ajoutée que si une option NETFLIX existe déjà sur l’abonnement
L’option MUSIC peut être ajoutée aux abonnements MOBILE ou FIBRE
ATTENTION : Un abonnement ne peut pas contenir deux fois la même option

L’objectif de cet exercice est de voir comment fonctionne le framework Spring.

L’architecture globale suit les principes principaux du DDD (Domain Driven Design).
Les tests couvrent 95 % du code.