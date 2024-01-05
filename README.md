# Consult Hotel
### Introduction
In this project we applied the microservices architecture with Spring Boot, it is a blog project where users can register, users create a hotel with their description and name, other users can give rating and comment about their experience. Youhave two roles, admin and user, where admin will have access to actions such as removing hotel posts or users who non-compliance the Terms and Conditions.

### 1. Architecture
The microservices architecture of this project can be better appreciated in the following image.
In this project several Spring modules were handled which are the following

- Spring Boot
- Spring Data
- Spring Security
- Spring Cloud
- Spring RESTful APIs

![Texto alternativo](https://github.com/Daniel-LA0303/mcsv_app1_hotels/blob/main/assets/1_architecture.png)

### 2. Services APIs
For the creation of the services we occupy APIs with the layers offered by Spring Boot. 
With this we will create the APIs of three services that are: Users, Hotel and Comment or Cal, these services will have CRUD functions in different databases as shown by the architecture

![Texto alternativo](https://github.com/Daniel-LA0303/mcsv_app1_hotels/blob/main/assets/2_model.png)

### 3. Eureka Server
Eureka is a server for the registration and localization of microservices, load balancing and fault tolerance, The function of Eureka is to record the different instances of existing microservices, their location, metadata status etc.
In our case we will start registering these three services.

![Texto alternativo](https://github.com/Daniel-LA0303/mcsv_app1_hotels/blob/main/assets/3_server.png)

### 4. Communication
Communication between microservices is important as there will be cases in which we must do so to either send or receive information, in our case we will communicate in two ways, to communicate to the microservice of
Comment or Cal sera with RestTemplete while communicating with the microservice Hotel with OpenFeing which is a dependency.

![Texto alternativo](https://github.com/Daniel-LA0303/mcsv_app1_hotels/blob/main/assets/4_comunication.png)

### 5. Implementing a Load Balancer
We will apply a load balancer with the Comment microservice, this means that we will have more than one instance of this micorservicio, so the load balancer will be responsible for managing the requests that come from the Users microservice.

![Texto alternativo](https://github.com/Daniel-LA0303/mcsv_app1_hotels/blob/main/assets/5_load_balancer.png)

### 6. Implement API Gateway
It is the traffic manager that interacts with data or the actual backend service and applies policies, auth and general access control for API calls to protect valuable data. An API Gateway is the way you control access to your backend systems and services and was designed to optimize communication between external customers and backend services.

I created a new API Gateway service so that it can manage services like User, Hotel and Cal or Comment.

![Texto alternativo](https://github.com/Daniel-LA0303/mcsv_app1_hotels/blob/main/assets/6_api_gateway.png)

### 7. Configuration Server
After I create a new service specifically for the configurations, note that each project has a lot of code that looks quite similar so I decided to upload it to a repository, after this servcio gets the Github repository configurations, After a delay that runs an isntance of selected services, these will go to the configuration service to have their configuration respectively.

![Texto alternativo](https://github.com/Daniel-LA0303/mcsv_app1_hotels/blob/main/assets/7_config_server.png)

### 8. Implement Circuit Breaker
It is a common problem that in distributed architectures some components fail which implies that certain operations stop working and it is advisable to take into account that we would do in case certain microservices fall

In this case use it for the User microservice, this in order to know if something has failed in the Comment microservice, in case if there is, I put a controller that runs when communication fails applying actuator and resilience4j configurations.

![Texto alternativo](https://github.com/Daniel-LA0303/mcsv_app1_hotels/blob/main/assets/8_circuit_breaker.png)

### 9. Implement Auth Service
I apply an authentication microservice for users, this means that a user can be created and then logged in so that certain things can be done freely, for example, request all hotels and all their comments, so a user who is not logged in will not be able to create a hotel or give their opinion about one.
All this with the help of Spring Security and JWT, this was also applied with a middleware filter where users have two roles Admin and User, it is Admin that has the highest priority since the middleware filters the URL and role, if you pass the filters then you can do the action.

![Texto alternativo](https://github.com/Daniel-LA0303/mcsv_app1_hotels/blob/main/assets/9_auth_service.png)




