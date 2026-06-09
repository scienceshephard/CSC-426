# CSC426

Coursework repository for CSC426. This is where the practical work for the course is kept across the semester.

The current submission is the weekly dev exercise, which has two separate tasks:

1. A desktop calculator built with Java Swing.
2. A web login authentication app built with HTML, CSS, JavaScript and PHP.

Each task sits in its own folder and has its own README with more detail.

## Repository structure

```
.
├── calculator/        Java Swing calculator
│   ├── Calculator.java
│   └── README.md
└── login/             PHP login authentication app
    ├── index.html
    ├── style.css
    ├── script.js
    ├── login.php
    ├── users.php
    ├── dashboard.php
    ├── logout.php
    └── README.md
```

## 1. Calculator (Java Swing)

A GUI calculator that handles addition, subtraction, multiplication, division, integer division, power and modulo, along with clear and backspace. It catches divide by zero and accepts both the on screen buttons and the keyboard.

Run it (JDK 17 or newer):

```bash
cd calculator
javac Calculator.java
java Calculator
```

See `calculator/README.md` for the full list of operations.

## 2. Login app (HTML, CSS, JavaScript, PHP)

A login screen with username and password fields, a login button and a reset button. Input is validated in the browser and again on the server, passwords are stored as bcrypt hashes, and a successful login opens a dashboard page that is protected by a PHP session.

**Live demo:** https://login-dev-ope.42web.io/

Run it locally (PHP 8 or newer):

```bash
cd login
php -S localhost:8000
```

Then open http://localhost:8000 and sign in with the demo account:

```
username: admin
password: password123
```

Deployment steps and the full feature list are in `login/README.md`.

## Tools used

Java, Swing, HTML, CSS, JavaScript, PHP.
