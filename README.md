# Projet Amio

L'objectif de ce mini-projet est de faire une application Android exploitant des données issues d'un réseau de capteurs et exposées à travers un web service (IoTLab de TELECOM Nancy).

<p align="center">
<img src="/img/home.jpg" alt="application" width="300"/>
</p>

## Équipe 
Ce projet est réalisé par [Victor de Moura Netto](https://github.com/vicnetto) et [Wenjia Tang](https://github.com/sans-sucre)

## Fonctionnalités implémentées

Nous avons développé les fonctionnalités suivantes au sein de notre application :

- **Requettes auprès du service de réseau de capteurs de TELECOM Nancy**
- **Envoi d'e-mails**
- **Notification automatique**
- **Configuration des paramètres**
  - Réglage de l'heure de réception des courriels/notifications.
  - Configuration du courrier électronique (contenu à envoyer, plages horaires et adresse du destinataire).
  - Activation automatique au démarrage du portable.

## Requettes HTTP
Pour obtenir des informations sur les capteurs, une requête HTTP est effectuée sur l'API `http://iotlab.telecomnancy.eu:8080/iotlab/rest/data/1/light1/last`. La réponse est au format JSON, qui est converti en singleton pour faciliter l'accès aux données.

## Notifications
Les notifications permettent d'informer l'utilisateur si un capteur commence à détecter la présence de lumière. Ces notifications sont générées à partir de l'heure définie dans la page de configuration.

<p align="center">
<img src="/img/sensor-on.jpg" alt="sensor" width="300"/>
</p>

## Envoi d'e-mails
En cas de détection d'une situation anormale liée à l'éclairage pendant les plages horaires définies, notre application utilise l'API Gmail pour envoyer automatiquement un e-mail depuis l'adresse amioinfotn@gmail.com.

## Configuration
Notre application offre la possibilité de personnaliser les paramètres suivants :

- Adresse e-mail du destinataire
- Plages horaires en semaine (debut et fin)
- Plages horaires le week-end (début et fin)
- Contenu de l'e-mail à envoyer
- Démarrage automatique de l'application au démarrage de l'appareil (boot)

<p align="center">
<img src="/img/image_config.png" alt="configuration" width="300"/>
</p>

## Boot
Pour continuer à recevoir les notifications et ne pas manquer les dernières informations sur les capteurs, l'option "startup on boot" peut être activée. Cette option permet à l'application de s'exécuter en arrière-plan dès le démarrage du téléphone.

<p align="center">
<img src="/img/boot.jpg" alt="boot" width="300"/>
</p>


