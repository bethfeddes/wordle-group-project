# Wordle Backend - Plain Java Implementation

This is a plain Java implementation of the Wordle game backend without Maven or Spring Boot dependencies.

## How to Run

1. Compile the Java files:
```bash
cd backend/src/main/java
javac -cp . com/wordle/server/WordleHttpServer.java
```

2. Run the server:
```bash
java -cp . com.wordle.server.WordleHttpServer
```

The server will start on port 8080.

## API Endpoints

- `POST /api/game/new` - Create a new game
- `GET /api/game/{gameId}` - Get game state
- `POST /api/game/{gameId}/guess` - Submit a guess

## Features

- Input validation (5 letters, Roman alphabet only)
- Word validation against 500+ English words
- Game state management
- Letter evaluation (correct/present/absent)
- CORS support for frontend integration
- JSON serialization without external libraries

## Architecture

- **WordService**: Manages word vault and random selection
- **ValidationService**: Handles input validation and normalization
- **GameService**: Core game logic and session management
- **WordleHttpServer**: Simple HTTP server using Java's built-in HttpServer
- **JsonUtil**: Manual JSON serialization for API responses

The implementation follows object-oriented principles with clear separation of concerns and no external dependencies beyond Java standard library.
