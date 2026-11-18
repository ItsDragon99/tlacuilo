# 3. Java 25 LTS and Spring Boot 4

Date: 2026-11-18

## Status

Accepted

## Context

The project needs a current, supported runtime and framework. Spring Boot 4 supports Java 17 as a minimum and treats Java 25 (the latest LTS) as a first-class target. Spring Boot 3.5 reached the end of open-source support in June 2026.

## Decision

Use Java 25 (LTS) and Spring Boot 4.1.x, currently 4.1.2, with the Spring Framework 7 line and JUnit 6 that come in its BOM. Pin the exact versions in the parent POM and never use `latest`.

## Consequences

- Long-term support for the runtime and a supported framework line.
- Boot 4 splits the code into many modules, so starter names differ from Boot 3; always generate the POM from start.spring.io and check the real artifact names.
- Jackson 3 changes the main packages (`tools.jackson`), and Testcontainers 2.x renames its modules.
- Spring Cloud must be aligned with the chosen Boot version before the gateway module is added.
