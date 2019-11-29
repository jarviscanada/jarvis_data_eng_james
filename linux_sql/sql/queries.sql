--Query 1
--Group hosts by CPU number and sort by their memory size in descending order
--(within each cpu_number group)
select
  info.cpu_number,
  host.id AS host_id,
  info.total_mem
from
  host_info AS info
ORDER BY 
  info.total_mem DESC;

--Query 2
-- Average used memory in percentage over 5 mins interval for each host.
--(used memory = total memory - free memory).
