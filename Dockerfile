# Usar a imagem do LocalStack
FROM localstack/localstack

RUN mkdir /etc/localstack/init/ready.d

# Copia o script de inicialização para o contêiner
COPY init-aws.sh /etc/localstack/init/ready.d

# Define as permissões de execução para o script
RUN chmod +x /etc/localstack/init/ready.d/init-aws.sh