FROM postgres
COPY sql_scripts/ /docker-entrypoint-initdb.d/
EXPOSE 4567