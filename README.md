# Tlacuilo

Tlacuilo is an educational fintech platform centered around a double-entry,
append-only ledger.

All users, accounts, money and payments are simulated. This project does not
connect to real banks or payment networks and must not be used to handle real
funds.

## Current Progress

Day 1 foundation:

- Maven multi-module repository
- Java 25 and Spring Boot 4.1.2
- Docker-based Maven development environment
- PostgreSQL 18.7 with Docker Compose
- Database roles and schemas for ledger, payments, account and risk
- `Money` value object using `long` minor units
- Unit, parameterized and property-style tests
- JaCoCo coverage
- Ledger service with Actuator health endpoint
- Gitleaks through pre-commit and GitHub Actions
- Dependabot configuration

## Requirements

- Docker
- Docker Compose v2
- GNU Make
- Git

Java and Maven are not required locally. Maven runs inside Docker.

## Development

Create the local environment file:

```bash
cp deploy/compose/.env.dev.example deploy/compose/.env.dev
```
> [!WARNING]
> **Never commit .env.dev.**

## Start PostgreSQL:
```bash
make up
make ps
```
## Stop the development environment:
```bash
make down
```
## Remove the containers, network and database volume:
```bash
make nuke
```
## Run the ledger service:
```bash
make mvn A="-pl services/ledger-service spring-boot:run -Dspring-boot.run.profiles=dev"
```
## Check its health:
```bash
curl -sS http://localhost:9082/actuator/health
```
## Quality Checks
```bash
make lint
make test
git add -A
pre-commit run
```
## Repository Structure: 
```
libs/                  Shared libraries
services/              Deployable services
deploy/compose/        Docker Compose infrastructure
docs/adr/              Architecture Decision Records
.github/               CI and Dependabot configuration
```
## Security
Secrets must not be committed.
The repository uses:
- .gitignore for local environment files
- Gitleaks through pre-commit
- Gitleaks through GitHub Actions
- GitHub secret scanning and push protection
- Dependabot for dependency updates
If a real secret is exposed, revoke and rotate it immediately.
License
This project is licensed under the Apache License 2.0.