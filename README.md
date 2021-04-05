# ECSE 321 Project Group 11

## Project Overview
We are developing a website and an Android app to facilitate the artwork selling process of an artgallery. In this system,
artists can post advertisements to put their artworks up for sale. They can also apply to buy a piece of artwork on an advertisement
posted by an artist or by the gallery itself. The system is implemented in teams of 5. The implementation of this system
requires requirements engineering, software development, validation of the system through unit testing and automation of the software
delivery process.

## Running the Project
The backend is hosted at https://artgallery-11.herokuapp.com/. The backend can be run locally by cloning this repository, and running the artgallery-Backend repository as a Spring Boot App. Using a terminal/command line from the root of the project, one can instead use "gradle run" to run the project locally. In that case, the application will be hosted at http://localhost:8080/. To use the application, see the API documentation [here](waiting to be written)

The frontend is hosted at https://artgallery-frontend-1.herokuapp.com/.

### About Us
Name | Major | Year
-----| ----- | -----
Kua Chen | CE | U2 
Lide Cui | SE | U2 
Yutian Fu | SE | U3 
Bokun Zhao | CE | U2 
Chenxuan Zhou| CS | U2 

## Deliverable 1
[Deliverable 1 Report](https://github.com/McGill-ECSE321-Fall2020/project-group-11/wiki/Deliverable-1-Report)
Name | Hour | Responsibility
-----| ----- | -----
Kua Chen | 22 |  Requirements, Login use case, Domain Model innovation, Database mapping annotation, Persistenace Reference Test
Lide Cui | 25 |  Requirements, Payment use case, Domain Model design-build-revision, Persistenace Reference Test
Yutian Fu | 23 | Requirements, Registeration use case, Domain Model desgin, Database mapping annotation, Persistence Test 
Bokun Zhao | 8 |  Requirements, Main/Administration/Shopping(updated) use cases and workflows, Domain Model Decisions (Promotion class/Manager class, relations), design decision documentation, project wiki
Chenxuan Zhou | 21 | Requirements, Product use case, Project setup, Domain Model decisions, Persistance Reference Test, Meeting records

## Deliverable 2
[Deliverable 2 Report](https://github.com/McGill-ECSE321-Fall2020/project-group-11/wiki/Deliverable-2-Report)
Name | Hour | Responsibility
-----| ----- | -----
Kua Chen | 31 |  PromotionDto, Product service, Product service unit test, Product controller, Integeration test
Lide Cui | 30 |  ProductDto, Manager service, Manager service unit test, Manager controller, Integeration test, QA Plan
Yutian Fu | 27 | CustomerDto, Promotion Service, Promotion service unit test, Promotion Controller, Integration Test, QA Plan
Bokun Zhao | 30 | Account/Manager/SellerDto, Seller service, Order Service, Order service unit test, Customer service unit test, Order Controller, Customer Controller, Integration test, QA Plan
Chenxuan Zhou | 25 | SellerDto, Travis deploy Heroku; Seller service, Seller service unit test, Seller controller, Seller integration test

## Deliverable 3
[Deliverable 3 Report](https://github.com/McGill-ECSE321-Fall2020/project-group-11/wiki/Deliverable-3-Report)
Name | Hour | Responsibility
-----| ----- | -----
Kua Chen | 21 | Components and coresponding javascripts and user guide: AllProducts, AllSellers, SellerDisplay, SellerInfo, SellerProfile, SellerProductInfo 
Lide Cui | 21 |  Components and coresponding javascripts and user guide: Make order, home page, manager profile; Arvhitecture model
Yutian Fu | 20 | Heroku setup; Vue setup; All promotions.vue, MakePromotion.vue, makePromotion.js, promotion.js,corrsponding fronted user guide
Bokun Zhao | 20 | CustomerProfile.vue, CustomerInfo.vue, CustomerReception.vue, customerprofile.js, customerinfo.js, customerreception.js
Chenxuan Zhou | 25 | Vuex setup; Login, Logout, Signup, ProductCreation; corresponding frontend user guide  

## Deliverable 4
[Deliverable 4 Report](https://github.com/McGill-ECSE321-Fall2020/project-group-11/wiki/Deliverable-4-Report)
Name | Hour | Responsibility
-----| ----- | -----
Kua Chen | 20 | ViewProduct, ProductAdapter, activity_dashboard, activity_product, my_product, Deliverable Report 
Lide Cui | 15 |  MakeOrder.java, makeOrder.xml, user guide
Yutian Fu | 14 | viewPromotion, PromotionAdapter,activity_promotion.xml,single_promotion.xml, part of Android setup
Bokun Zhao | 15 | customerProfile.java, activity_customerprofile.xml, json return object fix-up
Chenxuan Zhou | 15 | Global, login() in MainActivity, logout() in Dashboard
