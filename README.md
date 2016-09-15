Phr application on Docker

Docker image is upploaded to https://hub.docker.com/r/thinklaboy/phr/
It can be downloaded with command:  
docker pull thinklaboy/phr:latest

Run image on container:  
docker run -p 8080:8080 thinklaboy/phr:latest

Start project with local build:  
docker run -v <path_to_project>/phr_poc:/opt/repo -p 8080:8080 --rm thinklaboy/phr:latest java -jar /opt/repo/target/phr-0.0.1.jar
