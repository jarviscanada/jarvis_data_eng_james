#!/bin/bash

if (($# > 2));
then
  echo Too many arguments!
  exit 1
fi

if [ "$1" = "start" ];
then
    systemctl status docker || systemctl start docker
    docker run --rm --name jrvs-psql -e POSTGRES_PASSWORD=$2 -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres
    #start sql
    psql -h localhost -U postgres -W
fi

if [ "$1" = "Stop" ];
then
    systemctl stop docker
fi

exit 0
