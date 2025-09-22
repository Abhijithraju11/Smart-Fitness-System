-- Database schema for Smart Fitness System
CREATE DATABASE fitnessdb;
USE fitnessdb;

CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100),
  email VARCHAR(100) UNIQUE,
  password VARCHAR(100)
);
