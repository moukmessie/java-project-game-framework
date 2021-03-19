# Git Helper

## Description 
Ce document vise à décrire le processus d'utilisation de **Git** durant les TPs ainsi que de vous fournir les commandes classiques d'utilisation de cet outil. 

## Conseils imports

Il est très important de chercher le bénéfice de cet outil. La création de `commit` doit être lié à une fonctionnalité à développer.
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

### Interaction avec le serveur d'hébergement Gitlab

```bash
 # Crée un commit avec les fichiers `ajoutés`
 git commit -m "mon premier commit"

 # Énumère les serveurs d'hébergements référencés
 git remote -v

 # Publie les modifications apportés par une branche sur un serveur
 git push origin master

 # Récupère les modifications sur le serveur d'hébergement
 git pull origin master
```

### Récupération de solutions

```
# Ajout d'un projet git en tant que distant  
git remote add upstream https://gitlab.com/jbuisine/l2_projet_java.git

# Récupération de modification spécifique d'une branche (en ciblant la remote upstream)
git pull upstream branche-TPX
```

