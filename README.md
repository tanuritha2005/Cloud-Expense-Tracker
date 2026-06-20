# Cloud-Expense-Tracker
Cloud Expense Tracker is a cloud-based expense management application built using Java Spring Boot, React, MySQL, Docker, and AWS. It enables users to securely add, update, delete, and track daily expenses with interactive dashboards and reports. The project demonstrates DevOps practices including containerization, CI/CD pipelines.
DevOps Project Report: Automated CI/CD Pipeline for Cloud Expense Tracker
📋 Table of Contents
1. Project Overview
2. Architecture Diagram
3. Prerequisites
4. Step 1: Launch AWS EC2 Instance
5. Step 2: Install Dependencies
6. Step 3: Install Jenkins
7. Step 4: Configure GitHub Repository
8. Step 5: Dockerize Application
9. Step 6: Configure CI/CD Pipeline
10. Step 7: Deploy Application
11. Step 8: Verify Deployment
12. Screenshots
13. Future Enhancements
14. Conclusion
1. Project Overview

The Cloud Expense Tracker is a two-tier application that allows users to manage their expenses. The application is containerized using Docker and automatically deployed using Jenkins whenever code is pushed to GitHub.

Technologies Used
Java 21
Spring Boot
React
MySQL
Docker
Jenkins
GitHub
AWS EC2
2. Architecture Diagram
Developer
    ↓
GitHub Repository
    ↓
Jenkins Pipeline
    ↓
Docker Build
    ↓
Docker Compose
    ↓
Spring Boot Container
    ↓
MySQL Container
    ↓
Application Running on AWS EC2
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
