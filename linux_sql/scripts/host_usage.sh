#! /bin/bash

#take command line argument and setup variable
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

#parse data and setup variable
memory_free=$(vmstat --unit M | tail -1 | awk '{print $4}' | xargs)
cpu_idel=$(vmstat -t | tail -1 | awk '{print $15}' | xargs)
cpu_kernel=$(vmstat --unit M | tail -1 | awk '{print $14}' | xargs)
disk_io=$(vmstat -d | tail -1 | awk '{print $10}' | xargs)
disk_available=$(df -BM | head -2 | tail -1 | awk '{print $4}' | sed s/.$// | xargs)
timestamp=$(date --rfc-3339=seconds | awk -F+ '{print $1}' | xargs)

#construct the insert statement
insert_statement="INSERT INTO host_usage ("timestamp", memory_free, cpu_idel, cpu_kernel, disk_io, disk_available) VALUES ('${timestamp}', ${memory_free}, ${cpu_idel}, ${cpu_kernel}, ${disk_io}, ${disk_available})";
echo $insert_statement

#execute insert statement
export PGPASSWORD=$psql_password
psql -h $psql_host -p $psql_port -U $psql_user -d $db_name -c "$insert_statement"
