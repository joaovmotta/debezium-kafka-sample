# Debezium Kafka CDC Sample

A sample project demonstrating Change Data Capture (CDC) using Debezium, Kafka, and Spring Boot with PostgreSQL.

## What is this?

This project captures real-time database changes (INSERT, UPDATE, DELETE) from PostgreSQL and streams them to Kafka using Debezium. A Spring Boot consumer application processes these events.

## Prerequisites

- Docker & Docker Compose
- Java 17+
- Maven

## How to Run

### 1. Start the infrastructure

```bash
docker-compose up -d
This starts PostgreSQL, Kafka, Zookeeper, and Debezium Connect. The Debezium connector is automatically configured after startup.

bash
Copy
cd producer-service
mvn spring-boot:run
Consumer service (port 8092):

bash
Copy
cd consumer-service
mvn spring-boot:run
4. Test it
Insert a product:

bash
Copy
curl -X POST http://localhost:8091/products -H "Content-Type: application/json" -d '{"name": "Laptop", "price": 1200.00}'
Check the consumer logs to see the CDC event.

Stop
bash
Copy
docker-compose down -v
