## Overview

This is a demo application that demonstrates the use of **JMS Messaging** with **Server Sent Events** to deliver **feeds / notifications / push messages** to clients.

### Main Technologies & Frameworks
- Spring Boot
- ActiveMQ Artemis
- Thymeleaf
- H2 database

### How to build ActiveMQ Artemis Docker image?

1. Go to https://github.com/apache/activemq-artemis and download your preferred release.
2. Copy the folder **artemis-docker** to a folder - on this example, I'm using [this folder](src/main/activemq-sse-demo-docker-local) for starting.
3. The contents of your folder should be like this:

| ...                        |
|----------------------------|
| Dockerfile-adoptopenjdk-11 |
| Dockerfile-centos          |
| Dockerfile-debian          |
| docker-run.sh              |
| prepare-docker.sh          |
| readme.md                  |
      
4. There's a **readme.md** file you can check it out for more clearance.
5. commands used on this example:
```shell
#1
./prepare-docker.sh --from-release --artemis-version 2.22.0
#2
cd _TMP_/artemis/2.22.0
#3
docker build -f ./docker/Dockerfile-adoptopenjdk-11 -t apache/artemis-adoptopenjdk-11:2.22.0 .
```
6. Now, you can start utilizing thi [docker-compose.yml](src/main/activemq-sse-demo-docker-local/docker-compose.yml) file.

## Authors
[![Linkedin](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white&label=Muhammad%20Ali)](https://linkedin.com/in/zatribune)

