COMPOSE = docker compose \
	-f deploy/compose/compose.yml \
	-f deploy/compose/compose.dev.yml \
	--env-file deploy/compose/.env.dev

export HOST_UID := $(shell id -u)
export HOST_GID := $(shell id -g)
export DOCKER_GID := $(shell stat -c %g /var/run/docker.sock)
$(shell mkdir -p $(HOME)/.m2)
ifdef DEVCONTAINER
MVN = ./mvnw
else
MVN = docker compose -f deploy/compose/maven.yml run --rm mvn
endif

mvn:   ; $(MVN) $(A)            
test:  ; $(MVN) -B verify
lint:  ; $(MVN) -B spotless:check

up:
	COMPOSE_PROFILES=core $(COMPOSE) up -d
down:
	COMPOSE_PROFILES=core $(COMPOSE) down

nuke:
	COMPOSE_PROFILES=core $(COMPOSE) down -v

ps:
	COMPOSE_PROFILES=core $(COMPOSE) ps