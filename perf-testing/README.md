# Performance testing

How to run:

    cd service
    docker-compose up --force-recreate -d
    cd gatling-load-1
    sbt gatling:testOnly

Dashboard with metrics vizualization:

    http://<DOCKER_HOST>/dashboard/file/dashboard.json
    
Note:
Currently `src/test/resources/gatling.conf` contains the hardcoded Graphite host address.
You need to check if the hardcoded value fits your environment setup, in order to get Gatling metrics collected.

Docker stack is inspired by and mosntly copies this article:
http://mintbeans.com/jvm-monitoring-docker/

The article can be used for the reference.
