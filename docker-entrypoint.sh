#!/bin/bash

# Horrendous workaround
# As Postgresql container restarts twice on startup, it's difficult to figure out when it's ready
sleep 10

cd /opt/app

# Create DB
psql -U postgres -h db -c "CREATE DATABASE bookmarker"

# Call Migrate
flyway -url=jdbc:postgresql://db:5432/bookmarker -user=postgres -password=postgres -locations=filesystem:sql migrate

# Start app
java -Dspring.profiles.active=test -jar bookmarker-0.0.1-SNAPSHOT.jar --spring.datasource.url=jdbc:postgresql://db:5432/bookmarker