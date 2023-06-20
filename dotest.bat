docker-compose up -d
call mvn test -Dsurefire.suiteXmlFiles=src/test/resources/all_tests_suite.xml
docker-compose down
docker rmi dz-13_postgres