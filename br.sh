#!/bin/bash
clear
mvn clean package
java -jar target/bookmarker-0.0.1-SNAPSHOT.jar
