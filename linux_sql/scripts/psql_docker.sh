#!/bin/bash
# script usage
#./scripts/psql_docker.sh start|stop [db_password]

if (($# > 2))
then
    echo Too many arguments!
    exit 1
fi

case $1 in
    start)
          #check if password is provided
          if [ $# -ne 2 ]
          then
              echo Please provide password for databse.
              exit 1
          fi

          #start docker if docker server is not running
          systemctl status docker || systemctl start docker

          #get latest postgres image
          docker pull postgres

          #check if container is running, if running exit
          if [ `docker ps -f name=jrvs-psql | wc -l` -eq 2 ]
          then
              echo jrvs-psql container is running.
              exit 0
          fi

          #check if the volume is created
          if [ -z `docker volume ls | egrep "pgdata" |awk '{print $2}'` ]
          then
              docker volume create pgdata
          fi

          #set password for default user `postgres`
          export PGPASSWORD='password'

          #check if the container `jrvs-psql` is created or not
          if [ `sudo docker container ls -a -f name=jrvs-psql | wc -l` -ne 2 ]
          then
              docker run --name jrvs-psql -e POSTGRES_PASSWORD=$PGPASSWORD -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres
          fi

          #run psql
          docker container start jrvs-psql
          echo jrvs-psql is now running!

          ;;
    stop)
          #check if there is extra argument
          if [ $# -ne 1 ]
          then
              echo Too many arguments!
              exit 1
          fi

          #check if container is running, if running exit
          if [ `docker ps -f name=jrvs-psql | wc -l` -eq 1 ]
          then
              echo jrvs-psql is already stopped
              exit 1
          fi

          #stop psql
          docker container stop jrvs-psql
          echo jrvs-psql is stopped

          ;;
    *)
          echo Wrong command
          exit 1

esac

exit 0
