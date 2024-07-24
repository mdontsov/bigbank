# bigbank

# Game Solver Spring Boot Application

This Spring Boot application runs a game that involves:

1. Starting a new game
2. Fetching a list of ads (quests)
3. Picking the best ads to solve and solving them
4. Optionally buying items to heal
5. Repeating the process from step two until lives run out

## Getting Started

### Prerequisites

- Java 17 or later
- Gradle 

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/mdontsov/bigbank.git
2. Open the cloned project in IntelliJ and, build & run

## How It Works

### Components

- GameClient: Handles communication with the game server, including starting a new game, fetching ads, and solving
  quests.

- QuestFilter: Filters the quests to select only the most solvable ones.

- QuestAnalyzer: Analyzes the quests to determine the best course of action.

- ShopClient: Handles buying items to heal.

- QuestClient: Solves the quests using Retry if server response fails

- StatsClient: Updates the game stats

- GameProcessor: Main component that orchestrates the game flow.

### Game Flow

- Start New Game: The application starts by requesting the server to start a new game.
- Fetch Ads: The application fetches a list of ads (quests) from the server.
- Pick and Solve Ads: The application filters the ads to find the most solvable ones and solves them. The high score and
  lives are updated accordingly.
- Buy Items: Optionally, the application buys items to heal if needed.
- Repeat: The process repeats from step 2 until the player's lives run out or the desired high score is reached.
