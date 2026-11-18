# 1. Monorepo with web

Date: 2026-11-18

## Status

Accepted

## Context

Tlacuilo is a portfolio project made of several backend services (Spring Boot), shared libraries, a React web client, deployment manifests and observability configuration. One person builds and reviews all of it, and a change often touches more than one part (for example, an event contract and its consumers).

## Decision

Keep everything in a single Git repository: `services/`, `libs/`, `web/`, `deploy/`, `infra/`, `observability/`, `tests/`, `tools/` and `docs/`. The `web/` client lives next to the services instead of in a separate repository.

## Consequences

- One pull request can change a service, its contract and its client, and CI verifies them together.
- A single place to browse the whole system, which suits a portfolio.
- CI must stay selective (build only what changed) as the repository grows.
- Services and the web client still deploy independently: sharing a repository does not mean sharing a release.

## Project name

**Tlacuilo** is a Nahuatl word for a scribe or painter: the person who wrote and painted codices, the books in which Mesoamerican societies recorded history, tribute and accounts.

It fits because the core of this project is a ledger. Like a tlacuilo, the system's job is to write down what happened (who moved what, and when) faithfully and durably, so that the record can be trusted and checked later.
