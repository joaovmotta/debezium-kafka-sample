## Debezium Kafka CDC Sample

Este projeto demonstra um fluxo simples de CDC (Change Data Capture) com Postgres + Debezium + Kafka, e dois microsserviços Spring Boot: um produtor que escreve no banco e um consumidor que lê eventos do Kafka.

### Pré-requisitos
- Docker e Docker Compose instalados
- Java 17+ e Maven (opcional se quiser rodar os serviços localmente fora de containers)

### Como iniciar
1. Suba a infraestrutura (Zookeeper, Kafka, Postgres e Debezium Connect):
   ```bash
   docker compose up -d
   ```
   Aguarde o container `debezium-setup` registrar o conector (ele já faz o POST automático após o Debezium subir).

2. Rode os serviços Spring Boot localmente (em terminais separados):
   ```bash
   ./mvnw -f producer-service/pom.xml spring-boot:run
   ```
   ```bash
   ./mvnw -f consumer-service/pom.xml spring-boot:run
   ```
   - Producer sobe em `http://localhost:8091`
   - Consumer sobe em `http://localhost:8092`

Observação: o `producer-service` conecta no Postgres do Docker através de `localhost:5432` (conforme `application.yml`).

### Como testar
1. Criar um produto (isso persiste no Postgres e gera eventos CDC via Debezium para o Kafka):
   ```bash
   curl -X POST http://localhost:8091/products \
     -H "Content-Type: application/json" \
     -d '{
       "name": "Produto Exemplo",
       "description": "Descrição de teste",
       "price": 99.9
     }'
   ```

2. Verificar logs do `consumer-service` para confirmar o consumo do evento:
   - No terminal onde o consumer está rodando, você deverá ver mensagens do tópico gerado pelo Debezium (prefixo `dbserver1`), referentes à tabela `public.product`.

3. (Opcional) Inspecionar mensagens no Kafka diretamente:
   ```bash
   docker exec -it kafka kafka-console-consumer \
     --bootstrap-server localhost:9092 \
     --topic dbserver1.public.product \
     --from-beginning
   ```

### Encerrar
```bash
docker compose down -v
```

Isso remove containers e volumes, limpando o estado do banco e dos tópicos.


