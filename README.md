# 42 in Action: Programming is about thinking, not syntax

_This project is a demonstration of the adaptability and problem-solving philosophy championed by 42 programming school: programming is about thinking, not syntax._

Although `Java` isn't my primary programming language, I challenged myself to translate a part of a 42 project, originally written in `Python`, into `Java`. The goal wasn't to become an expert in `Java` overnight but to prove that with strong programming fundamentals, the ability to self-learn, a problem-solving mindset, tackling a new language is very much than possible. Let's review how far I got in just about 30 hours.

## From Pong to Tic Tac Toe

### The Original Project: `ft_transcendence`
`ft_transcendence` is a Single Page Application (SPA) recreation of the classic Pong game, developed using:

- **Backend**: built with `Django`, utilizing the [`Django REST Framework`](https://www.django-rest-framework.org/) for APIs and [`Django Channels`](https://channels.readthedocs.io/en/stable/) for managing WebSocket communication
- **Frontend**: implemented with `Vanilla JavaScript`
- **Architecture**: the backend services were designed as microservices and containerized using `Docker`

It's the last project of the 42 Core Curriculum and the work of our group can be followed here: [Repository](https://github.com/andreaulicna/42_ft_transcendence) | [Kanban Board](https://github.com/users/andreaulicna/projects/13/)

### Scope of the Rewritten Project
For this adapted project, I focused on rewriting user registration and login functionality from the original Pong game backend. Additionally, this project features a local match and an AI match of Tic Tac Toe game.

The backend is built using a combination of `Java` and `Kotlin`, showcasing their interoperability:  

- **`Java`**:  
  - Core functionality and logic are implemented in Java classes.  
  - The [`Spring Boot framework`](https://spring.io/projects/spring-boot) is used to handle API calls and backend services.  

- **`Kotlin`**:  
  - Controllers are written in Kotlin to demonstrate seamless integration with Java.  

Further, the backend is connected to a `PostgreSQL` to store user information and record match results.

All functionality is exposed via backend `API calls`, which are connected to a (very, because I'm not on a path to be a frontend developer) simple [`Thymeleaf`](https://www.thymeleaf.org/) frontend.

## Incremental Development

I followed an incremental development approach, where each phase built upon the previous one, allowing me to first understand the fundamentals of Java to than later built something that has at least the ambition to mimic an ideal customer experience.

### Command-Line Application 

First, I focused on implementing the fundamental game logic for Tic Tac Toe via a simple command-line application

