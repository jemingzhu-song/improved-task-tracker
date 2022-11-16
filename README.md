# improved-task-tracker

🎟 A minimal and clean task tracker

The same task tracker application, but built with a React/Typescript Frontend and a Kotlin/Spring Boot Backend, with a PostgreSQL database!

Note: This application has been Dockerised! Instructions to run this on your local machine:

1. Pull from this repository.
2. Make sure you have docker installed on your machine.
3. In the root directory, run the command:

      > docker-compose up

4. This will generate 3 containers:
    - "improved_task_tracker_frontend_c": Container for the frontend of this application
    - "improved_task_tracker_backend_c": Container for the backend of this application
    - "improved_task_tracker_db_c": Container for the database (PostgreSQL) of this application
5. Open a browser, and go to the URL: "localhost:3000".
6. The application should be running!
