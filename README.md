#  DevOps Project Report: Automated CI/CD Pipeline for Cloud Expense Tracker on AWS
# Project Overview

This project demonstrates the deployment of a Cloud Expense Tracker application using a fully automated CI/CD pipeline on AWS EC2. The application follows a two-tier architecture consisting of:

• Frontend: React.js
• Backend: Spring Boot (Java 21)
• Database: MySQL 8
• Containerization: Docker & Docker Compose
• CI/CD Tool: Jenkins
• Version Control: GitHub
• Cloud Platform: AWS EC2

The pipeline automatically builds and deploys the application whenever new code is pushed to GitHub, ensuring seamless and reliable deployments.

---

#  Architecture Diagram

Developer
↓
GitHub Repository
↓
Jenkins Server (AWS EC2)
↓
Docker Build
↓
Docker Compose
↓
React Container
↓
Spring Boot Container
↓
MySQL Container
↓
Cloud Expense Tracker Running on AWS

2.Backend (Spring Boot)
Entity: Expense
@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String category;
    private double amount;
    private String date;

    public Expense() {}

    public Expense(String title, String category,
                   double amount, String date) {
        this.title = title;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    // Getters and Setters
}
Repository
@Repository
public interface ExpenseRepository
        extends JpaRepository<Expense, Long> {
}
Service
@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository repo;

    public List<Expense> getAll() {
        return repo.findAll();
    }

    public Expense save(Expense expense) {
        return repo.save(expense);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
Controller
@RestController
@RequestMapping("/api/expenses")
@CrossOrigin("*")
public class ExpenseController {

    @Autowired
    private ExpenseService service;

    @GetMapping
    public List<Expense> getAll() {
        return service.getAll();
    }

    @PostMapping
    public Expense save(
            @RequestBody Expense expense) {
        return service.save(expense);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id) {
        service.delete(id);
    }
}
application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/expense_db
spring.datasource.username=root
spring.datasource.password=root

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
server.port=8080
Frontend (React)

Install:

npm create vite@latest frontend -- --template react
npm install axios react-router-dom
API Call
import axios from "axios";

const API = "http://localhost:8080/api/expenses";

export const getExpenses = () =>
  axios.get(API);

export const addExpense = (expense) =>
  axios.post(API, expense);

export const deleteExpense = (id) =>
  axios.delete(`${API}/${id}`);
Add Expense Component
function AddExpense() {
  const [title, setTitle] = useState("");
  const [category, setCategory] = useState("");
  const [amount, setAmount] = useState("");

  const save = async () => {
    await axios.post(
      "http://localhost:8080/api/expenses",
      {
        title,
        category,
        amount,
        date: new Date()
      }
    );
    alert("Expense Added");
  };

  return (
    <>
      <input
        placeholder="Title"
        onChange={e =>
          setTitle(e.target.value)}
      />

      <input
        placeholder="Category"
        onChange={e =>
          setCategory(e.target.value)}
      />

      <input
        placeholder="Amount"
        onChange={e =>
          setAmount(e.target.value)}
      />

      <button onClick={save}>
        Add
      </button>
    </>
  );
}
Dockerfile
FROM openjdk:21
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
docker-compose.yml
version: '3'

services:
  mysql:
    image: mysql:8
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: expense_db
    ports:
      - "3306:3306"

  app:
    build: ./backend
    ports:
      - "8080:8080"
    depends_on:
      - mysql

---
3. Prerequisites

Install:

Git
Docker
Docker Compose
Java 21
Maven
Jenkins
AWS Account
4. Step 1: Launch AWS EC2 Instance
Create Instance
OS: Ubuntu 22.04
Instance Type: t2.micro
Storage: 20 GB
Security Group
Port	Purpose
22	SSH
80	HTTP
8080	Jenkins
3000	React
3306	MySQL

Connect:

ssh -i key.pem ubuntu@<public-ip>
5. Step 2: Install Dependencies

Update server:

sudo apt update
sudo apt upgrade -y

Install Git:

sudo apt install git -y

Install Docker:

sudo apt install docker.io -y
sudo systemctl start docker
sudo systemctl enable docker

Install Docker Compose:

sudo apt install docker-compose -y

Add user:

sudo usermod -aG docker ubuntu
newgrp docker
6. Step 3: Install Jenkins

Install Java:

sudo apt install openjdk-21-jdk -y

Install Jenkins:

curl -fsSL https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key | sudo tee /usr/share/keyrings/jenkins-keyring.asc

echo deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc] https://pkg.jenkins.io/debian-stable binary/ | sudo tee /etc/apt/sources.list.d/jenkins.list

sudo apt update
sudo apt install jenkins -y

Start Jenkins:

sudo systemctl start jenkins
sudo systemctl enable jenkins

Get password:

sudo cat /var/lib/jenkins/secrets/initialAdminPassword

Open:

http://<ec2-ip>:8080
7. Step 4: Configure GitHub Repository

Repository Structure:

Cloud-Expense-Tracker
│
├── frontend
├── backend
├── docs
├── screenshots
├── .github/workflows
├── Dockerfile
├── docker-compose.yml
├── Jenkinsfile
└── README.md

Push code:

git init
git add .
git commit -m "Initial Commit"
git remote add origin <repo-url>
git push -u origin main
8. Step 5: Dockerize Application
Dockerfile
FROM eclipse-temurin:21-jre
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
docker-compose.yml
version: '3'

services:
  mysql:
    image: mysql:8
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: expense_db

  backend:
    build: ./backend
    ports:
      - "8080:8080"
    depends_on:
      - mysql
9. Step 6: Configure Jenkins Pipeline
Jenkinsfile
pipeline {
    agent any

    stages {
        stage('Clone') {
            steps {
                git 'https://github.com/yourusername/Cloud-Expense-Tracker.git'
            }
        }

        stage('Build') {
            steps {
                sh 'docker compose build'
            }
        }

        stage('Deploy') {
            steps {
                sh 'docker compose up -d'
            }
        }
    }
}
10. Step 7: Deploy Application

Run:

docker compose up -d

Check:

docker ps
11. Verify Deployment

Application:

http://<ec2-ip>:8080

Jenkins:

http://<ec2-ip>:8080

---

# ✅ Conclusion

The Cloud Expense Tracker project successfully implements a fully automated DevOps CI/CD pipeline using GitHub, Jenkins, Docker, and AWS EC2. The pipeline reduces manual deployment efforts, improves reliability, and enables rapid application delivery.
