# Lista de Desejos
###Introdução

A ideia é de um Microserviço para gerencia uma Wishlist (lista de desejos) do cliente em e-commerce. O cliente será capaz de realizar a buscar e selecionar um produto, inserir ou remove-lo de sua Wishlist. O cliente poderá também visualizar a sua lista de desejo ou vericar se um determinado produto está presente na lista.

#### Processo de instalação

- Instale o IntelliJ IDEA ou a IDE de sua preferência (configure o lombok na IDE)
- Instale a JDK
- Instale o MongoDb

#### Dependencias de software

- IntelliJ IDEA
- Java 17
- Lombok
- Maven
- Spring boot 2.5.4
- MongoDb
	
#### Build e test

Para rodar o código é necessário seguir as etapas abaixo:

- Clone o repositório
- Suba o banco na sua máquina, conforme os passos abaixo
- Builde o código

#### Instalação do MongoDb

- Windows:
	- https://www.mongodb.com -> Download -> Community Server
	- Baixar e realizar a instalação com opção "Complete"
	- https://docs.mongodb.com/manual/tutorial/install-mongodb-on-windows/ -> Set up the MongoDB environment
		- Criar pasta \data\db
		- Acrescentar em PATH: C:\Program Files\MongoDB\Server\3.6\bin (adapte para sua versão)
	- Testar no terminal: mongod
	
- Mac:
	- https://docs.mongodb.com/manual/tutorial/install-mongodb-on-os-x/
	- Instalar brew:
		- https://brew.sh -> executar o comando apresentado na primeira página
	- Instalar o MongoDB:
		- brew install mongodb
	- Criar pasta /data/db:
		- sudo mkdir -p /data/db
	- Liberar acesso na pasta criada
		- whoami (para ver seu nome de usuário, exemplo: nelio)
		- sudo chown -Rv nelio /data/db
	- Testar no terminal:
		- mongod

#### Rotas

| Método  | Rota                                | Descrição                                          | Autenticação |
| ------- | ----------------------------------- | -------------------------------------------------- | ------------ |
| POST    | /users/{userId}/product/{productId} | Adicionar Produto na Withlist do cliente           | Público      |
| GET     | /users/{userId}/product/{productId} | Consultar se produto está na wishlist do cliente   | Público      |
| GET     | /users/{userId}/wishlist            | Consultar todos os produtos da wishlist do cliente | Público      |
| DELETE  | /users/{userId}/product/{productId} | Remover produto da wishlist do cliente             | Público      |

#### Outras rotas desenvolvidas para facilitar os testes

| Método  | Rota                                | Descrição                                          | Autenticação |
| ------- | ----------------------------------- | -------------------------------------------------- | ------------ |
| POST    | /users                              | Cadastar cliente                                   | Público      |
| GET     | /users/{userId}                     | Buscar cliente                                     | Público      |
| PUT     | /users/{userId}                     | Alterar cliente                                    | Público      |
| DELETE  | /users/{userId}                     | Remover cliente                                    | Público      |
| GET     | /users                              | Buscar todos os  cliente cadastrados               | Público      |
| POST    | /products                           | Cadastar produto                                   | Público      |
| GET     | /products/{productId}               | Buscar produto                                     | Público      |
| GET     | /products                           | Buscar todos os produto                            | Público      |
| PUT     | /products/{productId}               | Alterar produto                                    | Público      |
| DELETE  | /products/{productId}               | Remover produto                                    | Público      |

#### Swagger UI

A documentação da API pode ser acessada através do Swagger UI após iniciar o projeto:
- http://localhost:8090/swagger-ui/index.html#/
	
#### Postman Collection

Foi anexado ao projeto a collection do postman para facilitar com os testes das funcionalidades:
- https://github.com/tiagoarantes27/wishlist/blob/master/src/main/resources/documents/wishlist.postman_collection.json


