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
  - **numberOfGroups=** suivit du nombre de groupe souhaité (sans espace après le "=")

*remarque: le nombre de groupe doit être tel que 3 adresse peuvent être 
            attribuée par groupe, sinon le programme ne fonctionnera pas*
## Implementation
