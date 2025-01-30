-- Insertion des utilisateurs
-- tous les mots de passe chiffrés Bcrypt sont 'test123'
INSERT INTO
    USERS (email, username, password, picture, is_admin)
VALUES
    (
        'admin@test.com',
        'AdminTest',
        '$2a$10$mIuUj7YekUqX.ibesAbbl.glhMuLdnnBeBQ/NoTCPz02on82QkeNO',
        'profil1',
        1
    ),
    (
        'linus.thorvalds@test.com',
        'LinusThorvalds',
        '$2a$10$mIuUj7YekUqX.ibesAbbl.glhMuLdnnBeBQ/NoTCPz02on82QkeNO',
        'profil8',
        0
    ),
    (
        'tim.berners.lee@test.com',
        'TimBLee',
        '$2a$10$mIuUj7YekUqX.ibesAbbl.glhMuLdnnBeBQ/NoTCPz02on82QkeNO',
        'profil3',
        0
    ),
    (
        'guido.vrossum@test.com',
        'GuidoV_Rossum',
        '$2a$10$mIuUj7YekUqX.ibesAbbl.glhMuLdnnBeBQ/NoTCPz02on82QkeNO',
        'profil4',
        0
    );

-- Insertion des thèmes
INSERT INTO
    THEMES (title, description, icon, color)
VALUES
 (
        'News',
        'Les derniers articles publiés sont à consulter ici !',
        'themeG',
        'EEEEEE'
    ),
    (
        'Angular et librairies',
        'Un projet Angular sans librairie, c’est comme un plat sans épices : fade et sans saveur !',
        'themeA',
        'FFDAB9'
    ),
    (
        'Base de données',
        'Tout sur les bases de données, les tables ou les relations...',
        'themeB',
        'A1EAFB'
    ),
    (
        'Spring-boot Java',
        'Tout sous la main, plus qu’à assembler !',
        'themeC',
        'B2F2BB'
    ),
    (
        'Tests',
        'Le test, ça fait mal au début, mais on fini par y prendre goût.',
        'themeD',
        'FFABAB'
    ),
    (
        'UX/UI',
        'L’UX/UI, est-ce que ça se prononce hu-iquse-hu-ï ou you-equse-you-aï ?.',
        'themeE',
        'CBAACB'
    ),
    (
        'Style CSS',
        'Un décorateur d’intérieur peut cacher le chaos sous une belle facade...',
        'themeF',
        'FDFD96'
    );

-- Insertion des articles
INSERT INTO
    ARTICLES (title, content, author, created_at, updated_at)
VALUES
    (
        'Bases pour Angular',
        'Lorem ipsum dolor sit amet, uno consectetur adipiscing elit. Aenean nec massa vitae ante eleifend tincidunt. Praesent dignissim elit eros, quis placerat ligula pharetra id. Duis neque ligula, molestie nec mollis eget, gravida sit amet elit. Sed fringilla tellus sed dui euismod euismod. Maecenas ac odio tincidunt, ornare nulla non, cursus odio. Proin venenatis hendrerit egestas. Praesent commodo pharetra felis nec porttitor. Maecenas lacinia turpis eget arcu volutpat, gravida malesuada velit hendrerit. Sed lorem erat, tempor id nunc at, blandit tristique metus. Vivamus magna ligula, facilisis a in. ',
        1,'2025-01-01 12:00:00','2025-01-01 12:00:00'
    ),
    (
        'Guide sur les bases de données',
        'Lorem ipsum dolor sit amet, duo consectetur adipiscing elit. Aenean nec massa vitae ante eleifend tincidunt. Praesent dignissim elit eros, quis placerat ligula pharetra id. Duis neque ligula, molestie nec mollis eget, gravida sit amet elit. Sed fringilla tellus sed dui euismod euismod. Maecenas ac odio tincidunt, ornare nulla non, cursus odio. Proin venenatis hendrerit egestas. Praesent commodo pharetra felis nec porttitor. Maecenas lacinia turpis eget arcu volutpat, gravida malesuada velit hendrerit. Sed lorem erat, tempor id nunc at, blandit tristique metus. Vivamus magna ligula, facilisis a in. ',
        2,'2025-01-02 12:00:00','2025-01-02 12:00:00'
    ),
    (
        'Démarrer avec Spring-boot',
        'Lorem ipsum dolor sit amet, tres consectetur adipiscing elit. Aenean nec massa vitae ante eleifend tincidunt. Praesent dignissim elit eros, quis placerat ligula pharetra id. Duis neque ligula, molestie nec mollis eget, gravida sit amet elit. Sed fringilla tellus sed dui euismod euismod. Maecenas ac odio tincidunt, ornare nulla non, cursus odio. Proin venenatis hendrerit egestas. Praesent commodo pharetra felis nec porttitor. Maecenas lacinia turpis eget arcu volutpat, gravida malesuada velit hendrerit. Sed lorem erat, tempor id nunc at, blandit tristique metus. Vivamus magna ligula, facilisis a in. ',
        3,'2025-01-03 12:00:00','2025-01-03 12:00:00'
    ),
    (
        'Jest et Jasmine',
        'Lorem ipsum dolor sit amet, quattuor consectetur adipiscing elit. Aenean nec massa vitae ante eleifend tincidunt. Praesent dignissim elit eros, quis placerat ligula pharetra id. Duis neque ligula, molestie nec mollis eget, gravida sit amet elit. Sed fringilla tellus sed dui euismod euismod. Maecenas ac odio tincidunt, ornare nulla non, cursus odio. Proin venenatis hendrerit egestas. Praesent commodo pharetra felis nec porttitor. Maecenas lacinia turpis eget arcu volutpat, gravida malesuada velit hendrerit. Sed lorem erat, tempor id nunc at, blandit tristique metus. Vivamus magna ligula, facilisis a in. ',
        4,'2025-01-04 12:00:00','2025-01-04 12:00:00'
    ),
    (
        'Design inclusif',
        'Lorem ipsum dolor sit amet, quinque consectetur adipiscing elit. Aenean nec massa vitae ante eleifend tincidunt. Praesent dignissim elit eros, quis placerat ligula pharetra id. Duis neque ligula, molestie nec mollis eget, gravida sit amet elit. Sed fringilla tellus sed dui euismod euismod. Maecenas ac odio tincidunt, ornare nulla non, cursus odio. Proin venenatis hendrerit egestas. Praesent commodo pharetra felis nec porttitor. Maecenas lacinia turpis eget arcu volutpat, gravida malesuada velit hendrerit. Sed lorem erat, tempor id nunc at, blandit tristique metus. Vivamus magna ligula, facilisis a in. ',
        1,'2025-01-05 12:00:00','2025-01-05 12:00:00'
    ),
    (
        'Créer un site avec HTML/CSS',
        'Lorem ipsum dolor sit amet, sex consectetur adipiscing elit. Aenean nec massa vitae ante eleifend tincidunt. Praesent dignissim elit eros, quis placerat ligula pharetra id. Duis neque ligula, molestie nec mollis eget, gravida sit amet elit. Sed fringilla tellus sed dui euismod euismod. Maecenas ac odio tincidunt, ornare nulla non, cursus odio. Proin venenatis hendrerit egestas. Praesent commodo pharetra felis nec porttitor. Maecenas lacinia turpis eget arcu volutpat, gravida malesuada velit hendrerit. Sed lorem erat, tempor id nunc at, blandit tristique metus. Vivamus magna ligula, facilisis a in. ',
        2,'2025-01-06 12:00:00','2025-01-06 12:00:00'
    ),
    (
        'Programmation orientée objet',
        'Lorem ipsum dolor sit amet, septum consectetur adipiscing elit. Aenean nec massa vitae ante eleifend tincidunt. Praesent dignissim elit eros, quis placerat ligula pharetra id. Duis neque ligula, molestie nec mollis eget, gravida sit amet elit. Sed fringilla tellus sed dui euismod euismod. Maecenas ac odio tincidunt, ornare nulla non, cursus odio. Proin venenatis hendrerit egestas. Praesent commodo pharetra felis nec porttitor. Maecenas lacinia turpis eget arcu volutpat, gravida malesuada velit hendrerit. Sed lorem erat, tempor id nunc at, blandit tristique metus. Vivamus magna ligula, facilisis a in. ',
        3,'2025-01-07 12:00:00','2025-01-07 12:00:00'
    ),
    (
        'SQL vs NoSQL',
        'Lorem ipsum dolor sit amet, octo consectetur adipiscing elit. Aenean nec massa vitae ante eleifend tincidunt. Praesent dignissim elit eros, quis placerat ligula pharetra id. Duis neque ligula, molestie nec mollis eget, gravida sit amet elit. Sed fringilla tellus sed dui euismod euismod. Maecenas ac odio tincidunt, ornare nulla non, cursus odio. Proin venenatis hendrerit egestas. Praesent commodo pharetra felis nec porttitor. Maecenas lacinia turpis eget arcu volutpat, gravida malesuada velit hendrerit. Sed lorem erat, tempor id nunc at, blandit tristique metus. Vivamus magna ligula, facilisis a in. ',
        4,'2025-01-08 12:00:00','2025-01-08 12:00:00'
    ),
    (
        'Les algorithmes',
        'Lorem ipsum dolor sit amet, novem consectetur adipiscing elit. Aenean nec massa vitae ante eleifend tincidunt. Praesent dignissim elit eros, quis placerat ligula pharetra id. Duis neque ligula, molestie nec mollis eget, gravida sit amet elit. Sed fringilla tellus sed dui euismod euismod. Maecenas ac odio tincidunt, ornare nulla non, cursus odio. Proin venenatis hendrerit egestas. Praesent commodo pharetra felis nec porttitor. Maecenas lacinia turpis eget arcu volutpat, gravida malesuada velit hendrerit. Sed lorem erat, tempor id nunc at, blandit tristique metus. Vivamus magna ligula, facilisis a in. ',
        2,'2025-01-09 12:00:00','2025-01-09 12:00:00'
    ),
    (
        'Créer un site avec HTML/CSS',
        'Lorem ipsum dolor sit amet, decem consectetur adipiscing elit. Aenean nec massa vitae ante eleifend tincidunt. Praesent dignissim elit eros, quis placerat ligula pharetra id. Duis neque ligula, molestie nec mollis eget, gravida sit amet elit. Sed fringilla tellus sed dui euismod euismod. Maecenas ac odio tincidunt, ornare nulla non, cursus odio. Proin venenatis hendrerit egestas. Praesent commodo pharetra felis nec porttitor. Maecenas lacinia turpis eget arcu volutpat, gravida malesuada velit hendrerit. Sed lorem erat, tempor id nunc at, blandit tristique metus. Vivamus magna ligula, facilisis a in. ',
        3,'2025-01-10 12:00:00','2025-01-10 12:00:00'
    ),
    (
        'Optimisation de bases de données',
        'Lorem ipsum dolor sit amet, undecim consectetur adipiscing elit. Aenean nec massa vitae ante eleifend tincidunt. Praesent dignissim elit eros, quis placerat ligula pharetra id. Duis neque ligula, molestie nec mollis eget, gravida sit amet elit. Sed fringilla tellus sed dui euismod euismod. Maecenas ac odio tincidunt, ornare nulla non, cursus odio. Proin venenatis hendrerit egestas. Praesent commodo pharetra felis nec porttitor. Maecenas lacinia turpis eget arcu volutpat, gravida malesuada velit hendrerit. Sed lorem erat, tempor id nunc at, blandit tristique metus. Vivamus magna ligula, facilisis a in. ',
        2,'2025-01-11 12:00:00','2025-01-11 12:00:00'
    ),
    (
        'Créer une application web dynamique',
        'Lorem ipsum dolor sit amet, duodecim consectetur adipiscing elit. Aenean nec massa vitae ante eleifend tincidunt. Praesent dignissim elit eros, quis placerat ligula pharetra id. Duis neque ligula, molestie nec mollis eget, gravida sit amet elit. Sed fringilla tellus sed dui euismod euismod. Maecenas ac odio tincidunt, ornare nulla non, cursus odio. Proin venenatis hendrerit egestas. Praesent commodo pharetra felis nec porttitor. Maecenas lacinia turpis eget arcu volutpat, gravida malesuada velit hendrerit. Sed lorem erat, tempor id nunc at, blandit tristique metus. Vivamus magna ligula, facilisis a in. ',
        1,'2025-01-12 12:00:00','2025-01-12 12:00:00'
    ),
    (
        'Tout savoir sur Angular et RxJs',
        'Lorem ipsum dolor sit amet, tredecim consectetur adipiscing elit. Aenean nec massa vitae ante eleifend tincidunt. Praesent dignissim elit eros, quis placerat ligula pharetra id. Duis neque ligula, molestie nec mollis eget, gravida sit amet elit. Sed fringilla tellus sed dui euismod euismod. Maecenas ac odio tincidunt, ornare nulla non, cursus odio. Proin venenatis hendrerit egestas. Praesent commodo pharetra felis nec porttitor. Maecenas lacinia turpis eget arcu volutpat, gravida malesuada velit hendrerit. Sed lorem erat, tempor id nunc at, blandit tristique metus. Vivamus magna ligula, facilisis a in. ',
        2,'2025-01-13 12:00:00','2025-01-13 12:00:00'
    ),
    (
        'Spring-Boot Hibernate',
        'Lorem ipsum dolor sit amet, quattrodecim consectetur adipiscing elit. Aenean nec massa vitae ante eleifend tincidunt. Praesent dignissim elit eros, quis placerat ligula pharetra id. Duis neque ligula, molestie nec mollis eget, gravida sit amet elit. Sed fringilla tellus sed dui euismod euismod. Maecenas ac odio tincidunt, ornare nulla non, cursus odio. Proin venenatis hendrerit egestas. Praesent commodo pharetra felis nec porttitor. Maecenas lacinia turpis eget arcu volutpat, gravida malesuada velit hendrerit. Sed lorem erat, tempor id nunc at, blandit tristique metus. Vivamus magna ligula, facilisis a in. ',
        3,'2025-01-14 12:00:00','2025-01-20 12:00:00'
    ),
    (
        'L’importance des tests automatisés',
        'Lorem ipsum dolor sit amet, quidecim consectetur adipiscing elit. Aenean nec massa vitae ante eleifend tincidunt. Praesent dignissim elit eros, quis placerat ligula pharetra id. Duis neque ligula, molestie nec mollis eget, gravida sit amet elit. Sed fringilla tellus sed dui euismod euismod. Maecenas ac odio tincidunt, ornare nulla non, cursus odio. Proin venenatis hendrerit egestas. Praesent commodo pharetra felis nec porttitor. Maecenas lacinia turpis eget arcu volutpat, gravida malesuada velit hendrerit. Sed lorem erat, tempor id nunc at, blandit tristique metus. Vivamus magna ligula, facilisis a in. ',
        4,'2025-01-15 12:00:00','2025-01-15 12:00:00'
    ),
    (
        'Améliorer l’UX/UI de vos applications',
        'Lorem ipsum dolor sit amet, sedecim consectetur adipiscing elit. Aenean nec massa vitae ante eleifend tincidunt. Praesent dignissim elit eros, quis placerat ligula pharetra id. Duis neque ligula, molestie nec mollis eget, gravida sit amet elit. Sed fringilla tellus sed dui euismod euismod. Maecenas ac odio tincidunt, ornare nulla non, cursus odio. Proin venenatis hendrerit egestas. Praesent commodo pharetra felis nec porttitor. Maecenas lacinia turpis eget arcu volutpat, gravida malesuada velit hendrerit. Sed lorem erat, tempor id nunc at, blandit tristique metus. Vivamus magna ligula, facilisis a in. ',
        2,'2025-01-16 12:00:00','2025-01-16 12:00:00'
    ),
    (
        'Styliser avec CSS',
        'Lorem ipsum dolor sit amet, septendecim consectetur adipiscing elit. Aenean nec massa vitae ante eleifend tincidunt. Praesent dignissim elit eros, quis placerat ligula pharetra id. Duis neque ligula, molestie nec mollis eget, gravida sit amet elit. Sed fringilla tellus sed dui euismod euismod. Maecenas ac odio tincidunt, ornare nulla non, cursus odio. Proin venenatis hendrerit egestas. Praesent commodo pharetra felis nec porttitor. Maecenas lacinia turpis eget arcu volutpat, gravida malesuada velit hendrerit. Sed lorem erat, tempor id nunc at, blandit tristique metus. Vivamus magna ligula, facilisis a in. ',
        3,'2025-01-17 12:00:00','2025-01-17 12:00:00'
    );

-- Insertion des relations article-thèmes
INSERT INTO
    ARTICLE_THEMES (article_id, theme_id)
VALUES
    (1, 2), (2, 3), (3, 4), (4, 5), (5, 6), (6, 7),
    (7, 2), (7, 4), (8, 3), (9, 4), (10, 5), (10, 7),
    (11, 3), (12, 2), (12, 6), (12, 7), (13, 2),
    (14, 4), (15, 5), (16, 6), (17, 7), (17, 1),
    (16, 1), (15, 1), (14, 1), (13, 1), (12, 1);

    -- Insertion des commentaires
INSERT INTO
    COMMENTS (content, author_id, article_id, created_at)
VALUES
    ('Super article, merci !', 2, 1,'2025-01-01 12:00:00'),
    ('Très instructif, bravo.', 3, 2,'2025-01-01 12:00:00'),
    ('Merci pour les infos.', 4, 3,'2025-01-01 12:00:00'),
    ('Bonne introduction.', 2, 4,'2025-01-01 12:00:00'),
    ('Article bien écrit.', 3, 5,'2025-01-01 12:00:00'),
    ('Très utile, merci !', 4, 6,'2025-01-01 12:00:00'),
    ('J’ai appris beaucoup.', 2, 7,'2025-01-01 12:00:00'),
    ('Merci pour cet article.', 3, 8,'2025-01-01 12:00:00'),
    ('Très pertinent.', 4, 9,'2025-01-01 12:00:00'),
    ('Explications claires.', 2, 10,'2025-01-01 12:00:00'),
    ('Article intéressant.', 3, 11,'2025-01-01 12:00:00'),
    ('J’adore ce thème.', 4, 12,'2025-01-01 12:00:00'),
    ('Bon résumé.', 4, 13,'2025-01-01 12:00:00'),
    ('Informations utiles.', 3, 14,'2025-01-01 12:00:00'),
    ('Contenu très pertinent.', 4, 15,'2025-01-01 12:00:00'),
    ('Merci pour les détails.', 2, 16,'2025-01-01 12:00:00'),
    ('Très bien rédigé.', 3, 17,'2025-01-01 12:00:00'),
    ('C’est exactement ce que je cherchais.', 4, 8,'2025-01-01 12:00:00'),
    ('Bravo pour cet article.', 2, 1,'2025-01-01 12:00:00'),
    ('Hâte de lire la suite.', 3, 5,'2025-01-01 12:00:00');