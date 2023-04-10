# Camp Reservation API

This is an API for managing campsite reservations. It includes CRUD functionality for campsites, ratings, reservations, and users. Additionally, it includes some special features in the Rating and Reservation services.

## Installation

1. Clone this repository.
2. Set up a PostgreSQL database and update the application.properties file with your database details.
3. Import the camp_reservation.sql file into your database to set up the necessary tables and data. You can find this file in the sql directory of this repository. Use the following command to import the file:

```bash
psql -U <your_username> -d <your_database_name> -a -f camp_reservation.sql
```
Note: make sure to replace <your_username> and <your_database_name> with your actual PostgreSQL username and database name.

## Endpoints

### Camps

    GET /api/camps - Get all camps.
    GET /api/camps/{id} - Get a camp by ID.
    POST /api/camps - Create a new camp.
    PUT /api/camps/{id} - Update an existing camp by ID.
    DELETE /api/camps/{id} - Delete a camp by ID.

### Ratings

    GET /api/ratings - Get all ratings.
    GET /api/ratings/{id} - Get a rating by ID.
    POST /api/ratings - Create a new rating.
    PUT /api/ratings/{id} - Update an existing rating by ID.
    DELETE /api/ratings/{id} - Delete a rating by ID.

### Reservations

    GET /api/reservations - Get all reservations.
    GET /api/reservations/{id} - Get a reservation by ID.
    POST /api/reservations - Create a new reservation.
    PUT /api/reservations/{id} - Update an existing reservation by ID.
    DELETE /api/reservations/{id} - Delete a reservation by ID.

### Users

    GET /api/users - Get all users.
    GET /api/users/{id} - Get a user by ID.
    POST /api/users - Create a new user.
    PUT /api/users/{id} - Update an existing user by ID.
    DELETE /api/users/{id} - Delete a user by ID.

## Special Features

### Rating Service

The Rating service has the following special features:

   1. A user can only rate a campsite if they have made a reservation for that campsite.
   2. A user can only rate a campsite once per reservation.

### Reservation Service

The Reservation service has the following special features:

   1. The system ensures that there are enough spots available for a reservation.
   2. The system automatically updates the campsite stock when a reservation is created or deleted.

## Error Handling

The API returns HTTP status codes to indicate success or failure of an operation. In the event of an error, the API will returns an error message.
