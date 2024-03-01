Following tutorials from [Craig Walls](https://github.com/habuma) and huge credit to him for his original work.

```shell
./gradlew spring-ai:bootRun --args='--spring.profiles.active=local'
```

```shell
curl localhost:8080/sql \
  -H"Content-type: application/json" \
  -d'{"question":"How many books has Craig Walls written?"}'
```