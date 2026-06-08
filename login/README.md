# CSC426 Login Authentication App

A web based login authentication application built with HTML, CSS, JavaScript,
and PHP for the CSC426 Weekly Dev exercise.

## Files

| File            | Role                                                      |
|-----------------|-----------------------------------------------------------|
| `index.html`    | Login form with username, password, log in, and reset     |
| `style.css`     | Responsive styling, dark theme, mobile friendly layout    |
| `script.js`     | Client side validation, password toggle, AJAX submit      |
| `login.php`     | Server side validation and credential check, returns JSON |
| `users.php`     | User store holding bcrypt hashed passwords                |
| `dashboard.php` | Protected page that requires an active session            |
| `logout.php`    | Ends the session and returns to the login page            |

## Features

* Username and password fields with a submit (Log in) button and a reset button.
* Input validation on both sides. The browser checks for empty fields and
  minimum length, and PHP repeats those checks because the client can never be
  trusted on its own.
* Clear success and error messages shown in an alert area.
* Passwords are stored as bcrypt hashes and checked with `password_verify`,
  never compared in plain text.
* Sessions are used to protect the dashboard, and the session id is regenerated
  on login to reduce fixation risk.
* Responsive interface that adapts cleanly from desktop down to small phones.
* Show and hide password toggle.

## Demo credentials

```
username: admin
password: password123
```

## Run locally

You need PHP installed (PHP 8 or newer is recommended).

```bash
cd login
php -S localhost:8000
```

Then open `http://localhost:8000` in your browser.

## Adding or changing a user

Generate a hash and paste it into `users.php`:

```bash
php -r "echo password_hash('yourNewPassword', PASSWORD_DEFAULT), PHP_EOL;"
```

## Deployment notes

This app needs a host that can run PHP and sessions. Vercel and Netlify only
serve static files by default, so they will not execute the PHP. Use one of
these instead:

* Render (with a PHP environment or a Docker image)
* Railway
* InfinityFree or 000webhost (free PHP hosts)
* Any shared cPanel host such as Hostinger

Upload the contents of the `login` folder to the web root, confirm the host
runs PHP 8, then visit your deployed URL. Submit both the GitHub repository link
and the deployed application link as the brief requests.
