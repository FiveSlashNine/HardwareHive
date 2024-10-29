# HardwareHive

Welcome to HardwareHive, a comprehensive full-stack e-commerce application tailored for hardware enthusiasts! With a mobile-friendly Android frontend, a robust Spring Boot backend, and Firebase for secure authentication, HardwareHive provides a seamless shopping experience for users to explore, manage, and purchase their favorite hardware products.

## Installation

### Prerequisites
- Java 21 for Spring Boot
- Android Studio for Android development
- Firebase account for user authentication
- Docker compose to build the corresponding containers

### Backend Setup

**1. Clone the Repository:**
```bash
git clone https://github.com/FiveSlashNine/HardwareHive
cd HardwareHive/HardwareHive_Backend
```
**2. Configure Database:**
Update application.properties and the docker compose .yml file with your database credentials.

**3. Build and run the spring boot app**
```bash
./gradlew clean build (You need to have a running instance of the postgres db that you will use)
docker compose up --build
```

### Frontend Setup

1. Open Android Studio and load the HardwareHive project folder.
2. Configure Firebase:
	 Go to Firebase and enable authenticaction with email and password.
	 Add google-services.json from your Firebase project to app.
4. Build and Run on an emulator. Remember to alter the Base_URL constant on the Retrofit2Client class in case you want the app to run on a physical device. 

## Tech Stack
📱 Frontend: Android (Java)

- UI Design: Built with Material Design principles for a modern and responsive interface.
- Networking: Retrofit to handle HTTP requests between the app and the backend.
- Image Loading: Picasso for fast, efficient image loading and caching.

🔧 Backend: Spring Boot API

- Framework: Spring Boot for RESTful API development.
- Database: PostgreSQL for structured data management.
- API Documentation: OpenAPI Specification (OAS) for clear, auto-generated API documentation.

🔐 Authentication: Firebase

- User Auth: Secure user authentication using Firebase Authentication.

## Future Enhancements
- Secure the api endpoints.
- Store the orders and allow the admin to view them.
- Add filters:  Create filter options so that the user can quickly find desired items brand, price, and specifications.
- Payment Gateway Integration: Add Stripe or PayPal for secure payments.
- Wishlist Feature: Enable users to save favorite products.
- Real-Time Notifications: For order status updates and promotional offers.


## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

## License

[MIT](https://choosealicense.com/licenses/mit/)