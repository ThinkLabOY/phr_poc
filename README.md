# Phr application on Docker

### Activate Docker in Windows CMD 
@FOR /f "tokens=*" %i IN ('docker-machine env default') DO @%i
### Docker image is upploaded to https://hub.docker.com/r/thinklaboy/phr/
### Image can be downloaded with command:  
docker pull thinklaboy/phr:latest

### Run image on container:  
docker run -p 8080:8080 thinklaboy/phr:latest

### Start project with local build:  
docker run -v <path_to_project>/phr_poc/target/phr-0.0.1.jar:/app.jar -p 8080:8080 --rm thinklaboy/phr:latest 

### on Windows:  
docker run -v //c/Users/<path_to_project>/phr_poc/target/phr-0.0.1.jar:/app.jar -p 8080:8080 --rm thinklaboy/phr:latest 

### To verify that Docker is working open address:
http://192.168.99.100:8080/


## Commands:

### Show running containers
docker ps
### Show all containers
docker ps -a	
### Access container bash
docker exec -it <container id> bash
### Start/stop container  
docker start/stop <container id>	
### View running presesses on docker
ps -eaf
### Delete ... containers on windows
docker ps -a | grep 'weeks ago' | awk '{print $1}' | xargs --no-run-if-empty docker rm
### Delete all containers
docker rm $(docker ps -a -q)
### Delete all images
docker rmi $(docker images -q)
### Show logs
docker logs <container_id> -f
