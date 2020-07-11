# NFe Emissão API

- Adicione as informações da empresa emitente no arquivo /src/main/resources/emitente.json
- Adicione o certificado da empresa emitente em /src/main/resources/certificado/cert.pfx

Para subir o serviço de banco de dados:
    sudo docker-compose up -d
    
Para fazer o build do projeto
    ./gradlew build                 (Com execução dos testes)
    ./gradlew build -x test         (Sem execução dos testes)
    
Endpoints:
    POST | /api/v1/nfe/envio
    POST | /api/v1/nfe/cancelamento
    GET  | /api/v1/nfe/status/{nfChave}
    GET  | /api/v1/nfe/download/{nfChave}
    





