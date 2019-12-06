CREATE TABLE pessoa(
	id serial PRIMARY KEY,
	nome VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL
	
);

INSERT INTO pessoa (nome, email) VALUES ('Jean Pandolfi', 'jean-pandolfi@hotmail.com');
INSERT INTO pessoa (nome, email) VALUES ('Ana Silva', 'ana-silva@gmail.com');

CREATE TABLE tarefa(
	id serial PRIMARY KEY,
	titulo VARCHAR(30) NOT NULL,
	descricao VARCHAR(100) NOT NULL,
	
	id_pessoa INTEGER NOT NULL,
	FOREIGN KEY (id_pessoa) REFERENCES pessoa (id)
);

INSERT INTO tarefa(titulo, descricao, id_pessoa) VALUES ('Trabalho de TPA', 'Desenvolver um algoritmo de fluxo máximo', 1);
INSERT INTO tarefa(titulo, descricao, id_pessoa) VALUES ('Introdução TCC', 'Escrever a introdução do TCC até o dia 29/11', 1);

INSERT INTO tarefa(titulo, descricao, id_pessoa) VALUES ('Reunião', 'Reunião com o cliente do projeto', 2);
INSERT INTO tarefa(titulo, descricao, id_pessoa) VALUES ('Pagamento', 'Pagar boleto do cartão', 2);