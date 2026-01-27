wordle-game/
├── backend/
│   ├── src/main/java/com/wordle/
│   │   ├── controller/
│   │   │   └── GameController.java
│   │   ├── service/
│   │   │   ├── GameService.java
│   │   │   ├── WordService.java
│   │   │   └── ValidationService.java
│   │   ├── model/
│   │   │   ├── Game.java
│   │   │   ├── Guess.java
│   │   │   ├── GuessResult.java
│   │   │   └── GameState.java
│   │   ├── dto/
│   │   │   ├── GuessRequest.java
│   │   │   ├── GuessResponse.java
│   │   │   └── GameResponse.java
│   │   ├── config/
│   │   │   └── AppConfig.java
│   │   └── exception/
│   │       └── GameException.java
│   ├── src/main/resources/
│   │   └── application.properties
│   └── pom.xml
├── frontend/
│   ├── index.html
│   ├── css/
│   │   └── styles.css
│   ├── js/
│   │   ├── app.js
│   │   ├── gameBoard.js
│   │   ├── keyboard.js
│   │   └── apiService.js
│   └── assets/
│       └── icons/
└── README.md
