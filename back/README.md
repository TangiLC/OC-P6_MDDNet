
# MDD Social Network API 

![Java](https://img.shields.io/badge/Java-21-%23ED8B00?style=&logo=openjdk&logoColor=orange)
![Maven](https://img.shields.io/badge/Maven-3.9.9-%23C71A36?style=&logo=apachemaven&logoColor=red)
![MySQL](https://img.shields.io/badge/MySQL-8.0-%234479A1?style=&logo=mysql&logoColor=blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.0-%236DB33F?style=&logo=springboot&logoColor=green)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6.4.0-%236DB33F?style=&logo=spring&logoColor=green)
![Swagger](https://img.shields.io/badge/Swagger%20UI-V3-%2385EA2D?style=&logo=swagger&logoColor=green)

## 📝 Description

MDD Social Network API est un projet back-end développé en **Java 21** avec le framework **Spring Boot**, dans un cadre pédagogique pour le cursus **Full-Stack Java Angular d'OpenClassrooms**. Il s'agit d'une API REST sécurisée permettant la gestion d'utilisateurs, de thèmes, d'articles, et de commentaires. Ce projet inclut une authentification via JWT (JSON Web Tokens) et le stockage sécurisé des mots de passe via BCrypt.

La documentation de l'API est disponible via Swagger et inclut des endpoints sécurisés ainsi que des routes publiques.

---

## 🛠️ Installation et lancement du projet

### 🔧. Prérequis
- **Java 21** ou version ultérieure.
- **Maven 3.9** pour la gestion des dépendances.
- **MySQL 8.0** pour la base de données.

### 📋. Cloner le dépôt
Clonez le projet depuis GitHub :
```bash
git clone https://github.com/TangiLC/OC-P6_MDDNet.git
```

### 📥. Créer et importer la base de données
Créez une nouvelle base de données avec mySQL.

Dans le terminal, depuis votre repertoire `.../MySQL Server/bin` exécutez la commande suivante pour lancer mysql :
 ```bash
   mysql -u root -p
```

La commande suivante va créer une nouvelle base de données (personnaliser le nom) :
```mysql
   CREATE DATABASE [database_name];
```

Le fichier `script.sql` dans le dossier `/ressources/sql` à la racine de ce projet va initialiser la structure de la base de données. Le fichier `test.sql` contient un jeu de données pour remplir les tables. Importez ces fichiers dans votre base MySQL via un outil comme **phpMyAdmin** ou **MySQL Workbench**, ou avec la commande suivante :
```bash
mysql -u [username] -p [database_name] < script.sql
mysql -u [username] -p [database_name] < test.sql
```


### 🚀. Lancer le projet
1. Ouvrez le projet dans un IDE (*Eclispe*, *Intellij DEA*, *VS Code*...)

2. Configurez l'accès à la base de données

    a. Créez un fichier `application-secret.properties` dans le même répertoire que `application.properties (.back/src/main/resources/)`. Ce fichier sera masqué par gitIgnore par sécurité.

    b. Ajoutez les configurations suivantes dans ce fichier en personnalisant les [valeurs]  :
   ```
   spring.datasource.url=jdbc:mysql://localhost:[3306]/[database_name]?
   spring.datasource.username=[username]
   spring.datasource.password=[password]
   jwt.secretKey=[secured32BitKey]
   jwt.validity=[millisec token validity (86400000)]
   ```

3. Lancez le projet avec l'option **Run** de l'IDE ou via Maven :
   ```bash
   mvn clean spring-boot:run
   ```


4. Le serveur par défaut est accessible en `localhost:8080`

---

## 🖥️ Front-end associé

Le projet front-end Angular associé à cette API est disponible dans le dossier `/front`

---

## 📖 Documentation Swagger 

Lorsque le serveur est lancé, la documentation Swagger V3 est générée et accessible à l'adresse suivante :
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

Pour utiliser les routes protégées :
1. Créez un nouvel utilisateur avec `POST api/auth/register`
2. Générez un token JWT avec `POST api/auth/login`. &nbsp;*`nouvel utilisateur`*
2. Copiez le token renvoyé en réponse par l'API.
3. Cliquez sur "Authorize" dans SwaggerUI et entrez le token dans la modale BearerAuth.

#### Fonctionnalités principales

Routes publiques
- **POST /auth/register** : Crée un nouvel utilisateur.
- **POST /auth/login** : Authentifie un utilisateur et génère un token JWT.

Routes protégées (nécessitent un token JWT)

**USER**
- **GET /me** : Récupère les informations de l'utilisateur connecté.
- **PUT /user/{id}** : Modifie les données de l'utilisateur.
- **PUT /user/add_theme/{id}** : Ajoute le thème 'id' dans la liste de suivi.
- **PUT /user/remove_theme/{id}** : Retire le thème 'id' dans la liste de suivi.

**ARTICLE**
- **POST /article** : Crée un nouvel article.
- **GET /article/{id}** : Récupère les données de l'article 'id'.
- **PUT /article/{id}** : Modifie les données de l'article 'id'.
- **DELETE /article/{id}** : Supprime l'article 'id' de la bdd.
- **GET /article/by_author/{authorId}** : Récupère la liste des articles créés par 'authorId'.
- **GET /article/by_theme/{themeId}** : Récupère la liste des articles associés à 'themeId'.

**THEME**
- **POST /theme** : Crée un nouveau thème.
- **GET /theme/{id}** : Récupère les données du thème 'id'.
- **PUT /theme/{id}** : Modifie les données du thème 'id'.
- **DELETE /theme/{id}** : Supprime le thème 'id' de la bdd.
- **GET /themes** : Récupère la liste des thèmes existants dans la bdd.

**COMMENTS**
- **POST /comment** : Crée un nouveau commentaire.
- **GET /comment/{id}** : Récupère les données du commentaire 'id'.
- **PUT /comment/{id}** : Modifie les données du commentaire 'id'.[dev en cours]
- **DELETE /comment/{id}** : Supprime le commentaire 'id' de la bdd.[dev en cours]
- **GET /comment/by_author/{authorId}** : Récupère la liste des commentaires créés par 'authorId'.
- **GET /comment/by_article/{articleId}** : Récupère la liste des articles associés à l'article 'articleId'.


---

## 📣 Notes

- Ce projet en phase de **développement** est une ébauche MVP à compléter et tester avant production.

---

### 🎓 Merci pour votre intérêt ! 😊


### 📚 References

| Documentation | Guides |
|-----------------------|-----------------------|
| [Official Apache Maven documentation](https://maven.apache.org/guides/index.html) | [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/) |
| [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.0/maven-plugin) | [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/) |
| [Create an OCI image](https://docs.spring.io/spring-boot/3.4.0/maven-plugin/build-image.html) | [Securing a Web Application](https://spring.io/guides/gs/securing-web/) |
| [Spring Data JPA](https://docs.spring.io/spring-boot/3.4.0/reference/data/sql.html#data.sql.jpa-and-spring-data) | [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/) |
| [Spring Security](https://docs.spring.io/spring-boot/3.4.0/reference/web/spring-security.html) | [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/) |
| [Validation](https://docs.spring.io/spring-boot/3.4.0/reference/io/validation.html) | [Validation form-input](https://spring.io/guides/gs/validating-form-input/) |
| [Spring Web](https://docs.spring.io/spring-boot/3.4.0/reference/web/servlet.html) | [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)|
| | [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/) |
| | [Building REST services with Spring](https://spring.io/guides/tutorials/rest/) |

