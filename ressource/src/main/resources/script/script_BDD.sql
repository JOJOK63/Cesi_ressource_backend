
CREATE TABLE utilisateur(
        id_utilisateur Int  Auto_increment  NOT NULL ,
        userName       Varchar (50) NOT NULL ,
        password       Varchar (50) NOT NULL ,
        email          Varchar (50) NOT NULL ,
        birthDate      Date NOT NULL ,
        permissions    Int NOT NULL ,
        strike         Int NOT NULL ,
        active         Boolean NOT NULL
	,CONSTRAINT utilisateur_PK PRIMARY KEY (id_utilisateur)
)ENGINE=InnoDB;

CREATE TABLE ressources(
        id_ressources  Int  Auto_increment  NOT NULL ,
        title          Varchar (100) NOT NULL ,
        description    Mediumtext NOT NULL ,
        creationDate   Date NOT NULL ,
        endDate        Date ,
        picture        TinyText NOT NULL ,
        id_utilisateur Int NOT NULL
	,CONSTRAINT ressources_PK PRIMARY KEY (id_ressources)
	,CONSTRAINT ressources_utilisateur_FK FOREIGN KEY (id_utilisateur) REFERENCES utilisateur(id_utilisateur)
)ENGINE=InnoDB;


CREATE TABLE comments(
        id_comments    Int  Auto_increment  NOT NULL ,
        message        Mediumtext NOT NULL ,
        datePost       Datetime NOT NULL ,
        id_utilisateur Int NOT NULL ,
        id_ressources  Int NOT NULL
	,CONSTRAINT comments_PK PRIMARY KEY (id_comments)

	,CONSTRAINT comments_utilisateur_FK FOREIGN KEY (id_utilisateur) REFERENCES utilisateur(id_utilisateur)
	,CONSTRAINT comments_ressources0_FK FOREIGN KEY (id_ressources) REFERENCES ressources(id_ressources)
)ENGINE=InnoDB;


CREATE TABLE tag(
        id_tag  Int  Auto_increment  NOT NULL ,
        libelle Varchar (50) NOT NULL
	,CONSTRAINT tag_PK PRIMARY KEY (id_tag)
)ENGINE=InnoDB;


CREATE TABLE groupes(
        id_groupes Int  Auto_increment  NOT NULL ,
        libelle    Varchar (50) NOT NULL ,
        private    Boolean NOT NULL
	,CONSTRAINT groupes_PK PRIMARY KEY (id_groupes)
)ENGINE=InnoDB;


CREATE TABLE message(
        id_message     Int  Auto_increment  NOT NULL ,
        content        Text NOT NULL ,
        datePost       Datetime NOT NULL ,
        isDelete       Boolean NOT NULL ,
        id_utilisateur Int NOT NULL ,
        id_groupes     Int NOT NULL
	,CONSTRAINT message_PK PRIMARY KEY (id_message)

	,CONSTRAINT message_utilisateur_FK FOREIGN KEY (id_utilisateur) REFERENCES utilisateur(id_utilisateur)
	,CONSTRAINT message_groupes0_FK FOREIGN KEY (id_groupes) REFERENCES groupes(id_groupes)
)ENGINE=InnoDB;

CREATE TABLE permissions(
        id_permissions  Int  Auto_increment  NOT NULL ,
        libelle         Varchar (50) NOT NULL ,
        private         Boolean NOT NULL
	,CONSTRAINT permissions_PK PRIMARY KEY (id_permissions)
)ENGINE=InnoDB;


CREATE TABLE typeLibelle(
        id_typeLibelle Int NOT NULL ,
        libelle        Varchar (50) NOT NULL
	,CONSTRAINT typeLibelle_PK PRIMARY KEY (id_typeLibelle)
)ENGINE=InnoDB;


CREATE TABLE signalement(
        id_signalement Int  Auto_increment  NOT NULL ,
        object         Varchar (50) NOT NULL ,
        context        Mediumtext NOT NULL ,
        date           Date NOT NULL ,
        id_utilisateur Int NOT NULL ,
        id_typeLibelle Int NOT NULL ,
        id_ressources  Int ,
        id_comments    Int
	,CONSTRAINT signalement_PK PRIMARY KEY (id_signalement)
	,CONSTRAINT signalement_utilisateur_FK FOREIGN KEY (id_utilisateur) REFERENCES utilisateur(id_utilisateur)
	,CONSTRAINT signalement_typeLibelle0_FK FOREIGN KEY (id_typeLibelle) REFERENCES typeLibelle(id_typeLibelle)
	,CONSTRAINT signalement_ressources1_FK FOREIGN KEY (id_ressources) REFERENCES ressources(id_ressources)
	,CONSTRAINT signalement_comments2_FK FOREIGN KEY (id_comments) REFERENCES comments(id_comments)
)ENGINE=InnoDB;


CREATE TABLE posseder(
        id_ressources Int NOT NULL ,
        id_tag        Int NOT NULL
	,CONSTRAINT posseder_PK PRIMARY KEY (id_ressources,id_tag)
	,CONSTRAINT posseder_ressources_FK FOREIGN KEY (id_ressources) REFERENCES ressources(id_ressources)
	,CONSTRAINT posseder_tag0_FK FOREIGN KEY (id_tag) REFERENCES tag(id_tag)
)ENGINE=InnoDB;


CREATE TABLE favoris(
        id_ressources  Int NOT NULL ,
        id_utilisateur Int NOT NULL
	,CONSTRAINT favoris_PK PRIMARY KEY (id_ressources,id_utilisateur)

	,CONSTRAINT favoris_ressources_FK FOREIGN KEY (id_ressources) REFERENCES ressources(id_ressources)
	,CONSTRAINT favoris_utilisateur0_FK FOREIGN KEY (id_utilisateur) REFERENCES utilisateur(id_utilisateur)
)ENGINE=InnoDB;


CREATE TABLE modificationRessource(
        id_utilisateur Int NOT NULL ,
        id_ressources  Int NOT NULL ,
        changeDate     Datetime NOT NULL ,
        oldTitle       Varchar (100) NOT NULL ,
        oldDescription Mediumtext NOT NULL ,
        oldPicture     TinyText NOT NULL
	,CONSTRAINT modificationRessource_PK PRIMARY KEY (id_utilisateur,id_ressources)
	,CONSTRAINT modificationRessource_utilisateur_FK FOREIGN KEY (id_utilisateur) REFERENCES utilisateur(id_utilisateur)
	,CONSTRAINT modificationRessource_ressources0_FK FOREIGN KEY (id_ressources) REFERENCES ressources(id_ressources)
)ENGINE=InnoDB;


CREATE TABLE modificationCommentaire(
        id_comments                Int NOT NULL ,
        id_utilisateur             Int NOT NULL ,
        id_modificationCommentaire Int NOT NULL ,
        changeDate                 Datetime NOT NULL ,
        oldComment                 Mediumtext NOT NULL
	,CONSTRAINT modificationCommentaire_PK PRIMARY KEY (id_comments,id_utilisateur)

	,CONSTRAINT modificationCommentaire_comments_FK FOREIGN KEY (id_comments) REFERENCES comments(id_comments)
	,CONSTRAINT modificationCommentaire_utilisateur0_FK FOREIGN KEY (id_utilisateur) REFERENCES utilisateur(id_utilisateur)
)ENGINE=InnoDB;

CREATE TABLE noter(
        id_ressources  Int NOT NULL ,
        id_utilisateur Int NOT NULL ,
        note           Int NOT NULL
	,CONSTRAINT noter_PK PRIMARY KEY (id_ressources,id_utilisateur)

	,CONSTRAINT noter_ressources_FK FOREIGN KEY (id_ressources) REFERENCES ressources(id_ressources)
	,CONSTRAINT noter_utilisateur0_FK FOREIGN KEY (id_utilisateur) REFERENCES utilisateur(id_utilisateur)
)ENGINE=InnoDB;

CREATE TABLE participer(
        id_groupes     Int NOT NULL ,
        id_utilisateur Int NOT NULL
	,CONSTRAINT participer_PK PRIMARY KEY (id_groupes,id_utilisateur)

	,CONSTRAINT participer_groupes_FK FOREIGN KEY (id_groupes) REFERENCES groupes(id_groupes)
	,CONSTRAINT participer_utilisateur0_FK FOREIGN KEY (id_utilisateur) REFERENCES utilisateur(id_utilisateur)
)ENGINE=InnoDB;


CREATE TABLE referencer(
        id_utilisateur  Int NOT NULL ,
        id_permissions  Int NOT NULL
	,CONSTRAINT referencer_PK PRIMARY KEY (id_utilisateur,id_permissions)

	,CONSTRAINT referencer_utilisateur_FK FOREIGN KEY (id_utilisateur) REFERENCES utilisateur(id_utilisateur)
	,CONSTRAINT referencer_permissions0_FK FOREIGN KEY (id_permissions) REFERENCES permissions(id_permissions)
)ENGINE=InnoDB;

