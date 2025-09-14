# REST API Demo Project

A small REST API written in Java with Micronaut, demonstrating HoloDB's automatic mocking on top of plain JPA entities.

The `demo` Micronaut environment configures:

- JDBC URL: `jdbc:holodb:jpa:///demo`
- Driver: `hu.webarticum.holodb.jpa.JpaMetamodelDriver`

This setup automatically configures and launches an embedded HoloDB instance, based on the JPA entities.
So in this demo mode, the app runs **entirely on mock data derived from your JPA entities**.
To fine-tune this, you can find some additional annotations on the entities, such as `@HoloTable`, `@HoloColumn`, etc.

> [!IMPORTANT]
> Currently, the little listener logic implemented in the `HoloInit` class is necessary for passing the JPA `Metamodel` to the HoloDB initializer.
>
> The need for this explicit initialization is planned to be eliminated in future versions.

To run the application in demo mode, simply use the `demo` Gradle task:

```
./gradlew demo
```

The available endpoints are:

- `http://localhost:8080/categories/**`
- `http://localhost:8080/authors/**`
- `http://localhost:8080/posts/**`
- `http://localhost:8080/posts/{id}/comments/**`

For easier testing, you can use the Swagger web interface here:

```
http://localhost:8080/swagger-ui
```
