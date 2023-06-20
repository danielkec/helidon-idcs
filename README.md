# Helidon IDCS example

After you have successfully prepared user in group `secret_role` with 
access to your application in IDCS, you need to edit `application.yaml`
for both your services and fill in client id, secret and IDCS url.

Build all
```shell
mvn clean package
```

Run Helidon service
```shell
java -jar ./helidon-service/target/helidon-idcs-service.jar
```

Run WLS service mock
```shell
java -jar ./wls-mock-service/target/mock-wls-service.jar
```

Visit http://localhost:8080/helidon

After successful login you should see:
```json
{
  "user": "john@doe.com",
  "is_secret_role": true,
  "wls-response": {
    "user": {
      "name": "john@doe.com"
    }
  }
}
```