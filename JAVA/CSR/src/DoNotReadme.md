*Oh !*
#Progression :

## Optimization
- Sum dans le MatrixReader -> Lien CsrClusteredMatrix et Stocker.
## Tests
- Créer un test qui prend CsrClusteredMatrix et qui implément les mesures.
##Intégration projet Maden -> Eclipse :
- ***Eclipse reconnait le projet*** en tant que projet Maven à l'aide de plugin installé.
- Mais lancer un **Main** est impossible faute de la libraire ***org.apache.maven.plugins:maven-jar-plugin***

##Intégration des mesures :
- Regrouper les mesures : ***Fait***
- Déplacer les méthodes de Feature Selection dans une classe Matrix : ***En cours***
- Tester à l'aide d'un **Main**

- Installer JDK
#Questions :
##en vrac :

- CsrClusteredMatrix, 21 : super() par rapport à quoi ?
- L'utilité du Hashcode dans une définition de type (Pair et PairF) sous Java => Classification ? Ou Pair n'est pas un tuple mais une liste chainée ?
- Cast d'une matrice de type IMatrix en CsrClusteredMatrix en étant en train de la construire dans CsrClusteredMatrix :
  - Problème lors de l'utilisation des méthodes getSomeClusterMethod

# Notes:
- Create Class -> Source -> Generate delegate methods
- Generate Getters and Setters + toString

# CV :
- Monster + Apec + Kelstage 
