## Debezium Kafka CDC Sample

This project demonstrates a simple CDC (Change Data Capture) flow using Postgres + Debezium + Kafka, with two Spring Boot microservices: a producer that writes to the database and a consumer that reads events from Kafka.

### Prerequisites
- Docker and Docker Compose installed
- Java 17+ and Maven (optional if you want to run services locally outside containers)

### How to start
1. Bring up the infrastructure (Zookeeper, Kafka, Postgres and Debezium Connect):
   ```bash
   docker compose up -d
   ```
   Wait for the `debezium-setup` container to register the connector (it automatically performs the POST once Debezium is up).

2. Run the Spring Boot services locally (in separate terminals):
   ```bash
   ./mvnw -f producer-service/pom.xml spring-boot:run
   ```
   ```bash
   ./mvnw -f consumer-service/pom.xml spring-boot:run
   ```
   - Producer runs at `http://localhost:8091`
   - Consumer runs at `http://localhost:8092`

Note: the `producer-service` connects to the Docker Postgres via `localhost:5432` (see `application.yml`).

### How to test
1. Create a product (this persists to Postgres and emits CDC events via Debezium to Kafka):
   ```bash
   curl -X POST http://localhost:8091/products \
     -H "Content-Type: application/json" \
     -d '{
       "name": "Sample Product",
       "description": "Test description",
       "price": 99.9
     }'
   ```

2. Check `consumer-service` logs to confirm the event consumption:
   - In the terminal where the consumer is running, you should see messages from the Debezium-generated topic (prefix `dbserver1`), related to the `public.product` table.

3. (Optional) Inspect Kafka messages directly:
   ```bash
   docker exec -it kafka kafka-console-consumer \
     --bootstrap-server localhost:9092 \
     --topic dbserver1.public.product \
     --from-beginning
   ```

### Shutdown
```bash
docker compose down -v
```

This removes containers and volumes, cleaning the database and topics state.

