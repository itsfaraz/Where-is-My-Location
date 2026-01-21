# Minimal Implementation of Fused location with caching

### Some technical intricacies to be observed in the repository

## General Points
* The application is pointing to the local host created using simple node backend linked
* Refer the link for mock node server :: 
* An attached debug build application for testing

#### Android Related

## Common Pattern Used
* MVVM architecture + Clean Arachitecture
* Room Database for caching
* Background Service
* Manual Dependency Injection With Service Locator
* Jetpack Compose
* Retrofit, Okhttp, & Chucker for networking and observation


## Database Schema for offline caching
* Simple Location log table with following fields (employeeId, latitude, longitude, timestamp, speed)

## Android Guide

* Provide location permission manually from app or developer setting
* Enter local IP Address connected with wifi or same network of backend and mobile ex : 192.168.*.*
* Toggle GPS always on for fused location tracking
* Start Tracking and Stop Tracking starts and ends the fused location service
* During recent or app minimize state :: application tracks location in background with offline caching capability
* Once internet connection active it uploads all the log
* Toggle internet or wifi toggle to test sychronization feature


## Node Guide

* Clone the mock server
* Install node modules with npm install
* In terminal command :: node index.js to start the server
* Each location post from android can be shown in logs
* Ensure to start server on port 3000
* Once server started ensure ip address is same in mobile
* On mobile side port no need to enter in text field (starting static port : 3000)



### reach faraz.ar.sheikh@gmail.com