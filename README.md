# I Travel With U

Application pour une plateforme d'etude des hopitaux


## Lancement

Veuillez d'abord initaliser la base de donnees avant de lancer l'application si vous 
activez les informations de la database

Il faut renommer le template de fichier de configuration

Sur un OS unix based ou similaire (ex : FreeBSD) : 

```shell

cp "src/main/resources/.application.properties" "src/main/resources/application.properties"

```

Sur windows :

```powershell

Copy-Item "src/main/resources/.application.properties" "src/main/resources/application.properties"

```

Ou 

```cmd

copy "src\main\resources\.application.properties" "src\main\resources\application.properties"

```

Il faut changer vos informations de database dans le fichier de configuration

```properties

spring.datasource.url=jdbc:postgresql://localhost:5432/sig_hopital
spring.datasource.username=postgres
spring.datasource.password=votre_mot_de_passe
spring.datasource.driver-class-name=org.postgresql.Driver

```

Puis entrer la commande suivante dans le terminal

```shell

mvn spring-boot:run

```

## Contributeurs

-
-
- 
- 
- 
- ETU003951 RAKOTOARINIA Miharivola Nomena Manou

## Technologies

- java
- spring boot
- postgresql
- thymeleaf
- leaflet
