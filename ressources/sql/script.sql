-- Script SQL pour créer la base de données
--CREATE DATABASE mdd CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
-- Création de la table USERS
CREATE TABLE
    USERS (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        email VARCHAR(50) NOT NULL UNIQUE,
        username VARCHAR(30) NOT NULL UNIQUE,
        password VARCHAR(255) NOT NULL,
        picture CHAR(36),
        is_admin BOOLEAN
    );

-- Création de la table THEMES
CREATE TABLE
    THEMES (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        title VARCHAR(255) NOT NULL,
        description TEXT NOT NULL,
        icon CHAR(36),
        color CHAR(6)
    );

-- Création de la table ARTICLES
CREATE TABLE
    ARTICLES (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        title VARCHAR(255) NOT NULL,
        content TEXT NOT NULL,
        author BIGINT,
        FOREIGN KEY (author) REFERENCES USERS (id) ON DELETE SET NULL
    );

-- Création de la table COMMENTS
CREATE TABLE
    COMMENTS (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        content TEXT NOT NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        author_id BIGINT,
        article_id BIGINT,
        FOREIGN KEY (author_id) REFERENCES USERS (id) ON DELETE SET NULL,
        FOREIGN KEY (article_id) REFERENCES ARTICLES (id) ON DELETE CASCADE
    );

-- Création de la table ARTICLE-RELATIONS
CREATE TABLE
    ARTICLE_THEMES (
        article_id BIGINT NOT NULL,
        theme_id BIGINT NOT NULL,
        FOREIGN KEY (article_id) REFERENCES ARTICLES (id) ON DELETE CASCADE,
        FOREIGN KEY (theme_id) REFERENCES THEMES (id) ON DELETE CASCADE,
        PRIMARY KEY (article_id, theme_id)
    );

-- Création de la table USER_THEMES
CREATE TABLE
    USER_THEMES (
        user_id BIGINT NOT NULL,
        theme_id BIGINT NOT NULL,
        FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE,
        FOREIGN KEY (theme_id) REFERENCES THEMES (id) ON DELETE CASCADE,
        PRIMARY KEY (user_id, theme_id)
    );