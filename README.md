# Cloud-Expense-Tracker
Cloud Expense Tracker is a cloud-based expense management application built using Java Spring Boot, React, MySQL, Docker, and AWS. It enables users to securely add, update, delete, and track daily expenses with interactive dashboards and reports. The project demonstrates DevOps practices including containerization, CI/CD pipelines.
# 🚀 DevOps Project Report: Automated CI/CD Pipeline for Cloud Expense Tracker on AWS

**Author:** Your Name
**Date:** June 2026

---

# 📋 Table of Contents

1. Project Overview
2. Architecture Diagram
3. AWS EC2 Instance Preparation
4. Install Dependencies on EC2
5. Jenkins Installation and Setup
6. GitHub Repository Configuration
7. Docker Configuration
8. Jenkins Pipeline Creation and Execution
9. Deployment Verification
10. Infrastructure Diagram
11. Workflow Diagram
12. Future Enhancements
13. Conclusion

---

# 1️⃣ Project Overview

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

# 🏗️ Architecture Diagram

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

---

# 3️⃣ AWS EC2 Instance Preparation

### Instance Configuration

OS: Ubuntu 22.04 LTS
Instance Type: t2.micro
Storage: 20 GB

### Security Groups

SSH – 22
HTTP – 80
Jenkins – 8080
React – 3000
Spring Boot – 8081
MySQL – 3306

### Connect to EC2

ssh -i key.pem ubuntu@<public-ip>

---

# 4️⃣ Install Dependencies

sudo apt update && sudo apt upgrade -y

sudo apt install git docker.io docker-compose openjdk-21-jdk maven -y

sudo systemctl start docker
sudo systemctl enable docker

sudo usermod -aG docker ubuntu
newgrp docker

---

# 5️⃣ Jenkins Installation and Setup

curl -fsSL https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key | sudo tee /usr/share/keyrings/jenkins-keyring.asc

echo deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc] https://pkg.jenkins.io/debian-stable binary/ | sudo tee /etc/apt/sources.list.d/jenkins.list

sudo apt update
sudo apt install jenkins -y

sudo systemctl start jenkins
sudo systemctl enable jenkins

sudo cat /var/lib/jenkins/secrets/initialAdminPassword

Open:

http://<ec2-ip>:8080

---

# 6️⃣ GitHub Repository Structure

Cloud-Expense-Tracker
│
├── frontend
├── backend
├── diagrams
├── screenshots
├── docs
├── Dockerfile
├── docker-compose.yml
├── Jenkinsfile
└── README.md

---

# 7️⃣ Docker Configuration

## Backend Dockerfile

FROM eclipse-temurin:21-jre
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

## docker-compose.yml

services:

mysql:
image: mysql:8
environment:
MYSQL_ROOT_PASSWORD: root
MYSQL_DATABASE: expense_db

backend:
build: ./backend
ports:

* "8081:8081"
  depends_on:
* mysql

frontend:
build: ./frontend
ports:

* "3000:3000"

---

# 8️⃣ Jenkins Pipeline

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

---

# 9️⃣ Deployment Verification

docker ps

Application:

Frontend:
http://<ec2-ip>:3000

Backend API:
http://<ec2-ip>:8081

Jenkins:
http://<ec2-ip>:8080

---

# 🔮 Future Enhancements

• Kubernetes Deployment
• Terraform Infrastructure Provisioning
• SonarQube Code Quality Analysis
• Prometheus & Grafana Monitoring
• Docker Hub Integration
• Blue-Green Deployment Strategy

---

# ✅ Conclusion

The Cloud Expense Tracker project successfully implements a fully automated DevOps CI/CD pipeline using GitHub, Jenkins, Docker, and AWS EC2. The pipeline reduces manual deployment efforts, improves reliability, and enables rapid application delivery.
