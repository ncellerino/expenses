# expenses

1. git clone https://github.com/ncellerino/expenses.git

## Back-end service

It is as service implemented in Java with Spring Boot and MongoDB. It is secured using using JWT authentication and salted password hashing.

To run the service:

1. cd back-end

2. ./gradlew build

3. java -jar build/libs/expenses-0.1.0.jar --spring.profiles.active=prod


Next features to be added:

1. Etag filter
2. Guava cache 

