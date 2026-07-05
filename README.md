## Docker Container bauen (quick)

- WSL hochgefahren?
- Docker Desktop hochgefahren?
- andere App auf 8080 runtergefahren?
- `dev` Profile vom Browser offen?

```bash
./gradlew clean bootJar && docker build -t academy-app:1.0 .
```

### System hoch- und runterfahren
Im rootDir des Projekts
```bash
docker compose up -d
docker compose down
lazydocker
```

## Schnell einen Studentin anlegen

### Login

```bash
TOKEN=$(curl -s -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"lecturer","password":"academy"}' \
  | jq -r '.token')
```

### Alle vorhandenen Studenten einsehen

```bash
curl \                               
  -H "Authorization: Bearer $TOKEN" \
  "http://localhost:8081/students?page=0&size=10&sort=lastName" \
  | jq
```

### Student anlegen

```bash
curl -X POST http://localhost:8081/students \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
        "firstName":"Günter",
        "lastName":"Wegner",
        "email":"wegner.wegner@example.com"
      }'
```
---

## Cloud-Sationen besuchen

| Station    | Adresse                            |
|------------|------------------------------------|
| pgadmin    | http://localhost:8080/login?next=/ |
| minio      | http://localhost:9001/login        |
| prometheus | http://localhost:9090/query        |
| grafana    | http://localhost:3000/login        |
| mail       | http://localhost:8025/             |