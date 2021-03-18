# Git Helper

## Description 
Ce document vise à décrire le processus d'utilisation de **Git** durant les TPs ainsi que de vous fournir les commandes classiques d'utilisation de cet outil. 

Lors du développement de votre projet, vous aurez toujours au minimum 2 branches :

- `master` : branche liée au développement final de chaque TP.
- `develop` : branche permettant de vérifier le fonctionnement finale de chacun de vos développement avant ajout sur la branche `master`.

Durant le développement de vos TPs, vous pouvez suivre le processus suivant :

- Pour chaque nouveau développement, créer une branche `feature/TPX-NomFeature` à partir de la branche `develop`.
- Une fois un développement terminé, fusionner les modifications de la branche `feature/TPX-NomFeature` vers la branche `develop`. Puis supprimer la branche `feature/TPX-NomFeature`.
- Vérifier que vos développements sur la branche `develop` fonctionne toujours.
- Mettre à jour la branche `master` sans supprimer la branche `develop` lorsqu'un TP est terminé.
- Mettre à jour votre projet **Git** distant hébergé sur Gitlab.

**Note :** C'est un processus assez classique de développement permettant de bien vérifier le fonctionnement d'un projet.

## Conseils imports

Il est très important de chercher le bénéfice de cet outil. La création d'une branche liée à une fonctionnalité à développer est conseillée.
Cela permet de sectionner le développement du code et de le valider étape par étape.

## Les commandes importantes

### Gestion de modifications

```bash
# Montre le status des fichiers versionnés ou non versionnés
git status

# Ajoute le fichier `file.txt` pour un prochain commit
git add file.txt

# Ajoute tous les fichiers pour un prochain commit
git add .

# Visualisation des différents commits de la branche courante
git log --oneline
```

### Gestion des branches

```bash
# Liste toutes les branches disponibles
git branch
 
# Crée une nouvelle branche
git branch ma-branche

# Change de branche si existante
git checkout ma-branche
	
# Création et changement de branche courante
git checkout -b ma-branche
 
# Supprime une branche
git branch -d ma-branche
 
# Depuis la branche courante, récupération des modifications de `ma-branche`
git merge ma-branche
 ```

### Interaction avec le serveur d'hébergement Gitlab

```bash
 # Crée un commit avec les fichiers `ajoutés`
 git commit -m "mon premier commit"

 # Énumère les serveurs d'hébergements référencés
 git remote -v

 # Publie les modifications apportés par une branche sur un serveur
 git push origin ma-branche

 # Récupère les modifications sur le serveur d'hébergement
 git pull origin ma-branche
```

### Récupération de solutions

```
# Ajout d'un projet git en tant que distant  
git remote add upstream https://gitlab.com/jbuisine/l2_projet_java.git

# Récupération de modification spécifique d'une branche (en ciblant la remote upstream)
git pull upstream branche-TP1
```

