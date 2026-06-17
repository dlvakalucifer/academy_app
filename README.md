## Docker Container bauen (quick)

./gradlew clean bootJar && docker build -t academy-app:1.0 .

## Schnell einen Studentin anlegen

### Login

```bash
TOKEN=$(curl -s -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"lecturer","password":"academy"}' \
  | jq -r '.token')
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
