default: runDbContainer runServer

.PHONY: jar
jar:
	./mvnw clean package

.PHONY: runDbContainer
runDbContainer:
	docker-compose up -d db

.PHONY: stopDbContainer
stopDbContainer:
	docker-compose down db

.PHONY: removeDbContainer
removeDbContainer:
	docker-compose rm db

.PHONY: runServer
runServer:
	./mvnw clean spring-boot:run -Dspring-boot.run.profiles=dev

.PHONY: buildServerImage
buildServerImage:
	./mvnw clean spring-boot:build-image -Dspring-boot.build-image.imageName=hu553in/trello-clone

.PHONY: runServerContainer
runServerContainer:
	docker-compose up server

.PHONY: docker
docker: buildServerImage runDbContainer runServerContainer
