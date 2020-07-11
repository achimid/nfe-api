# NFe API - Api para emissão de Nota Fiscal Eletrônica

#### Configuração inicial
- Adicione as informações da empresa emitente no arquivo /src/main/resources/emitente.json
- Adicione o certificado da empresa emitente em /src/main/resources/certificado/cert.pfx

###### Para subir o serviço de banco de dados:
    sudo docker-compose up -d
    
###### Para fazer o build do projeto
    ./gradlew build                 (Com execução dos testes)
    ./gradlew build -x test         (Sem execução dos testes)
    
###### Endpoints:
    POST | /api/v1/nfe/envio
    POST | /api/v1/nfe/cancelamento
    GET  | /api/v1/nfe/status/{nfChave}
    GET  | /api/v1/nfe/download/{nfChave}
    
###### TODOs:
 * Adicionar documentação do Swagger
 * Criar paginas front-end para utilização da API
 * Permitir criação de multiplos Emitentes, permitindo upload do certificado
 * Implementar testes dos endpoints REST
    





