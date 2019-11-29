#! /bin/bash

#take command line argument and setup variable
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

lscpu_out=`lscpu`

#helper function to eliminate duplicate code
parse_helper(){
    echo $(echo "$lscpu_out"  | egrep "$1" | awk -F':' '{print $2}' | xargs)
}
#parse data and setup variable
hostname=$(hostname -f)
cpu_number="$(parse_helper "^CPU\(s\):")"
cpu_architecture="$(parse_helper "Architecture")"
cpu_model="$(parse_helper "Model name:")"
cpu_mhz="$(parse_helper "CPU MHz:")"
L2_cache=$(echo "$lscpu_out"  | egrep "L2 cache:" | awk '{print $3}' | sed s'/K//' | xargs)
total_mem=$(cat /proc/meminfo | egrep "^MemTotal:" | awk '{print $2}')
timestamp=$(date --rfc-3339=seconds | awk -F+ '{print $1}' | xargs)

#constuct insert statement
insert_statement="INSERT INTO host_info (hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, l2_cache, total_mem, "timestamp") VALUES ('${hostname}', ${cpu_number}, '${cpu_architecture}', '${cpu_model}', ${cpu_mhz}, ${L2_cache}, ${total_mem}, '${timestamp}');"
echo $insert_statement

#execute insert statement
export PGPASSWORD=$psql_password
psql -h $psql_host -p $psql_port -U $psql_user -d $db_name -c "$insert_statement"
