# customer-reward-system

Customer Reward System

In order to run this application, please follow the below steps.

- Step 1: Go to the terminal, point the project directory and execute ```mvn clean install```
- Step 2: Enter ```mvn spring-boot:run``` command to run this application
- Step 3: If all works fine, application will be running at ```https://localhost:8080"``` and visit ```http://localhost:8080/health/```

This is the spring boot application and it's provides following endpoints. 

- /customers - Get all customers

![Customers](https://raw.githubusercontent.com/idineshkrishnan/customer-reward-system/main/src/main/resources/screens/customers_endpoint.png)

- /customers/{customerId} - Get customer by ID

![Customer By ID](https://raw.githubusercontent.com/idineshkrishnan/customer-reward-system/main/src/main/resources/screens/customerById_endpoint.png)

- /customers/{customerId}/purchases - Get all purchases by customer

![Purchases By Customer](https://raw.githubusercontent.com/idineshkrishnan/customer-reward-system/main/src/main/resources/screens/allPurchaseByCustomerId_endpoint.png)

- /customers/{customerId}/rewards - Get reward point by customer

![Rewards By Customer](https://raw.githubusercontent.com/idineshkrishnan/customer-reward-system/main/src/main/resources/screens/rewardsByCustomerId_endpoint.png)

