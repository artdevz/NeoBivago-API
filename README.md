# NeoBivago-API

**Description:** NeoBivago It's a refactoring of my final work from the OOP course.

**Author:** Arthur Dantas

## Summary:
1. [Objectives](#Objectives)
2. [Installing](#Installing)
3. [Executing](#Executing)
4. [Business Rules](#Business-Rules)

---

## Objectives:
**Main Objective:** Create a Hotel Host and Room Reservations System with a friendly interface for the users with Java for Back-End and MySQL for Database.
- **User Registration:** All guests over 18 yo can register as users.
- **Room Management:** Hotel administrators can add, edit and remove rooms.
- **Reservation System:** Users can search for rooms by city, capacity, category and price.

---

## Installing:

### Cloning the application repository:
```git clone https://github.com/your-user/NeoBivago-API.git cd NeoBivago-API```

### Required Dependencies:
You need **Java 17**, **Maven 3** and **MySQL 8x** previously installed.

---

## Executing:
```./mvnw spring-boot:run```

API path needs a prefix: [server:port/api/**]

---

## Business Rules:
- **Reservations:** The System checks room availability in real time.
- **Hotel Registers:** Hotel administrators can manage the hotel and rooms.
- **Cancellation of reservations:** Users can only cancel reservations up to 24 hours before Check-In.
