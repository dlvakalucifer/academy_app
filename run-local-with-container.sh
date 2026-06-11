#!/usr/bin/env bash

cd ~/projects/academy_app

docker compose up -d

./gradlew bootRun