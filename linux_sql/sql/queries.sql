--Query 1
--Group hosts by CPU number and sort by their memory size in descending order
--(within each cpu_number group)
SELECT
  info.cpu_number,
  host.id AS host_id,
  info.total_mem
FROM
  host_info AS info
ORDER BY
  info.total_mem DESC;

--Query 2
-- Average used memory in percentage over 5 mins interval for each host.
--(used memory = total memory - free memory).
SELECT
  usage.host_id,
  info.hostname AS host_name,
  info.total_mem AS total_memory,
  AVG((info.total_mem - usage.memory_free) / info.total_mem * 100)::INT AS used_memory_percentage
FROM
  host_usage AS usage
  JOIN
  host_info AS info
  ON usage.host_id=info.id
GROUP BY
  usage.host_id,
  info.hostname,
  info.total_mem,
  DATE_TRUNC('hour', usage."timestamp") + interval '5 MINUTE' * (DATE_PART('MINUTE',usage."timestamp")/5.0)::int
ORDER BY
  usage.host_id;
