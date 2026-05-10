# 🛣️ Smart Toll Payment System

A full-stack web application that simulates India's **FASTag-based electronic toll collection system**. Built with Java Spring Boot backend and HTML/CSS/JS frontend.

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen?style=flat-square&logo=springboot)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=flat-square&logo=mysql)
![JUnit](https://img.shields.io/badge/Tests-14%20Passing-success?style=flat-square&logo=junit5)
![License](https://img.shields.io/badge/License-MIT-yellow?style=flat-square)

---

## 📌 About The Project

Smart Toll Payment System is a web-based application that digitizes toll collection using FASTag technology. Users can register vehicles, manage a digital wallet, and simulate automatic toll deduction when passing through toll booths — just like the real FASTag system on Indian highways.

---

## ✨ Features

### User Features
- 🔐 **JWT Authentication** — Secure register/login with BCrypt password encryption
- 🚗 **Vehicle Management** — Register multiple vehicles with unique FASTag IDs
- 💰 **Digital Wallet** — Recharge wallet and track balance in real-time
- 🛣️ **FASTag Simulation** — Auto toll deduction when vehicle crosses a booth
- 📊 **Transaction History** — Complete history with SUCCESS/INSUFFICIENT_BALANCE status
- 📧 **Email Alerts** — Notifications on toll deduction and low balance

### Admin Features
- 👥 **User Management** — View all registered users
- 🏗️ **Booth Management** — Add, activate/deactivate toll booths
- 📋 **Transaction Overview** — Monitor all transactions across all users
- 🚫 **Vehicle Blacklisting** — Block specific vehicles from toll crossing

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Backend | Java 21, Spring Boot 3.2.5 |
| Security | Spring Security, JWT (jjwt 0.11.5) |
| Database | MySQL 8, Spring Data JPA, Hibernate |
| Frontend | HTML5, CSS3, Bootstrap 5, Vanilla JS |
| Template Engine | Thymeleaf |
| Testing | JUnit 5, Mockito 5 |
| Build Tool | Maven |

---

## 📁 Project Structure


---

## 🗄️ Database Schema

| Table | Description |
|-------|-------------|
| `users` | User accounts with roles (USER/ADMIN) |
| `vehicles` | Registered vehicles with FASTag IDs |
| `wallets` | Digital wallet with balance per user |
| `toll_booths` | Toll booth locations and fee amounts |
| `transactions` | All toll payment records |

---

## 🚀 Getting Started

### Prerequisites
- Java 21+
- Maven 3.8+
- MySQL 8.0+

### Setup Steps

**1. Clone the repository**
```bash
git clone https://github.com/YOUR_USERNAME/smart-toll-payment-system.git
cd smart-toll-payment-system
```

**2. Create MySQL database**
```sql
CREATE DATABASE smart_toll_db;
CREATE USER 'tolluser'@'localhost' IDENTIFIED BY 'toll@1234';
GRANT ALL PRIVILEGES ON smart_toll_db.* TO 'tolluser'@'localhost';
FLUSH PRIVILEGES;
```

**3. Configure application.properties**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/smart_toll_db
spring.datasource.username=tolluser
spring.datasource.password=toll@1234
```

**4. Run the application**
```bash
mvn spring-boot:run
```

**5. Open in browser**

**6. Add sample toll booths**
```sql
INSERT INTO toll_booths (name, location, fee_amount, is_active)
VALUES ('Delhi Highway Booth', 'NH-48 Delhi', 65.00, 1);
INSERT INTO toll_booths (name, location, fee_amount, is_active)
VALUES ('Mumbai Express Booth', 'NH-8 Mumbai', 85.00, 1);
```

---

## 🔌 API Endpoints

### Auth APIs (Public)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register new user |
| POST | `/api/auth/login` | Login and get JWT token |

### User APIs (Protected)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/vehicles` | Register a vehicle |
| GET | `/api/vehicles` | Get my vehicles |
| GET | `/api/wallet` | Get wallet balance |
| POST | `/api/wallet/recharge` | Recharge wallet |
| POST | `/api/toll/pay` | Pay toll via FASTag |
| GET | `/api/toll/transactions` | Get transaction history |
| GET | `/api/toll/booths` | Get active toll booths |

### Admin APIs (Protected)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/admin/users` | Get all users |
| GET | `/api/admin/transactions` | Get all transactions |
| POST | `/api/admin/booths` | Add new toll booth |
| PUT | `/api/admin/booths/{id}/toggle` | Activate/deactivate booth |
| PUT | `/api/admin/vehicles/{id}/blacklist` | Blacklist a vehicle |

---

## 🧪 Running Tests

```bash
mvn test
```

**14 tests across 3 test classes:**
- `AuthServiceTest` — 4 tests (register, login scenarios)
- `WalletServiceTest` — 4 tests (balance, recharge scenarios)
- `TollServiceTest` — 5 tests (payment, blacklist, email scenarios)

---

## 🐳 Docker Setup

```bash
# Build image
docker build -t smart-toll-system .

# Run with Docker
docker run -p 8080:8080 smart-toll-system
```

---

## 📱 Screenshots

> Add screenshots of your login page, dashboard, and admin panel here
[Image Alt](https://github.com/manshirupam/smart-toll-payment-system-/blob/0d425247ac0e70414831aafe0171c8df005677a0/%20%20Transactions%202026-05-11%20at%2001.47.53.jpeg)
> [Image Alt](https://github.com/manshirupam/smart-toll-payment-system-/blob/cc5eddd22d40a73b3c091daa9787dac6eeb9f32f/%20%20Users%202026-05-11%20at%2001.43.28.jpeg)
> [Image Alt](https://github.com/manshirupam/smart-toll-payment-system-/blob/079dff3531681a2f2d61df726826e22ee7627a50/%20%20Vehicles%202026-05-11%20at%2001.45.07.jpeg)
> [Image Alt](https://github.com/manshirupam/smart-toll-payment-system-/blob/a9c04d9ad345efbe88d175f6b25f31857e246d8a/%20Dashboard%202026-05-11%20at%2001.40.47.jpeg)
> [Image Alt](https://github.com/manshirupam/smart-toll-payment-system-/blob/aedea94117d30a07a1f5082d251c61ac9d420278/%20Login%20Page%202026-05-11%20at%2001.40.48.jpeg)
> [Image Alt](https://github.com/manshirupam/smart-toll-payment-system-/blob/3e019d3a9c104d92757878967ba52ab16919d2be/%20Overview%202026-05-11%20at%2001.40.48%20(2).jpeg)
> [Image Alt](https://github.com/manshirupam/smart-toll-payment-system-/blob/e9ac7ad035b12dcdfff8d7e62c5a0f2a59530bf7/%20Paytoll%202026-05-11%20at%2001.40.48%20(1).jpeg)
> [Image Alt](https://github.com/manshirupam/smart-toll-payment-system-/blob/881c2defdd43111101c54a1ac0306db56c4221b6/%20Wallet%202026-05-11%20at%2001.44.16.jpeg)
> [Image Alt](
---

## 🤝 Contributing

Pull requests are welcome! For major changes, please open an issue first.

---

## 📄 License

This project is licensed under the MIT License.

---

## 👨‍💻 Author

**Manshi Rupam**
- GitHub:https://github.com/manshirupam/smart-toll-payment-system-


---

⭐ If you found this project helpful, please give it a star!
