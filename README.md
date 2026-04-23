# Subscription Tracker

<p align="center">
  <b>Backend application for tracking subscriptions and analyzing recurring expenses.</b><br/>
  Built with Java, Spring Boot, PostgreSQL, and a layered architecture.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-000?logo=openjdk" alt="Java 21"/>
  <img src="https://img.shields.io/badge/Spring_Boot-6DB33F?logo=springboot&logoColor=white" alt="Spring Boot"/>
  <img src="https://img.shields.io/badge/PostgreSQL-17-4169E1?logo=postgresql&logoColor=white" alt="PostgreSQL"/>
  <img src="https://img.shields.io/badge/Maven-Build-C71A36?logo=apachemaven&logoColor=white" alt="Maven"/>
  <img src="https://img.shields.io/badge/Status-Pet_Project-blueviolet" alt="Pet Project"/>
</p>



## Overview

**Subscription Tracker** is a REST API for managing user subscriptions and calculating recurring expenses.

It allows clients to:

 - manage subscription lifecycle (create, update, deactivate)
 - track upcoming payments based on billing periods
 - calculate aggregated monthly costs

The project focuses on backend design, including domain modeling, business logic for recurring payments, and clean architecture.



## Table of contents

- [Why this project](#why-this-project)
- [Key idea](#key-idea)
- [Features](#features)
- [Tech stack](#tech-stack)
- [Architecture](#architecture)
- [Domain model](#domain-model)
- [API](#api)
- [Example request](#example-request)
- [Validation and error handling](#validation-and-error-handling)
- [Run locally](#run-locally)
- [Project structure](#project-structure)
- [Future improvements](#future-improvements)



## Why this project

Designed as a backend-focused project with a realistic domain model and recurring payments logic.



## Key idea

> **`nextPaymentDate` is not stored in the database.**
>  
> It is calculated dynamically from:
> - `startDate`
> - `billingPeriod`
> - current date

### Why?

A static `nextPaymentDate` becomes outdated unless the system updates it after every payment.

Instead, this project calculates the next payment date on the fly, which makes the model:

- cleaner
- easier to maintain
- more realistic for a subscription tracker MVP



## Features

 - manage subscription lifecycle (CRUD operations)
 - track upcoming payments for a given time window
 - calculate monthly recurring expenses
 - filter subscriptions (active / upcoming)
 - validate incoming request data
 - return structured JSON error responses



## Tech stack

- **Java 21**
- **Spring Boot**
- **Spring Web**
- **Spring Data JPA**
- **PostgreSQL**
- **Lombok**
- **Jakarta Validation**
- **Maven**



## Architecture

The project follows a classic layered architecture.

```text
Client -> Controller -> Service -> Repository -> Database
```

### Layers

- Controller — HTTP layer
- Service — business logic
- Repository — database access

## Domain model

The main entity is `Subscription`, which contains:

- name
- price
- currency
- billing period
- start date
- category
- status

`nextPaymentDate` is calculated dynamically.



## API

### Create subscription
POST /subscriptions
### Get all
GET /subscriptions
### Get by id
GET /subscriptions/{id}
### Active
GET /subscriptions/active
### Upcoming
GET /subscriptions/upcoming?days=7
### Monthly cost
GET /subscriptions/summary/monthly-cost
### Update status
PATCH /subscriptions/{id}/status?status=CANCELED
### Delete
DELETE /subscriptions/{id}


## Example request
```text
POST /subscriptions

{
  "name": "Spotify",
  "price": 4050,
  "currency": "RUB",
  "billingPeriod": "YEARLY",
  "startDate": "2026-01-10",
  "category": "MUSIC",
  "status": "ACTIVE"
}
```


## Example response
```text
{
  "id": 1,
  "name": "Spotify",
  "price": 4050,
  "currency": "RUB",
  "billingPeriod": "YEARLY",
  "startDate": "2026-01-10",
  "nextPaymentDate": "2027-01-10",
  "category": "MUSIC",
  "status": "ACTIVE",
  "createdAt": "2026-03-22T21:38:10"
}
```

## Validation and error handling

Example validation error:

```json
{
  "error": "Validation failed",
  "details": [
    "Name must not be blank",
    "Price must be greater than 0"
  ]
}
```

Example not found error:

```json
{
  "error": "Subscription not found with id: 999",
  "details": null
}
```

---

## Run locally

### 1. Clone repository

```bash
git clone https://github.com/YOUR_USERNAME/subscription-tracker.git
cd subscription-tracker
```

### 2. Create database

```sql
CREATE DATABASE subscription_tracker;
```

### 3. Configure `application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/subscription_tracker
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
server.port=8080
```

### 4. Run application

```bash
mvn spring-boot:run
```


## Project structure

```text
controller/
service/
repository/
entity/
dto/
exception/
```


## Future improvements

- Swagger / OpenAPI
- Docker
- Authentication
- Filtering
- Tests
- Frontend



## Author

https://github.com/little3snake
