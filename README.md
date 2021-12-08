# API - Labo SMTP
Guilain Mbayo
Mehdi Salhi

TODO
* **A brief description of your project**: if people exploring GitHub find your repo, without a prior knowledge of the API course, they should be able to understand what your repo is all about and whether they should look at it more closely.
* **Instructions for setting up a mock SMTP server (with Docker - which you will learn all about in the next 2 weeks)**. The user who wants to experiment with your tool but does not really want to send pranks immediately should be able to use a mock SMTP server. For people who are not familiar with this concept, explain it to them in simple terms. Explain which mock server you have used and how you have set it up.
* **Clear and simple instructions for configuring your tool and running a prank campaign**. If you do a good job, an external user should be able to clone your repo, edit a couple of files and send a batch of e-mails in less than 10 minutes.
* **A description of your implementation**: document the key aspects of your code. It is probably a good idea to start with a class diagram. Decide which classes you want to show (focus on the important ones) and describe their responsibilities in text. It is also certainly a good idea to include examples of dialogues between your client and an SMTP server (maybe you also want to include some screenshots here).

## Description
Ce projet a pour but de récupérer des addresses mail dans une
liste afin de créer des groupes et d'envoyer un mail forgé contenant
un prank à chacun des groupes. L'expéditeur apparent du message est 
sélectionné de manière aléatoire parmis les adresse d'un groupe.

## Setup Instructions

## Configuration and use instructions
#### Adresses mail
les addresses mail à utiliser doivent être écrites dans le fichier 
victims.UTF8 (./src/config/victims.UTF8). Une seule adresse ne doit 
être indiquée par ligne.
#### Prank
Les pranks doivent être écrit dans le fichier message.UTF8
(./src/config/message.UTF8). Chaque prank est séparé par le séparateur 
**<==========>**. Dans chaque prank, la première ligne est le sujet du
prank tandis que le reste est le corp du prank.
#### Configuration
le fichier properties.properties (./src/config/properties.properties)
contient les indication sur le serveur et sur le nombre de groupe voulu.
Pour assurer le bon fonctionnement du programme, il est impératif de 
respecter le format suivant:
- première ligne:
  - **smtpServerAdresse=** suivit de l'adresse du serveur (sans espace après le "=")
- seconde ligne:
  - **smtpServerPort=** suivit du port du serveur (sans espace après le "=")
- troisième ligne:
  - **numberOfGroups=** suivit du nombre de groupes souhaité (sans espace 
  - après le "=")
  
*remarque: le nombre de groupe doit être tel que 3 adresse peuvent être
attribuée par groupe, sinon le programme ne fonctionnera pas*

#### Serveur test & Docker
Nous avons mis a disposition un Dockerfile afin de créer une image docker 
avec un serveur test. Cela permet de deployer rapidement un serveur pour 
tester des campagnes de pranks.

Dans le dossier docker, en ligne de commande, executer la commande :
```
docker build -t mockmock .
```
Cette commande va télécharger le serveur MockMock depuis git et construire 
un conteneur Docker. Il faut ensuite lancer l'image Docker avec la commande 
suivante :
```
docker run -p 25000:25000 -p 8282:8282 -it mockmock
```
Cela permet de lancer le serveur de test dans le conteneur Docker en mappant 
les ports internes du conteneur vers l'extérieur afin de les rendre 
accessible. Une fois fait, le serveur MockMock est accessible depuis un 
navigateur web à l'adresse :
```
localhost:8282
```




## Implementation
Notre programme principal se trouve dans la classe MailBot. Cette classe
se charge de récupérer les paramètres du fichiers properties.properties
grâce aux méthodes contenues dans la classe Util. Elle crée ensuite les
différents groupes en leur passant en paramètres un nombre variable de 
Person grâce à une méthode qui est également contenue dans la 
classe Util. La classe groupe se charge de séléctionner un expéditeur parmis
les Person passées en paramètre.Ensuite, MailBot crée un message par groupe grâce à une instance de la 
classe PrankGenerator.

PrankGenerator crée tout d'abord un Prank en lui passant en paramètre l'un 
des pranks contenus dans le fichier message.UTF8 et séléctionné de manière
aléatoire. La classe prank se charge de découper le prank en un sujet et
un corps. Ensuite, PrankGenerator crée un Message en lui passant en paramètre
l'expéditeur du groupe, les victimes du groupe, le sujet du prank et son corps.

La classe message se charge de formater les différents éléments qui lui sont 
passés en paramètre de manière à avoir un message composé de chaînes de caractères
utilisable (body, subject, sender et receivers).

Mailbot transmet ensuite le message ainsi généré par PrankGenerator à une
instance de la Classe SmtpClient.

Ce sera finalement cette classe SmtpClient qui se chargera d'ouvrir une connexion
avec le serveur, puis de lui communiquer le message au format SMTP avant de clore
la connexion.

Un nouveau message et une nouvelle connexion SMTP est générée pour chacun
des groupes qui auront été créés plus tôt.