# 🏦 Enterprise Vendor Payment System

![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.0-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)

A robust, transactional REST API designed to manage the procurement lifecycle—from Vendor onboarding to Purchase Order (PO) creation and Invoice Reconciliation.

Built with **Spring Boot 3** and **MySQL**, this system enforces strict financial integrity using ACID transactions and composite unique constraints.

---

## 🚀 Key Features

### 1. **Financial Reconciliation Engine** 💰
- **Automated Balance Deduction:** Approving an invoice automatically locks the associated PO and deducts the amount in a thread-safe transaction.
- **ACID Compliance:** Uses `@Transactional` to ensure that if a balance update fails, the invoice status rolls back instantly. No "half-finished" data.
- **Validation Rules:** Prevents overdrafts (cannot approve an invoice if `PO Balance < Invoice Amount`).

### 2. **Advanced Data Integrity** 🛡️
- **Composite Unique Constraints:** The system enforces a database-level constraint on `(vendor_id, invoice_number)`.
    - *Scenario:* Vendor A can submit "INV-001". Vendor B can also submit "INV-001".
    - *Prevention:* Vendor A **cannot** submit "INV-001" twice.
- **Lazy Loading:** Optimized Hibernate relationships (`FetchType.LAZY`) to prevent memory leaks (N+1 problem) when fetching lists of invoices.

### 3. **Modern API Architecture** 🔌
- **DTO Pattern:** Exposes clean `Request/Response` records (Java 16+) instead of leaking raw Database Entities.
- **Swagger UI:** Fully documented API interface available at `/swagger-ui.html`.
- **RESTful Design:** Uses proper HTTP verbs (`POST` for creation, `PATCH` for state changes, `GET` for retrieval).

---

## 🛠️ Tech Stack

- **Core:** Java 17+, Spring Boot 3.2
- **Data:** Spring Data JPA (Hibernate), MySQL 8
- **Testing:** JUnit 5, Mockito
- **Documentation:** OpenAPI (Swagger)
- **Tools:** Maven, Git, Lombok

---

## ⚙️ Setup & Installation

### Prerequisites
- Java 17 or higher
- MySQL Server running on port `3306`

### 1. Clone the Repository
```bash
git clone [https://github.com/HumphreyMahlangu/vendor-payment-system.git](https://github.com/HumphreyMahlangu/vendor-payment-system.git)
cd vendor-payment-system 
```
### 2. Configure Database
- Update src/main/resources/application.properties with your credentials:

```bash
spring.datasource.url=jdbc:mysql://localhost:3306/vendor_payment_db
spring.datasource.username=root
spring.datasource.password=your_password
```

### 3. Run the Application
- mvn spring-boot:run

## 📖 API Documentation
#### Once the application is running, access the interactive Swagger UI dashboard:
#### 👉 http://localhost:8080/swagger-ui/index.html

| Method | Endpoint                          | Description                                                   |
|--------|-----------------------------------|---------------------------------------------------------------|
| POST   | /api/purchase-orders              | Create a new PO with an initial balance.                     |
| POST   | /api/invoices                     | Submit an invoice (Status: PENDING).                         |
| PATCH  | /api/invoices/{id}/decision       | Approve/Reject invoice & trigger fund deduction.             |

## 🧪 Testing

The project includes automated Unit Tests for the business logic layer.

### Run tests using Maven:

```bash
mvn test
```

## Key Test Coverage

InvoiceServiceTest.java ensures that:

1. Approving an invoice correctly reduces the PO balance.

2. Invoices cannot be approved if funds are insufficient.

3. Rejections do not touch the PO balance.