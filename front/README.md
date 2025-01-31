## MDD Social Network - Front-End  

![Angular](https://img.shields.io/badge/Angular-18-%23DD0031?style=&logo=angular&logoColor=white)
![RxJS](https://img.shields.io/badge/RxJS-7-%23B7178C?style=&logo=reactivex&logoColor=pink)
![Angular Material](https://img.shields.io/badge/Angular%20Material-%23F44336?style=&logo=angular&logoColor=white)
![Node.js](https://img.shields.io/badge/Node.js-18-%23339933?style=&logo=node.js&logoColor=green)

## 📝 Description  

MDD Social Network est une application **Angular 18** qui permet la gestion et la consultation d'articles IT classés par thèmes. Cette application s'inscrit dans le cadre du projet pédagogique **OpenClassrooms - Développeur Full-Stack Java Angular (P6)**.  

Elle communique avec une **API REST sécurisée** développée en **Spring Boot** et utilise un **système d'authentification JWT** pour gérer les utilisateurs et restreindre l'accès aux fonctionnalités selon les rôles.  

---

## 🛠️ Installation et lancement du projet  

### 🔧 Prérequis  
- **Node.js 18+** ([Téléchargement](https://nodejs.org/))  
- **Angular CLI 18+** ([Documentation](https://angular.io/cli))  
- **Back-end API** installé et fonctionnel ([README du back-end](../README.md#installation-et-lancement-du-projet))  

### 📋 Cloner le dépôt  

Clonez le projet depuis GitHub et accédez au dossier `front` :  
```bash
git clone https://github.com/TangiLC/OC-P6_MDDNet.git
cd front
```

### 📥 Installation des dépendances  

Installez les dépendances via npm :  
```bash
npm install
```

### ⚙️ Configuration de l'API  

Modifiez le fichier `src/environments/environment.ts` pour qu'il corresponde à l'URL du back-end :  
```typescript
export const environment = {
  production: false,
  apiBaseUrl: 'http://localhost:8080/' // API REST du back-end
};
```

### 🚀 Lancer le projet  

Démarrez l'application en mode développement avec :  
```bash
npm start
```
ou  
```bash
ng serve
```

Le serveur sera accessible sur :  
[http://localhost:4200](http://localhost:4200)  

**💡 Important** :  
Assurez-vous que l'**API back-end** est bien démarrée sur `localhost:8080` avant de lancer l'application front-end.  

---

## 🔑 Authentification  

L'application utilise un **système de tokens JWT** stockés en `localStorage` pour authentifier les utilisateurs et gérer les autorisations d'accès.  

### Comptes de test disponibles :  
- **👤 Admin**  :[*Username* : `AdminTest` - *Password* : `test123`  ]

- **👤 Utilisateur standard** :[*Username* : `TimBLee` - *Password* : `test123`  ] 


Les mots de passe sont stockés sous **forme cryptée avec Bcrypt**.  

Une fois connecté, l'utilisateur obtient un **token JWT**, nécessaire pour accéder aux pages protégées.  

---

## 📂 Architecture du projet  

L'application est une **single-page-application** avec **router**, qui suit une **architecture modulaire** et utilise des **composants standalone**.  

📁 **Dossiers principaux** :  
- `src/app/components/` → Composants modulaires de l'UI  
- `src/app/services/` → Services Angular (API, authentification...)  
- `src/app/intefaces/` → Interfaces et DTO  
- `src/app/pages/` → Pages principales de l'application  

---

## 🚧 Fonctionnalités et évolutions  

### ✅ Fonctionnalités implémentées  
- ✔️ Authentification avec JWT (connexion, inscription) 
- ✔️ Gestion du profil utilisateur (username, email, abonnements)   
- ✔️ Affichage des articles et filtres par thèmes  
- ✔️ Gestion des articles (CRUD)  
- ✔️ Outil de tri des articles (par date, titre, auteur)  
- ✔️ Gestion des thèmes (CRUD)  
- ✔️ Interface utilisateur responsive avec **Angular Material**  

### 🔜 Améliorations à prévoir  
- 🚀 **Ajout de tests** unitaires & E2E
- 🚀 **Possibilité d'upload d'images** (profil et thèmes)
- 🚀 **Gestion des commentaires** (Update, Delete)
- 🚀 **Gestion du cleanup** des derniers articles à afficher
- 🚀 **Mise en production & déploiement** (actuellement en mode dev)  


---

## 📖 Références  

| 📌 Ressource | 🔗 Lien |
|----------------|----------------|
| **Angular CLI** | [Angular CLI Docs](https://angular.io/cli) |
| **Angular Material** | [Material Docs](https://material.angular.io/) |
| **RxJS** | [RxJS Docs](https://rxjs.dev/) |
| **JWT Auth** | [JWT.io](https://jwt.io/) |

---

### 🎓 Merci pour votre intérêt ! 😊  
