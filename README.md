# rpe-desafio

1 - Inicialmente necessário rodar o comando:
docker-compose up

2 - Posteriomente o comando:
aws configure

3 - Quando solicitado, insira os seguintes valores:

Para "AWS Access Key ID [None]": test
Para "AWS Secret Access Key [None]": test
Para "Default region name [None]": us-east-1
Para "Default output format [None]": json

 4 - Criar a fila
 aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name cards-register

5 - É necessário utilizar o InteliJJ para subir os tres projetos

Informações:
Projeto product-service está rodando na porta 8081
Projeto card-service está rodando na porta 8082
Projeto carrier-service está rodando na porta 8080

Na raiz do repositório possui a collection do Postman com as URLS.





