CREATE TABLE usuario(
	id serial PRIMARY KEY,
	nome VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	senha VARCHAR(200) NOT NULL
);

INSERT INTO usuario (nome, email, senha) VALUES ('Jean Pandolfi', 'jean-pandolfi@hotmail.com', 'jean');
INSERT INTO usuario (nome, email, senha) VALUES ('Ana Silva', 'ana-silva@gmail.com', 'ana');