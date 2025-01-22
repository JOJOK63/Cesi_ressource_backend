# dockerfile permet de communiquer avec docker pour savoir ce qu'il doit faire

# Generate build donc le JAR
#récupérer img maven
FROM maven:3-eclipse-temurin-17-alpine as build
# Définir l'emplacement du répertoire de travail dans le conteneur
WORKDIR /app
#copier le fichier pom et le src
COPY pom.xml .
COPY src ./src

#permet de lancer le build
RUN mvn clean package

####### générer l'image
# Utiliser une image Java officielle comme base
FROM openjdk:17-jdk-alpine

# Définir l'emplacement du répertoire de travail dans le conteneur
WORKDIR /app

# Copier le fichier jar généré dans le conteneur la fin de la commande permet de nommet le jar générer
COPY --from=build /app/target/ressource-0.0.1-SNAPSHOT.jar ./ressource.jar

# Exposer le port que votre application va utiliser
EXPOSE 808

# Commande pour exécuter l'application dans le conteneur
ENTRYPOINT ["java", "-jar", "ressource.jar"]
