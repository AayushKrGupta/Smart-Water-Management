Smart Water Management Monitoring System
This project is a Smart Water Management Monitoring System application that uses a REST API (Retrofit) to fetch sensor data from a database. The application monitors water tank levels, water usage, and other relevant metrics in real-time.

Features
Water Tank Monitoring: Track tank levels and usage in real-time.
Sensor Data Visualization: Display sensor values like water level, motor status, sump level, and more.
REST API Integration: Uses Retrofit to fetch data from a remote server.
User-Friendly Interface: Designed with a clear and intuitive UI for easy monitoring.
Technology Stack
Android (Java/Kotlin) - for developing the mobile application.
Retrofit - for HTTP requests to the REST API.
REST API - serves as the backend interface to retrieve data from the database.
XML Layouts - for designing the app's UI components.
Project Structure
The main components of this application are:

UI Components:

Top Container: Displays tank information including tank level chart, tank name, and capacity.
Bottom Container: Uses a GridLayout to display other sensor values such as total capacity, usage liters, motor status, sump level, etc.
Networking:

Retrofit Setup: Manages HTTP requests to the REST API and maps responses to Java/Kotlin objects.
Data Models: Objects representing the structure of the data fetched from the API.
Database:

The database stores data gathered from sensors, which is fetched via the REST API.
Prerequisites
Before running the project, ensure you have:

Android Studio installed.
An API endpoint with data available in JSON format.
The necessary API credentials (if authentication is required).
