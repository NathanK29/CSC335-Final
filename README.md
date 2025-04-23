# Yahtzee - README

## Goal
Create a Yahtzee game that tracks player statistics and allows local play either with friends (multiple players on one machine) or against a computer-controlled opponent.

---

## Design

This project has two main parts:

- **Model** – Handles all the game logic and data. This includes dice rolls, scoring, players, and game stats.
- **View** – The graphical interface players interact with. It displays dice, scores, and buttons. It shows what’s happening in the game.

The view shows the game to the player. When a player does something (like rolling dice or choosing a score), the view tells the model. The model updates the game state, and the view updates what the player sees.

### Separation of Concerns

- UI code is in the `main.view` package.
- Game rules and data are in the `main.model` package and supporting packages.
- The view observes model changes but does not change the model directly.

### Data Structures

- `ArrayList` is used for dice and category lists for fast access and flexibility with small lists.
- `HashMap` in `ScoreCategoryStore` reuses shared objects to save memory (Flyweight pattern).

### Object-Oriented Design

- `Player` is an abstract class extended by `HumanPlayer` and `ComputerPlayer`.
- Computer behavior is implemented using a strategy interface, allowing different difficulty levels.
- Score categories implement a common `ScoreCategory` interface while each category has its own logic.

### Encapsulation

- Class fields are private and accessed through getters, setters, or methods.
- Collections are not exposed directly to prevent unwanted modification or bugs.

### Design Patterns Used

| Pattern   | Where                     | Purpose                                                              |
|----------|---------------------------|----------------------------------------------------------------------|
| Strategy | `strategy` package        | Switch between computer difficulties without changing player code   |
| Flyweight| `ScoreCategoryStore`      | Share category logic between players to reduce memory usage         |
| Observer | `ObservableGameState`, etc.| Automatically update the view when the model changes                |

### Input Validation

- The view checks inputs like dice selection and category choice.
- The model ensures scoring rules are followed (e.g., can’t reuse a category).

### Avoiding Antipatterns

- No God classes – logic is distributed across focused, small classes.
- No primitive obsession – classes like `Dice` are used instead of raw integers.
- Code is organized cleanly in packages, avoiding tight coupling or cycles.

### AI-Generated Code

Only AI-generated code used was for the GUI and our advanced feature.

---

## Testing

Tests are located in the `src/test` folder. They check:

- Dice behavior and randomness  
- Scoring logic for all categories  
- Computer's decision-making at different levels  

---

## Team

- Jaden Gee  
- Kyle Dervin  
- Abe Didan  
- Nathan Kumar

---

## How to Run

```bash
javac *.java
java GameView
