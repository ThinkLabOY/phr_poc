#!/usr/bin/env bash


#docker run -v /Users/rp/Documents/projects/affecto/phr_poc/target/phr-0.0.1.jar:/app.jar -p 8080:8080 --rm thinklaboy/phr:latest

path=$(PWD)

echo "Path: " $path;

docker run -v "$path/target/phr-0.0.1.jar:/app.jar" -p 8080:8080 --rm thinklaboy/phr:latest