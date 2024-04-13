Binôme :
* Yakine KLABI
* Sarra KOSSENTINI

Ce fichier contient et contiendra des remarques de suivi sur votre
projet tant sur la modélisation que sur la programmation. Un nouveau
suivi est indiqué par une nouvelle section datée.

Certaines remarques demandent des actions de votre part, vous les
repérerez par une case à cocher.

- []  Action (à réaliser) 

Merci de nous indiquer que vous avez pris en compte la remarque en
cochant la case. N'hésitez pas à écrire dans ce fichier et à nous
exposer votre point de vue.

- [x] Action (réalisée)
    - RÉPONSE et éventuelles remarques de votre part, 

 
---
# Suivi du mar. 13 févr. 2024 15:29:42
Chantal Taconet

Vous avez compris les specifications dans leur ensemble. Il vous manque des pré et post conditions. Je vous conseille une mise à jour ... après le TP3. Bonne continuation. 
 
- [x] GIT-03-Git-commentaires-de-commit-explicites
  - Vos messages de commit ne permettent pas de savoir où vous en êtes dans la réalisation de la séance, pourriez-vous svp être plus explicites sur les parties ajoutées/modifiées à chacun de vos commit ? N’hésitez pas à faire de nombreux commit, le travail réalisé sera d’autant plus lisible. 
  - Par exemple "Modified readme" ne permet pas de savoir ce qui a été modifié 
  
# Spécification et préparation des tests de validation

## Diagrammes de cas d'utilisation
- [x] Réfléchir au Lien entre un *modérateur* et un *membre*, entre un *administrateur* et un *modérateur* 
- [x] Les opérations de lister (les membres, les réseaux sociaux ...) pourraient être utiles  
## Préconditions et postconditions

- Réfléchir à toutes les données en entrée d'un cas d'utilisation 

1. Cas d'utilisation « créer un réseau social »

- [x] L'utilisateur ayant créé un réseau social se retrouve membre du dit réseau social, comment connaissez-vous l'identité de cet utilisateur ? Quelles sont les post-conditions associées 

2. Cas d'utilisation « ajouter un membre à un réseau social »

- [x] A quel réseau social cet utilisateur est-il ajouté ?


3. Cas d'utilisation « poster un message »

- [x] A compléter A quel réseau social ce  message est-il ajouté, le message est-il bien formé ... 


## Tables de décision des tests de validation
- [x] TABLEDECTV-07-MAJ-précondition-postcondition
    * Pensez à mettre à jour les tables de décision si vous faites des
      mises à jour des pré/post conditions.
1. Cas d'utilisation « créer un réseau social »
2. Cas d'utilisation « ajouter un membre à un réseau social »
3. Cas d'utilisation « poster un message »

# Suivi du mar. 05 mars 2024 16:15:40
Chantal Taconet

Du bon travail, il convient de revoir les diagrammes de séquence et la modélisation du modérateur. Bonne continuation. 

* [x] GEN-01-Indiquer-remarque-prise-en-compte
    * Veuillez indiquer lorsque vous avez pris en compte les remarques
      en mettant un « x » dans la case à cocher, comme indiqué en
      début de fichier

	  
## Nouveaux retours rapides : Spécification et préparation des tests de validation

Ne sachant pas quelles ont été les remarques prises en compte, je ne peux regarder les 3 points suivants.
## Nouveaux retours rapides : Spécification et préparation des tests de validation

### Diagrammes de cas d'utilisation
### Préconditions et postconditions
### Tables de décision des tests de validation

## 3. Conception

### Diagramme de classes
Vous êtes sur la bonne voie ! 
- [x] Comment savez-vous qu'un membre est modérateur ? 

### Diagrammes de séquence

1. Cas d'utilisation « créer un réseau social »
* [x] DIAGSEQ-04-Pb-cohérence-précondition-non-complète
    * Le diagramme de séquence incriminé ne modélise pas complètement
      le cas d'utilisation car certaines préconditions ne sont pas
      vérifiées.
	  * Comment identifiez vous l'utilisateur ayant créé le réseau et qui devient automatiquement modérateur ? 
	  
* [x] DIAGSEQ-05-Pb-cohérence-effet-non-complet
    * Le diagramme de séquence incriminé ne réalise pas complètement
      tous les effets du cas d'utilisation. Il manque la création d'un
      objet ou l'instanciation d'une association ou un autre élément à
      modéliser pour avoir un cas d'utilisation complet.

* [x] Un seul message en provenance de l'acteur mais avec les deux paramètres, il faudra rechercher également le pseudo, et l'ajouter en tant que modérateur 

2. Cas d'utilisation « ajouter un membre à un réseau social »


3. Cas d'utilisation « poster un message »
- [x] Vous pouvez faire un seul diagramme avec un fragment "alt" 
- [x] il manque des paramètres en entrée : le réseau social, l'utilisateur qui envoie le message et donc des préconditions 

### Raffinement du diagramme de classes

1. Fiche de la classe « Message »

- [x] 

### Diagramme de machine à états et invariant

1. Diagramme de machine à états de la classe « Message »
* [x] DIAGMACHETATS-02-Compréhension-étude-de-cas
    * Une ou plusieurs diagrammes de machine à états montrent un
      erreur de compréhension de l'étude de cas.
	  * Le message peut être caché 
	  * il peut être accepté directement si il est créé par le modérateur 
	  
* [x] DIAGMACHETATS-07-Transition-pb-syntaxe-événement-condition-action
    * Une ou plusieurs transitions ne respectent pas la
      syntaxe : `événement[condition]/action`.



2. Invariant de la classe « Message »

- [x] OK

## 4. Préparation des tests unitaires

1. Table de décision des tests unitaires de la méthode Message::constructeur

- [x] OK

2. Table de décision des tests unitaires de la méthode Message::modérer

- [x] OK
 
---
# Suivi du mar. 19 mars 2024 18:45:39
Chantal Taconet
 
Vous avez bien avancé, bravo ! 
Il vous reste à finaliser les 3 cas d'utilisation lors de la prochaine séance. 

## Nouveaux retours rapides : Conception

### Diagramme de classes
Bien avancé 
### Diagrammes de séquence
 DS créer réseau Bien mais reste à relier l'utilisateur au membre nouvellement créé pour être en cohérence avec le DC
 DS poster un message Bien, restera à le compléter lors de la séance 9 (notifications de modération)

### Diagramme de machine à états et invariant
Bien
## Nouveaux retours rapides : Préparation des tests unitaires
bien
## Programmation du logiciel

### Utilisation des outils de programmation

1. Module Maven et tests avec JUnit

- Bien 

### Programmation de la solution

#### Classes du diagramme de classes avec leurs attributs

#### Méthodes des cas d'utilisation de base

1. Cas d'utilisation « créer un réseau social »

- [x] Bien avancé 

2. Cas d'utilisation « ajouter un membre à un réseau social »

- [x] trop de données en entrée ??? mais bien avancé 

3. Cas d'utilisation « poster un message »

- [x] Attention qui crée le message le RS (DS) ou MiniSocs (java) ? 

#### Cohérence entre le code et le modèle

A vérifier  encore quelques incohérences cf ci-dessus 



## Programmation et exécution des tests

### Tests de validation des cas d'utilisation

1. Cas d'utilisation « créer un réseau social »

- [x] je ne retrouve pas les numéros du tableau de validation mais sinon compris 
- 

2. Cas d'utilisation « ajouter un membre à un réseau social »

- [] à compléter

3. Cas d'utilisation « poster un message »

- [] à compléter 

### Tests unitaires des méthodes d'une classe

1. Constructeur de la classe `Message`

- [x] à compléter 

2. Méthode `modérer` de la classe `Message`

- [] à compléter

 
---
# Suivi du jeu. 28 mars 2024 18:01:07
Chantal Taconet
 
Vous avancez bien. Je ne peux que vous féliciter. 
Une petite remarque ci-dessous sur le style de code 

## Cohérence entre le code et le modèle

1. Préconditions, postconditions et diagrammes de séquence
RAS, bien 

2. Diagrammes de classes et de séquence
RAS, bien 

3. Table de décision des tests unitaires et programmation des tests unitaires

- []

4. Table de décision des tests de validation et programmation des tests de validation

Reste quelques erreurs de tests mais l'ensemble est compris

## Qualité du code

1. Spotbugs
Bien
- [] 

2. Checkstyle
Bien sauf 
- []	private final Map<String, Membre> **Membres**;
  - nom d'attribut doit être en minuscule sinon on confond avec un nom de classe
 
## Application d'idiomes JAVA

1. Idiome méthode `equals` et `hashCode` de la classe `Object`
Bien
- []

2. Idiome méthode `toString` de la classe `Object`
Bien
- []

3. Idiome des pipelines de *Streams*
Bien
- []

4. Idiome de gestion des références `null` avec `Optional`
Cela viendra sans doute par la suite 
- []
