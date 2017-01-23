# expenses

1. git clone https://github.com/ncellerino/expenses.git

## Fron-end
Implemented with AngularJS 1.5 and LESS

1. JWT token is stored in Web Session Storage.
2. XSS is avoided using ng-bind instead of double curly brace.

To run the client:

1. install npm
2. npm - npm install -g bower
3. npm install gulp
4. npm install
5. bower install
6. gulp serve

Next features to be added:

1. Etag filter
2. Session timeout manage



## Back-end service

Service implemented in Java with Spring Boot and MongoDB. It is secured using using JWT authentication and salted password hashing.

To run the service:

1. cd back-end

2. ./gradlew build

3. java -jar build/libs/expenses-0.1.0.jar --spring.profiles.active=prod

Features:

1. Docker image created


Next features to be added:

1. Etag filter
2. Guava cache 

