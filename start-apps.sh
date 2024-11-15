#!/usr/bin/env bash

source scripts/my-functions.sh

echo
echo "Starting bitcoin-api..."

docker run -d --rm --name bitcoin-api -p 9081:8080 \
  -e MYSQL_HOST=mysql -e KAFKA_HOST=kafka -e KAFKA_PORT=9092 \
  --network=springboot-kafka-websocket_default \
  --health-cmd='[ -z "$(echo "" > /dev/tcp/localhost/9081)" ] || exit 1' \
  ivanfranchin/bitcoin-api:1.0.0

echo
echo "Starting bitcoin-client..."

docker run -d --rm --name bitcoin-client -p 9082:8080 \
  -e KAFKA_HOST=kafka -e KAFKA_PORT=9092 \
  --network=springboot-kafka-websocket_default \
  --health-cmd='[ -z "$(echo "" > /dev/tcp/localhost/9082)" ] || exit 1' \
  ivanfranchin/bitcoin-client:1.0.0

echo
wait_for_container_log "bitcoin-client" "Started"

echo
wait_for_container_log "bitcoin-api" "Started"

printf "\n"
printf "%15s | %37s |\n" "Application" "URL"
printf "%15s + %37s |\n" "--------------" "-------------------------------------"
printf "%15s | %37s |\n" "bitcoin-api" "http://localhost:9081/swagger-ui.html"
printf "%15s | %37s |\n" "bitcoin-client" "http://localhost:9082"
printf "\n"
