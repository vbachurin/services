#!/bin/bash

: "${GF_SECURITY_ADMIN_PASSWORD:=admin}"

echo 'Starting Grafana...'
#/run.sh "$@" &
/usr/bin/supervisord &
ConfigureGrafana() {
  curl -s "http://admin:${GF_SECURITY_ADMIN_PASSWORD}@localhost:3000/api/orgs/1" \
    -X PUT \
    -H 'Content-Type: application/json;charset=UTF-8' \
    --data-binary \
    '{"name":"Example App"}'
  curl "http://admin:${GF_SECURITY_ADMIN_PASSWORD}@localhost:3000/api/datasources" \
    -X POST \
    -H 'Content-Type: application/json;charset=UTF-8' \
    --data-binary \
    '{"name":"Pulse-Graphite","type":"graphite","url":"http://localhost:8000","access":"proxy","isDefault":true}'
}
until ConfigureGrafana; do
  echo 'Configuring Grafana...'
  sleep 1
done
echo 'Done!'
wait
