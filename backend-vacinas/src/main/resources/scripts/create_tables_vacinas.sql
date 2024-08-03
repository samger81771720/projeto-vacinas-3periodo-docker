create schema if not exists VACINAS;

use VACINAS;

create table if not exists VACINAS.ENDERECO(
	id INT AUTO_INCREMENT NOT NULL,
    logradouro VARCHAR(250) NOT NULL,
    numero VARCHAR(10),
    complemento VARCHAR(250),
    bairro VARCHAR(100) NOT NULL,
    localidade VARCHAR(100) NOT NULL,
    estado VARCHAR(100) NOT NULL,
    cep VARCHAR(9) NOT NULL,
    pais VARCHAR(10) NOT NULL,
    CONSTRAINT ENDERECO_pk PRIMARY KEY (id)
); 

	
create table if not exists VACINAS.CONTATO(
	id integer auto_increment not null,
	telefone varchar(15) not null,
	email varchar(100),
	constraint CONTATO_pk primary key(id)
); 


create table if not exists VACINAS.PESSOA(
	id integer auto_increment not null,
	idEndereco integer not null,
	idContato integer not null,
	nome varchar(255) not null,
	dataNascimento date not null,
	sexo char(1) not null comment 'M = MASCULINO, F = FEMININO, O = OUTROS',
	cpf varchar(11) unique not null,
	login varchar(255) not null unique,
	senha varchar(255) not null,
	tipo integer not null comment '1 = USUÁRIO, 2 = ADMINISTRADOR',
	doencaPreexistente boolean not null default false comment 'TRUE = Possui doença preexistente, FALSE = Não possui doença preexistente',
	constraint PESSOA_pk primary key(id),
	constraint CONTATO_PESSOA_fk foreign key(idContato) references VACINAS.CONTATO(id),
	constraint ENDERECO_PESSOA_fk foreign key (idEndereco) references VACINAS.ENDERECO(id)
); 


create table if not exists VACINAS.UNIDADE(
	id integer auto_increment not null,
	idEndereco integer not null,
	idContato integer not null,
	nome varchar(100) not null,
	constraint UNIDADE_pk primary key(id),
	constraint ENDERECO_UNIDADE_fk foreign key(idEndereco) references VACINAS.ENDERECO(id),
	constraint CONTATO_UNIDADE_fk foreign key(idContato) references VACINAS.CONTATO(id)
); 

create table if not exists VACINAS.FABRICANTE(
	id integer auto_increment not null,
	idEndereco integer not null,
	idContato integer not null,
	nome varchar(100) not null,
	constraint FABRICANTE_pk primary key(id),
	constraint ENDERECO_FABRICANTE_fk foreign key(idEndereco) references VACINAS.ENDERECO(id),
	constraint CONTATO_FABRICANTE_fk foreign key(idContato) references VACINAS.CONTATO(id)
); 

create table if not exists VACINAS.VACINA(
	id integer auto_increment not null,
	idFabricante integer not null,
	nome varchar(100) not null,
	categoria varchar(100) not null comment 'Gripe, Corona Vírus, Hepatite, Tétano, etc',
	idadeMinima integer not null,
	idadeMaxima integer not null,
	contraIndicacao boolean not null default false comment 'TRUE = Contra indicada para pessoas com doenças preexistentes, FALSE = Não possui nenhuma contra indicação',
	constraint VACINA_pk primary key(id),
	constraint FABRICANTE_VACINA_fk foreign key(idFabricante) references VACINAS.FABRICANTE(id)
); -- OK

create table if not exists VACINAS.APLICACAO(
	id integer not null auto_increment,
	idPessoa integer not null,
	idVacina integer not null,
	idUnidade integer not null,
	dataAplicacao date not null,
	constraint APLICACAO_pk primary key(id),
	constraint PESSOA_APLICACAO_fk foreign key(idPessoa) references VACINAS.PESSOA(id),
	constraint VACINA_APLICACAO_fk foreign key(idVacina) references VACINAS.VACINA(id),
	constraint UNIDADE_APLICACAO_fk foreign key(idUnidade) references VACINAS.UNIDADE(id)
); -- OK


-- "NÃO É POSSÍVEL INSERIR OU ATUALIZAR REGISTROS NA TABELA ESTOQUE COM VALORES DUPLICADOS DE idUnidade e idVacina"
create table if not exists VACINAS.ESTOQUE(
	idUnidade INTEGER not null,
	idVacina INTEGER not null,
	quantidade INTEGER NOT NULL CHECK (quantidade >= 0),
	dataLote date not null,
	validade date not null,
	constraint ESTOQUE_pk primary key(idUnidade,idVacina),
	constraint UNIDADE_ESTOQUE_fk foreign key(idUnidade) references VACINAS.UNIDADE(id),
	constraint VACINA_ESTOQUE_fk foreign key(idVacina) references VACINAS.VACINA(id)
);
   
   -- REGISTROS DE ENDEREÇOS

INSERT INTO VACINAS.ENDERECO (logradouro, numero, complemento, bairro, localidade, estado, cep, pais) VALUES
('Rua das Flores', '123', 'Apto 101', 'Jardim Primavera', 'São Paulo', 'SP', '1234-678', 'Brasil'),
('Avenida Paulista', '2000', 'Sala 305', 'Bela Vista', 'São Paulo', 'SP', '98765-432', 'Brasil'),
('Rua XV de Novembro', '456', '', 'Centro', 'Curitiba', 'PR', '12312-312', 'Brasil'),
('Rua da Harmonia', '789', 'Casa', 'Boqueirão', 'Curitiba', 'PR','87654-321', 'Brasil'),
('Avenida Brasil', '1001', 'Apto 202', 'Copacabana', 'Rio de Janeiro', 'RJ', '11223-344', 'Brasil'),
('Rua das Laranjeiras', '102', '', 'Laranjeiras', 'Rio de Janeiro', 'RJ', '44332-211', 'Brasil'),
('Rua Bento Gonçalves', '303', '', 'Centro', 'Porto Alegre', 'RS', '55667-788', 'Brasil'),
('Avenida Independência', '404', 'Sala 401', 'Independência', 'Porto Alegre', 'RS', '88776-655', 'Brasil'),
('Rua Sete de Setembro', '505', 'Apto 503', 'Centro', 'Florianópolis', 'SC', '99887-766', 'Brasil'),
('Rua das Palmeiras', '606', '', 'Trindade', 'Florianópolis', 'SC', '66554-433', 'Brasil'),
('Avenida Goiás', '707', 'Apto 707', 'Setor Central', 'Goiânia', 'GO', '22334-455', 'Brasil'),
('Rua 15 de Novembro', '808', 'Sala 810', 'Setor Marista', 'Goiânia', 'GO', '55443-322', 'Brasil'),
('Rua Amazonas', '909', '', 'Centro', 'Belo Horizonte', 'MG', '33445-566', 'Brasil'),
('Avenida Afonso Pena', '100', 'Sala 1001', 'Funcionários', 'Belo Horizonte', 'MG', '66557-788', 'Brasil'),
('Rua José de Alencar', '111', '', 'Centro', 'Fortaleza', 'CE', '77668-899', 'Brasil'),
('Avenida Beira Mar', '222', 'Apto 2201', 'Meireles', 'Fortaleza', 'CE', '99889-977', 'Brasil'),
('Rua das Acácias', '333', '', 'Alphaville', 'Barueri', 'SP', '55668-899', 'Brasil'),
('Avenida Tamboré', '444', 'Sala 404', 'Tamboré', 'Barueri', 'SP', '99887-755', 'Brasil'),
('Rua das Oliveiras', '555', '', 'Centro', 'Campinas', 'SP', '88776-644', 'Brasil'),
('Avenida Orozimbo Maia', '666', 'Sala 606', 'Cambuí', 'Campinas', 'SP', '44332-200', 'Brasil'),
('Rua Rio Grande do Sul', '777', '', 'Centro', 'Salvador', 'BA', '11224-455', 'Brasil'),
('Avenida Oceânica', '888', 'Apto 808', 'Ondina', 'Salvador', 'BA', '99880-077', 'Brasil'),
('Rua Pernambuco', '999', '', 'Centro', 'Recife', 'PE', '22335566', 'Brasil'),
('Avenida Boa Viagem', '101', 'Apto 101', 'Boa Viagem', 'Recife', 'PE', '55446-677', 'Brasil'),
('Rua Maranhão', '202', '', 'Centro', 'Natal', 'RN', '33447-788', 'Brasil'),
('Avenida Roberto Freire', '303', 'Sala 303', 'Ponta Negra', 'Natal', 'RN', '77665-544', 'Brasil'),
('Rua Paraíba', '404', '', 'Centro', 'Manaus', 'AM', '44556-677', 'Brasil'),
('Avenida Djalma Batista', '505', 'Sala 505', 'Chapada', 'Manaus', 'AM', '77889-900', 'Brasil'),
('Rua Rio de Janeiro', '606', '', 'Centro', 'Belém', 'PA', '11226-677', 'Brasil'),
('Avenida Almirante Barroso', '707', 'Apto 707', 'Marco', 'Belém', 'PA', '33449-900', 'Brasil');


-- REGISTROS DE CONTATOS DE EMPRESAS

INSERT INTO VACINAS.CONTATO (telefone, email) VALUES
('11987654331', 'pfizer@empresa_pfizer.com'),
('11987654332', 'moderna@empresa_moderna.com'),
('11987654333', 'astrazeneca@empresa_astrazeneca.com'),
('11987654334', 'johnson.johnson@empresa_johnsonjohnson.com'),
('11987654335', 'sanofi@empresa_sanofi.com'),
('11987654336', 'gsk@empresa_gsk.com'),
('11987654337', 'merck@empresa_merck.com'),
('11987654338', 'novavax@empresa_novavax.com'),
('11987654339', 'sinovac@empresa_sinovac.com'),
('11987654340', 'bharat@empresa_bharat.com');

-- REGISTROS DE CONTATOS DE PESSOAS

INSERT INTO VACINAS.CONTATO (telefone, email) VALUES
('11987654341', 'joao.silva@gmail.com'),
('11987654342', 'maria.oliveira@gmail.com'),
('11987654343', 'carlos.santos@gmail.com'),
('11987654344', 'ana.mendes@gmail.com'),
('11987654345', 'paulo.souza@gmail.com'),
('11987654346', 'lucia.costa@gmail.com'),
('11987654347', 'fernando.alves@gmail.com'),
('11987654348', 'marina.rocha@gmail.com'),
('11987654349', 'julio.ferreira@gmail.com'),
('11987654350', 'amanda.ribeiro@gmail.com');

-- REGISTROS DE CONTATOS DE UNIDADES DE SAÚDE

INSERT INTO VACINAS.CONTATO (telefone, email) VALUES
('1123456789', 'contato@hospitalcentral.com'),
('1123456790', 'informacoes@clinicafamilia.com'),
('1123456791', 'contato@ubsanita.com'),
('1123456792', 'atendimento@hospitalsaopaulo.com'),
('1123456793', 'info@upaminasgerais.com'),
('1123456794', 'contato@hospitalsantamaria.com'),
('1123456795', 'contato@ubsjardim.com'),
('1123456796', 'info@clinicafloripa.com'),
('1123456797', 'atendimento@hospitalnorte.com'),
('1123456798', 'contato@ubscentro.com');





-- REGISTROS DE PESSOAS

INSERT INTO VACINAS.PESSOA (idEndereco, idContato, nome, dataNascimento, sexo, cpf, login, senha, tipo, doencaPreexistente) VALUES
(1, 11, 'João Silva', '1980-01-15', 'M', '12345678901', 'joao.silva1', 'senhaPadrao123', 2, false),
(2, 12, 'Maria Oliveira', '1975-02-20', 'F', '23456789012', 'maria.oliveira2', 'senhaPadrao124', 1, false),
(3, 13, 'Carlos Santos', '1988-03-10', 'M', '34567890123', 'carlos.santos3', 'senhaPadrao125', 2, false),
(4, 14, 'Ana Mendes', '1990-04-25', 'F', '45678901234', 'ana.mendes4', 'senhaPadrao126', 1, false),
(5, 15, 'Paulo Souza', '1985-05-30', 'M', '56789012345', 'paulo.souza5', 'senhaPadrao127', 2, false),
(6, 16, 'Lucia Costa', '1992-06-15', 'F', '67890123456', 'lucia.costa6', 'senhaPadrao128', 1, false),
(7, 17, 'Fernando Alves', '1983-07-22', 'M', '78901234567', 'fernando.alves7', 'senhaPadrao129', 2, false),
(8, 18, 'Marina Rocha', '1987-08-14', 'F', '89012345678', 'marina.rocha8', 'senhaPadrao121', 1, false),
(9, 19, 'Julio Ferreira', '1995-09-05', 'M', '90123456789', 'julio.ferreira9', 'senhaPadrao122', 2, false),
(10, 20, 'Amanda Ribeiro', '1991-10-18', 'F', '01234567890', 'amanda.ribeiro10', 'senhaPadrao133', 1, false);

-- REGISTROS DE FABRICANTES

INSERT INTO VACINAS.FABRICANTE (idEndereco, idContato, nome) VALUES
(11, 1, 'Pfizer'),
(12, 2, 'Moderna'),
(13, 3, 'AstraZeneca'),
(14, 4, 'Johnson & Johnson'),
(15, 5, 'Sanofi'),
(16, 6, 'GSK'),
(17, 7, 'Merck'),
(18, 8, 'Novavax'),
(19, 9, 'Sinovac'),
(20, 10, 'Bharat');

-- REGISTROS DE UNIDADES DE SAÚDE

INSERT INTO VACINAS.UNIDADE (idEndereco, idContato, nome) VALUES
(21, 21, 'Hospital Central'),
(22, 22, 'Clínica da Família'),
(23, 23, 'UBS Anitá'),
(24, 24, 'Hospital São Paulo'),
(25, 25, 'UPA Minas Gerais'),
(26, 26, 'Hospital Santa Maria'),
(27, 27, 'UBS Jardim'),
(28, 28, 'Clínica Floripa'),
(29, 29, 'Hospital Norte'),
(30, 30, 'UBS Centro');


-- REGISTROS DE VACINAS

INSERT INTO VACINAS.VACINA (idFabricante, nome, categoria, idadeMinima, idadeMaxima, contraIndicacao) VALUES
(1, 'Comirnaty', 'Corona Vírus', 12, 99, false),
(1, 'Prevenar 13', 'Pneumonia', 2, 99, true),
(2, 'Spikevax', 'Corona Vírus', 12, 99, false),
(2, 'FluMist', 'Gripe', 2, 49, true),
(3, 'Vaxzevria', 'Corona Vírus', 18, 99, false),
(3, 'Fluenz', 'Gripe', 2, 49, true),
(4, 'Ad26.COV2.S', 'Corona Vírus', 18, 99, false),
(4, 'Janssen', 'Gripe', 2, 99, true),
(5, 'Fluzone', 'Gripe', 6, 99, false),
(5, 'Vaxigrip', 'Gripe', 6, 99, true),
(6, 'Shingrix', 'Herpes Zoster', 50, 99, false),
(6, 'Fluarix', 'Gripe', 6, 99, true),
(7, 'Gardasil', 'HPV', 9, 45, false),
(7, 'M-M-R II', 'Sarampo', 12, 99, true),
(8, 'Nuvaxovid', 'Corona Vírus', 18, 99, false),
(8, 'NanoFlu', 'Gripe', 18, 99, true),
(9, 'CoronaVac', 'Corona Vírus', 18, 99, false),
(9, 'Healive', 'Hepatite A', 1, 99, true),
(10, 'Covaxin', 'Corona Vírus', 18, 99, false),
(10, 'BioPolio', 'Poliomielite', 0, 99, true),
(1, 'Trumenba', 'Meningite', 10, 25, false),
(1, 'Prevnar 20', 'Pneumonia', 2, 99, true),
(2, 'RabAvert', 'Raiva', 0, 99, false),
(2, 'Varivax', 'Catapora', 12, 99, true),
(3, 'Vaxigrip Tetra', 'Gripe', 6, 99, false),
(3, 'Menjugate', 'Meningite', 2, 99, true),
(4, 'Bexsero', 'Meningite', 2, 25, false),
(4, 'Gardasil 9', 'HPV', 9, 45, true),
(5, 'ActHIB', 'Haemophilus influenzae', 2, 99, false),
(5, 'Pentacel', 'Difteria, Tétano, Pertussis, Polio, Hib', 2, 99, true),
(6, 'Boostrix', 'Tétano, Difteria, Pertussis', 10, 99, false),
(6, 'Engerix-B', 'Hepatite B', 0, 99, true);



-- REGISTROS DE APLICAÇÃO


INSERT INTO VACINAS.APLICACAO (idPessoa, idVacina, idUnidade, dataAplicacao) VALUES
(10, 30, 9, '2023-01-30'),
(10, 1, 9, '2023-01-30'),
(1, 1, 1, '2023-01-01'),
(2, 2, 2, '2023-01-02'),
(3, 3, 3, '2023-01-03'),
(4, 4, 4, '2023-01-04'),
(5, 5, 5, '2023-01-05'),
(6, 6, 6, '2023-01-06'),
(7, 7, 7, '2023-01-07'),
(8, 8, 8, '2023-01-08'),
(9, 9, 9, '2023-01-09'),
(10, 10, 10, '2023-01-10'),
(1, 11, 1, '2023-01-11'),
(2, 12, 2, '2023-01-12'),
(3, 13, 3, '2023-01-13'),
(4, 14, 4, '2023-01-14'),
(5, 15, 5, '2023-01-15'),
(6, 16, 6, '2023-01-16'),
(7, 17, 7, '2023-01-17'),
(8, 18, 8, '2023-01-18'),
(9, 19, 9, '2023-01-19'),
(10, 20, 10, '2023-01-20'),
(1, 21, 1, '2023-01-21'),
(2, 22, 2, '2023-01-22'),
(3, 23, 3, '2023-01-23'),
(4, 24, 4, '2023-01-24'),
(5, 25, 5, '2023-01-25'),
(6, 26, 6, '2023-01-26'),
(7, 27, 7, '2023-01-27'),
(8, 28, 8, '2023-01-28'),
(9, 29, 9, '2023-01-29'),
(10, 30, 10, '2023-01-30'),
(1, 1, 1, '2023-01-01'),
(2, 2, 2, '2023-01-02'),
(3, 3, 3, '2023-01-03'),
(4, 4, 4, '2023-01-04'),
(5, 5, 5, '2023-01-05'),
(6, 6, 6, '2023-01-06'),
(7, 7, 7, '2023-01-07'),
(8, 8, 8, '2023-01-08'),
(9, 9, 9, '2023-01-09'),
(10, 10, 10, '2023-01-10'),
(1, 11, 1, '2023-01-11'),
(2, 12, 2, '2023-01-12'),
(3, 13, 3, '2023-01-13'),
(4, 14, 4, '2023-01-14'),
(5, 15, 5, '2023-01-15'),
(6, 16, 6, '2023-01-16'),
(7, 17, 7, '2023-01-17'),
(8, 18, 8, '2023-01-18'),
(9, 19, 9, '2023-01-19'),
(10, 20, 10, '2023-01-20'),
(1, 21, 1, '2023-01-21'),
(2, 22, 2, '2023-01-22'),
(3, 23, 3, '2023-01-23'),
(4, 24, 4, '2023-01-24'),
(5, 25, 5, '2023-01-25'),
(6, 26, 6, '2023-01-26'),
(7, 27, 7, '2023-01-27'),
(8, 28, 8, '2023-01-28'),
(9, 29, 9, '2023-01-29'),
(10, 30, 10, '2023-01-30'),
(1, 1, 1, '2023-01-01'),
(2, 2, 2, '2023-01-02'),
(3, 3, 3, '2023-01-03'),
(4, 4, 4, '2023-01-04'),
(5, 5, 5, '2023-01-05'),
(6, 6, 6, '2023-01-06'),
(7, 7, 7, '2023-01-07'),
(8, 8, 8, '2023-01-08'),
(9, 9, 9, '2023-01-09'),
(9, 8, 9, '2023-01-23'),
(9, 20, 9, '2023-01-22'),
(9, 11, 9, '2023-01-21'),
(9, 10, 9, '2023-01-20'),
(9, 2, 9, '2023-01-19'),
(10, 10, 10, '2023-01-10'),
(1, 11, 1, '2023-01-11'),
(2, 12, 2, '2023-01-12'),
(3, 13, 3, '2023-01-13'),
(4, 14, 4, '2023-01-14'),
(5, 15, 5, '2023-01-15'),
(6, 16, 6, '2023-01-16'),
(7, 17, 7, '2023-01-17'),
(8, 18, 8, '2023-01-18'),
(9, 19, 9, '2023-01-19'),
(10, 20, 10, '2023-01-20'),
(1, 21, 1, '2023-01-21'),
(2, 22, 2, '2023-01-22'),
(3, 23, 3, '2023-01-23'),
(4, 24, 4, '2023-01-24'),
(5, 25, 5, '2023-01-25'),
(6, 26, 6, '2023-01-26'),
(7, 27, 7, '2023-01-27'),
(8, 28, 8, '2023-01-28'),
(9, 29, 9, '2023-01-29'),
(9, 16, 8, '2023-01-29'),
(9, 15, 7, '2023-01-29'),
(9, 14, 6, '2023-01-29'),
(9, 13, 5, '2023-01-29'),
(9, 12, 4, '2023-01-29'),
(9, 11, 3, '2023-01-29'),
(9, 10, 2, '2023-01-29'),
(10, 30, 10, '2023-01-30'),
(10, 29, 10, '2023-01-30'),
(10, 28, 10, '2023-01-30'),
(10, 27, 10, '2023-01-30'),
(10, 26, 10, '2023-01-30'),
(10, 25, 10, '2023-01-30'),
(10, 24, 10, '2023-01-30'),
(10, 23, 10, '2023-01-30'),
(10, 22, 10, '2023-01-30'),
(10, 21, 10, '2023-01-30');





-- REGISTROS DE ESTOQUE

	INSERT INTO VACINAS.ESTOQUE (idUnidade, idVacina, quantidade, dataLote, validade) VALUES
    -- Unidade 1
    (1, 1, 50, '2024-01-01', '2025-01-01'),
    (1, 2, 60, '2024-02-01', '2025-02-01'),
    (1, 3, 70, '2024-03-01', '2025-03-01'),
    (1, 5, 50, '2024-03-20', '2025-03-21'),
    (1, 32, 50, '2024-02-20', '2025-05-21'),
    (1, 31, 100, '2024-04-20', '2025-10-21'),
    (1, 30, 90, '2024-04-21', '2025-10-21'),
    (1, 29, 10, '2024-04-21', '2025-10-21'),
    (1, 28, 5, '2024-04-21', '2025-10-21'),
    (1, 27, 34, '2024-04-21', '2025-10-21'),
    (1, 26, 21, '2024-04-21', '2025-10-21'),
    (1, 25, 60, '2024-04-21', '2025-10-21'),
    (1, 24, 40, '2024-04-21', '2025-10-21');
   
       
    
    -- Unidade 2
    INSERT INTO VACINAS.ESTOQUE (idUnidade, idVacina, quantidade, dataLote, validade) VALUES
    (2, 4, 55, '2024-01-02', '2025-01-02'),
    (2, 5, 65, '2024-02-02', '2025-02-02'),
    (2, 1, 75, '2024-03-02', '2025-03-02'),
    (2, 28, 10, '2024-03-02', '2025-03-02'),
    (2, 27, 10, '2024-03-02', '2025-03-02'),
    (2, 26, 26, '2024-03-02', '2025-03-02'),
    (2, 10, 10, '2024-03-02', '2025-03-02'),
    (2, 9, 20, '2024-03-02', '2025-03-02'),
    (2, 8, 30, '2024-03-02', '2025-03-02'),
    (2, 7, 40, '2024-03-02', '2025-03-02');
   
    
     
   -- Unidade 3
    INSERT INTO VACINAS.ESTOQUE (idUnidade, idVacina, quantidade, dataLote, validade) VALUES
    (3, 23, 15, '2024-01-03', '2025-01-03'),
    (3, 22, 20, '2024-01-03', '2025-01-03'),
    (3, 21, 25, '2024-01-03', '2025-01-03'),
    (3, 20, 30, '2024-01-03', '2025-01-03'),
    (3, 19, 35, '2024-01-03', '2025-01-03'),
    (3, 18, 40, '2024-01-03', '2025-01-03'),
    (3, 17, 45, '2024-01-03', '2025-01-03'),
    (3, 16, 50, '2024-01-03', '2025-01-03'),
    (3, 15, 55, '2024-01-03', '2025-01-03'),
    (3, 14, 60, '2024-01-03', '2025-01-03');
   
   
    -- Unidade 4
   	 INSERT INTO VACINAS.ESTOQUE (idUnidade, idVacina, quantidade, dataLote, validade) VALUES
    (4, 24, 100, '2024-01-04', '2025-01-04'),
	(4, 25, 90, '2024-01-04', '2025-01-04'),
	(4, 26, 80, '2024-01-04', '2025-01-04'),
	(4, 13, 70, '2024-01-04', '2025-01-04'),
	(4, 12, 60, '2024-01-04', '2025-01-04'),
	(4, 11, 50, '2024-01-04', '2025-01-04'),
	(4, 10, 40, '2024-01-04', '2025-01-04'),
	(4, 9, 0, '2024-01-04', '2025-01-04'),
	(4, 8, 0, '2024-01-04', '2025-01-04'),
	(4, 7, 0, '2024-01-04', '2025-01-04');
	

    -- Unidade 5
	 INSERT INTO VACINAS.ESTOQUE (idUnidade, idVacina, quantidade, dataLote, validade) VALUES
    (5, 9, 50, '2024-01-05', '2025-01-05'),
    (5, 8, 50, '2024-01-05', '2025-01-05'),
    (5, 7, 50, '2024-01-05', '2025-01-05'),
    (5, 6, 10, '2024-01-05', '2025-01-05'),
    (5, 5, 20, '2024-01-05', '2025-01-05'),
    (5, 4, 30, '2024-01-05', '2025-01-05'),
    (5, 3, 40, '2024-01-05', '2025-01-05'),
    (5, 2, 50, '2024-01-05', '2025-01-05'),
    (5, 1, 60, '2024-01-05', '2025-01-05'),
    (5, 10, 70, '2024-01-05', '2025-01-05');
