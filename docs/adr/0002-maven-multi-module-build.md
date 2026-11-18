# 2. Maven multi-module build

Date: 2026-11-18

## Status

Accepted

## Context

The repository holds several services and a few shared libraries that must build and be tested together with consistent dependency versions. The build tool should be widely known and easy for others to run.

## Decision

Use Maven with a parent POM (`tlacuilo-parent`) that lists the modules and imports the Spring Boot BOM in `dependencyManagement`. Modules do not inherit from `spring-boot-starter-parent`, so plugin versions and the compiler configuration are declared explicitly in the parent.

Rules:

- Services do not depend on each other. They talk through HTTP or events.
- Shared libraries are limited to three: money, events and test support.
- Locally, Maven runs inside a container through `make`, so no JDK is needed on the host. CI uses the Maven Wrapper (`mvnw`).

## Consequences

- One versioned place for dependency and plugin versions.
- Without the starter parent, some defaults must be restored by hand (for example the `repackage` execution and `-parameters`).
- Builds depend on Docker locally, but anyone with a JDK can still use `./mvnw`.
- Gradle was considered; for this size the build speed difference is not significant.
