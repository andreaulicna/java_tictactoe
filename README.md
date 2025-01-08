# 42 in Action: Programming is about thinking, not syntax

_This project is a demonstration of the adaptability and problem-solving philosophy championed by 42 programming school: programming is about thinking, not syntax._

Although `Java` isn't my primary programming language, I challenged myself to translate a part of a 42 project, originally written in `Python`, into `Java`. The goal wasn't to become an expert in `Java` overnight but to prove that with strong programming fundamentals, the ability to self-learn, a problem-solving mindset, tackling a new language is very much than possible. Let's review how far I got in just about 40 hours.

<br>

## From Pong to Tic Tac Toe

### The Original Project: `ft_transcendence`
`ft_transcendence` is a Single Page Application (SPA) recreation of the classic Pong game, developed using:

- **Backend**: built with `Django`, utilizing the [`Django REST Framework`](https://www.django-rest-framework.org/) for APIs and [`Django Channels`](https://channels.readthedocs.io/en/stable/) for managing WebSocket communication
- **Frontend**: implemented with `Vanilla JavaScript`
- **Architecture**: the backend services were designed as microservices and containerized using `Docker`

It's the last project of the 42 Core Curriculum and the work of our group can be followed here: [Repository](https://github.com/andreaulicna/42_ft_transcendence/tree/backend_test) | [Kanban Board](https://github.com/users/andreaulicna/projects/13/)

<br>

### Scope of the Rewritten Project
For this adapted project, I focused on rewriting user registration and login functionality from the original Pong game backend. Additionally, this project features a local match and an AI match of Tic Tac Toe game.

The backend is built using a combination of `Java` and `Kotlin`, showcasing their interoperability:  

- **`Java`**:  
  - Core functionality and logic are implemented in Java classes.  
  - The [`Spring Boot framework`](https://spring.io/projects/spring-boot) is used to handle API calls and backend services.  

- **`Kotlin`**:  
  - llers are written in Kotlin to demonstrate seamless integration with Java.  

Further, the backend is connected to a `PostgreSQL` to store user information and record match results.

All functionality is exposed via backend `API calls`, which are connected to a (very, because I'm not on a path to be a frontend developer) simple [`Thymeleaf`](https://www.thymeleaf.org/) frontend.

<br>

## Incremental Development

I followed an incremental development approach, where each phase built upon the previous one, allowing me to first understand the fundamentals of Java to than later built something that has at least the ambition to mimic an ideal customer experience.

<br>

### 1. Command-Line Application 
First, I focused on implementing the fundamental game logic for Tic Tac Toe via a simple command-line application.
<table>
  <tr>
    <td>
      1. Local play that switches between asking for input <br> for Player X and Player O:
      <br>
      <br>
      <img src="https://github.com/user-attachments/assets/03d09437-5d35-442e-8d30-839a9600462e" alt="cli-local" width="800"/>
    </td>
    <td>
      <br>
      2. AI play that switches between asking for input for Player X and making a move for Player O:
      <br>
      <br>
      <img src="https://github.com/user-attachments/assets/457ac027-8655-437b-b3d4-d221cb809f4e" alt="cli-ai" width="800"/>
    </td>
  </tr>
  <tr>
   <td>
     3. The local play was somewhat playable:
     <br>
     <br>
     <img src="https://github.com/user-attachments/assets/3c67e399-9706-4319-880a-dd8077f3b9c2" alt="image" width="800"/>
   </td>
   <td>
     <br>
     4. The AI player was, however, making just random moves, and so was easy to beat:
     <br>
     <br>
     <img src="https://github.com/user-attachments/assets/1f43ba68-4dbe-4302-bf9b-78224883ace6" alt="poor ai" width="800"/>
   </td>
  </tr>
</table>

<br>

### 2. Web Interface
Moving on, I transitioned the game into a web-based application using `Spring Boot`. The idea was to have a very simple frontend to support a test-driven development of the backend.
<div align="center">
  <img src="https://github.com/user-attachments/assets/7949f4c2-62de-4939-b1c8-790faad71b1f" alt="image" width="400" />
  &nbsp;&nbsp;&nbsp;
  <img src="https://github.com/user-attachments/assets/46ffcc17-3a02-43e9-aecc-c9f6ebe1d60b" alt="image" width="275" />
</div>

<br>

### 3. AI Implementation (Minimax Algorithm)
Next, I researched algorithms for turn-based game and decided to implement minimax. That made AI play playable, but essentially unbeatable (see Future development for further explanation).
<div align="center">
  <img src="https://github.com/user-attachments/assets/be31edb1-0f54-4f1a-b1b5-e1b8863d0a92" alt="image" width="450" />
  &nbsp;&nbsp;&nbsp;
  <img src="https://github.com/user-attachments/assets/dff8738f-b76c-472e-b9d3-f85f73f160d9" alt="image" width="350" />
</div>

<br>

### 4. User Authentication and Database Integration
To introduce a bit of a user management and authentication layer to the application, the next step included implementing a user registration and login system.

<div align="center">
  <img src="https://github.com/user-attachments/assets/379dbbc2-3859-42a6-aa98-41bb868c41f3" alt="image1" width="300"/>
  &nbsp;&nbsp;&nbsp;
  <img src="https://github.com/user-attachments/assets/c161e934-f6dd-46e0-a3cf-8b1872f2cc48" alt="image2" width="350"/>
  &nbsp;&nbsp;&nbsp;
  <img src="https://github.com/user-attachments/assets/2b6fdda0-3748-42d8-a0b1-33346daebbf5" alt="image3" width="300"/>
</div>

<br>

Additionally, I also set up `PostgreSQL` database integration (see Database Diagram for more details) to store user information and the results of the AI matches for each user:
<br>
![image](https://github.com/user-attachments/assets/53bcc316-70ff-42e8-85a6-dc232b723b39)

![image](https://github.com/user-attachments/assets/d27fb628-6a0b-4525-aa60-8631467bb89e)

![image](https://github.com/user-attachments/assets/bcabc7f2-29c8-4043-b4fa-12a9538de24d)

<br>

### 5. Kotlin Integration (Interoperability Demonstration)
Finally, I rewrote a part of the code (llers `TicTacToeController.java` and `AuthController.java` into `Kotlin`. Given that `Kotlin` is completely interoperability with `Java` there was no disruption to the functionality of the application. I'll discuss the main changes in Java vs Kotlin part below.

<br>

## Main `Java` Concepts
This section highlights the main `Java` concepts used in this project. The concepts were predominantly used for demonstative purposes, and so may seem like a bit of an overengineering for such a small project.

<br>

### Interfaces
An interface defines a contract that classes can implement. It specifies methods without providing implementations.

The `Player` interface defines a single method `getSymbol()`, meaning that any class implementing `Player` must provide an implementation for this function:

```java
public interface Player {
    char getSymbol();
}
```

<br>

### Abstract Classes
Abstract classes are partially implemented classes that cannot be instantiated directly. They can include both abstract methods (without implementation) and concrete methods (with implementation).

`AbstractPlayer` implements the `Player` interface and provides a base implementation for `getSymbol()`:

```java
public abstract class AbstractPlayer implements Player {
    protected char symbol;

    public AbstractPlayer(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}

```

<br>

### Inheritance
Inheritance allows a class to reuse the functionality of a parent class.

`LocalPlayer` extends `AbstractPlayer`, inheriting the `symbol` field and the `getSymbol()` method. The constructor of `LocalPlayer` calls the superclass constructor using `super`:

```java
public class LocalPlayer extends AbstractPlayer {
    public LocalPlayer(char symbol) {
        super(symbol);
    }
}

```

<br>

### Encapsulation
Encapsulation hides the internal details of a class and exposes only necessary parts through methods.
The `symbol` field is declared as `protected`, so it is accessible to subclasses but hidden from external classes. The `getSymbol()` method provides controlled access to the `symbol` field:

```java
protected char symbol;

public AbstractPlayer(char symbol) {
    this.symbol = symbol;
}

@Override
public char getSymbol() {
    return symbol;
}

```

<br>

### Concrete Classes
A concrete class provides complete implementations of all its methods and can be instantiated.

```java
public class LocalPlayer extends AbstractPlayer {
    public LocalPlayer(char symbol) {
        super(symbol);
    }
}

```

<br> 

### Method Overriding
Method overriding allows a subclass to provide a specific implementation of a method that is already provided by its parent class.

```java
	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sb.append(board[i][j]);
                if (j < 2) sb.append(" | ");
            }
            sb.append("\n");
            if (i < 2) sb.append("---------\n");
        }
        return sb.toString();
    }
```
<br>

## Java vs Kotlin
This section takes a closer look at the main difference between `Java` and `Kotlin` as demostrated by examples from the rewriting of the controllers.
<br>
- [AuthController.kt](https://github.com/andreaulicna/csob_java_project/blob/d3e8dbe1a9060f59459c24667e20dfa9bffa5d2e/tictactoe_app/src/main/kotlin/com/app/tictactoe/controller/AuthController.kt) vs. [AuthController.java](https://github.com/andreaulicna/csob_java_project/blob/99b14ff3518a15edce8255cd25a8df7b917c0c0b/tictactoe_app/src/main/java/com/app/tictactoe/controller/AuthController.java)
- [TicTacToeController.kt](https://github.com/andreaulicna/csob_java_project/blob/d3e8dbe1a9060f59459c24667e20dfa9bffa5d2e/tictactoe_app/src/main/kotlin/com/app/tictactoe/controller/TicTacToeController.kt) vs. [TicTacToeController.java](https://github.com/andreaulicna/csob_java_project/blob/99b14ff3518a15edce8255cd25a8df7b917c0c0b/tictactoe_app/src/main/java/com/app/tictactoe/controller/TicTacToeController.java)

<br>

### Type Inference
**`Java`** requires explicitly specifying the type:

```java
Random random = new Random();

private Board board = new Board();

public String startGame(@RequestParam("mode") GameMode mode, Model model) {
    // implementation
    return "game";
}

```
<br>

**`Kotlin`** infers types automatically, reducing verbosity while keeping type safety:

```kotlin
val random = Random()

private var board = Board()

fun startGame(@RequestParam mode: GameMode, model: Model): String {
    // implementation
    return "game"
}
```
<br>

### Null Safety
**`Java`** needs manual null checks (that are prone to human error) to avoid exceptions:

```java
if (userDetails != null) {
    model.addAttribute("user", userDetails);
}
```
<br>

**`Kotlin`** has `?` that checks for `null` and skips the block if `null` (the `let` function executes the block only if `userDetails` is not `null`):

```kotlin
userDetails?.let {
    model.addAttribute("user", it)
}
```

<br>

### No Boilerplate for Getters and Setters:
**`Java`** requires explicit getters and setters:

```java
model.addAttribute("user", userService.findById(user.getId()));

private Board board = new Board();

public Board getBoard() {
    return board;
}

public void setBoard(Board board) {
    this.board = board;
}
```
<br>

**`Kotlin`** automatically provides getters and settors, such `getId()` for the `User` class, allowing direct access to the variable via `user.id`:

```kotlin
model.addAttribute("user", userService.findById(user.id))
```

<br>

### `is` vs. `instanceof`
**`Java`** requires both type-checking and explicit casting:

```java
if (this.currentPlayer instanceof AIPlayer) {
    AIPlayer aiPlayer = (AIPlayer) this.currentPlayer;
    aiPlayer.makeMove(board);
}
```
<br>

**`Kotlin`** automatically automatically allows casting within the same block by using the `is` keyword, simplifiying handling of polymorphism:

```kotlin
if (currentPlayer is AIPlayer) {
    (currentPlayer as AIPlayer).makeMove(board)
}
```

<br>

## Future Development
This project is a demonstrative prototype intended to showcase core concepts in Java and Kotlin, and how these languages can be utilized to implement a functional Tic Tac Toe game. It's important to note that this project is not intended to be a production-ready solution; rather, it's an exercise in demonstrating the 42 philosophy of learning through coding. As such, you will notice certain features are either missing or have been overengineered to intentionally explore various programming techniques.

That being said, here are a few areas that could be further developed in the future:

### Game
- **Remote Play using WebSockets**: Implementing multiplayer functionality using WebSockets would allow players to play the game remotely in real-time.
- **Improved AI (Algorithmic Focus)**: The current AI uses a full version of the Minimax algorithm, making the AI player unbeatable. This could be enhanced or replaced with a more advanced variant to make the AI more human-like. Yet, this improvement is focused more on algorithmic complexity than demonstrating language-specific features, which is why it was not prioritized in this version.

### Database
The current project has a minimal database structure to save user information and match history. That could be expanded to provide user statistics or keep track of match history for different game modes. Yet, this aspect would shift the focus of the current project more toward database design, which is outside the scope of demonstrating `Java`/`Kotlin` coding practices.

### Distribution
Wrapping the entire application into a Docker container would make it easier to distribute and deploy the project. However, this requires some system administration work, which, again, falls outside the scope of this project.

All of the above (and much more!) have been implemented in the original project that inspired this one `ft_transcendence` ([Repository](https://github.com/andreaulicna/42_ft_transcendence/tree/backend_test) | [Kanban Board](https://github.com/users/andreaulicna/projects/13/)).

<br>

## How to Run the Project

### Step 1: Install Prerequisites
1. Java Development Kit (JDK) (Version 17 or higher)
2. Apache Maven (Version 3.8 or higher)
3. PostgreSQL Database

### Step 2: Clone the Repository
```bash
git https://github.com/andreaulicna/csob_java_project.git
cd tictactoe_app
```
### Step 3: Configure Environment Variables
`.env` file in the project’s root directory with this content:
```
DATABASE_URL=jdbc:postgresql://localhost:5432/your_database_name
DATABASE_USERNAME=your_db_username
DATABASE_PASSWORD=your_db_password
```
### Step 4: Create the Database
Create a database matching the url, username and password in the .env file.

### Step 5: Install Dependencies and Run
```
mvn clean install
mvn spring-boot:run
http://localhost:8080
```

<br>

## Sources

https://docs.oracle.com/en/java/
https://docs.spring.io/spring-boot/index.html
https://kotlinlang.org/docs/home.html
https://towardsdatascience.com/game-theory-minimax-f84ee6e4ae6e






