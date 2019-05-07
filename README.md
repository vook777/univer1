..
docker run --name sokolov-postgres\
-e POSTGRES_DB=university \
-e POSTGRES_USER=postgres \
-e POSTGRES_PASSWORD=123tester123 \
-P 5432:5432 \
-d postgres

URL = jdbc:postgresql://localhost:5432/university
USER = postgres
PASSWORD = 123tester123
