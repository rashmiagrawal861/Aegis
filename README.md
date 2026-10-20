# Aegis — Emergency Response System

Aegis is a web-based emergency response project that I'm building using Java, Spring Boot, and MySQL.

The idea behind this project is to make communication between patients, emergency responders, and hospitals more organized during emergencies.

Instead of handling everything separately, Aegis brings the three sides together in one system where emergency requests can be created, accepted, and tracked.

## How it works

Aegis has three main dashboards:

**Patient Dashboard**

Patients can send an SOS request by entering their location, emergency type, and a short description. They can also check the status of their request.

**Responder Dashboard**

Responders can view emergency requests, accept them, and update their status as they proceed. There's also an option to open directions using Google Maps.

**Hospital Dashboard**

Hospitals can see incoming emergency cases, follow their progress, and update whether they're ready to receive the patient.

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- MySQL
- HTML, CSS, JavaScript
- Maven

## Emergency Status Flow

```text
REQUESTED
   ↓
ACCEPTED
   ↓
EN_ROUTE
   ↓
PATIENT_PICKED_UP
   ↓
ARRIVED_AT_HOSPITAL
```

Hospital readiness is managed separately.

## Running the Project Locally

You'll need Java, MySQL, and Maven installed.

**1. Clone the repository**

```bash
git clone YOUR_REPOSITORY_URL
cd aegis
```

**2. Create a MySQL database**

```sql
CREATE DATABASE aegis_db;
```

**3. Configure the database**

Check the database settings in `src/main/resources/application.properties`.

Set the `DB_PASSWORD` environment variable to your MySQL password.

**4. Run the application**

```bash
mvn spring-boot:run
```

**5. Open the website**

Visit `http://localhost:8080/index.html`.

You can navigate to the Patient, Responder, and Hospital dashboards from there.

## What I Want to Add Next

There are still a few things I'd like to improve:

- Login and authentication for different users
- Real-time notifications
- Live ambulance tracking
- Hospital bed availability
- Deployment so the project can be accessed online

## Note

Aegis is currently a college portfolio project. It demonstrates emergency coordination workflows but isn't connected to actual ambulance services or hospitals. It shouldn't be used for real emergencies or real patient data.