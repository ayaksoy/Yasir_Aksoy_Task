# 🚀 Insider QA Automation Project

This repository contains a comprehensive test automation framework covering **API**, **UI**, and **Load/Performance** testing.

The project is built using **Java**, **TestNG**, **RestAssured** (API), **Selenium** (UI), and **Apache JMeter** (Load).

---

## 🛠️ Prerequisites & Installation

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/ayaksoy/Yasir_Aksoy_Task.git
    ```
2.  **Install dependencies:**
    ```bash
    mvn clean install -DskipTests
    ```

---

## 🧪 1. API Testing
API tests cover CRUD operations for the PetStore API. The framework uses a modular structure with dedicated `models` and `utils`.

* **Test File:** `src/test/java/com/insider/test/API/PetCRUDTests.java`
* **How to Run:**
    Right-click `src/test/resources/configs/api_testng.xml` and run as TestNG Suite, or use Maven:
    ```bash
    mvn test -DsuiteXmlFile=src/test/resources/configs/api_testng.xml
    ```

---

## 🖥️ 2. UI Testing
UI tests implement the **Page Object Model (POM)** design pattern for maintainability. It includes generic utilities, test bases, and page classes.

* **Structure:** `pages`, `testbase`, `testcases`.
* **How to Run:**
    Right-click `src/test/resources/configs/testng.xml` and run as TestNG Suite, or use Maven:
    ```bash
    mvn test -DsuiteXmlFile=src/test/resources/configs/testng.xml
    ```

---

## 📉 3. Load Testing (JMeter)

The load testing scenarios are located in the `Load test` directory.

### 🛑 Security & WAF Analysis (n11.com)
The initial goal was to test the Search Module of `n11.com`. However, the application's **WAF (Web Application Firewall)** actively blocks automated traffic (even with valid User-Agent/Cookie simulation), resulting in a **403 Forbidden** response.

**Evidence of WAF Blocking:**
![N11 WAF Blocked](Load%20test/screenshots/n11_blocked_waf.png)

### ✅ Proof of Concept (Google)
To demonstrate the scripting capability, **assertions**, and **scenario management**, the same test logic was successfully implemented and validated against **Google Search**.

* **File:** `Load test/jmxFiles/2_Google_Search_Success.jmx`
* **How to Run:**
    1.  Open Apache JMeter.
    2.  Load the `.jmx` file.
    3.  Click Start to view execution results.

---

## 👨‍💻 Author
**Yasir** - QA Automation Engineer
