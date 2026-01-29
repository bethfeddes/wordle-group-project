package com.wordle.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private String gameId;
    private String targetWord;
    private List<String> guesses;
    private List<List<LetterResult>> guessResults;
    private GameState gameState;
    private int maxAttempts;
    private LocalDateTime createdAt;

    public Game(String gameId, String targetWord) {
        this.gameId = gameId;
        this.targetWord = targetWord;
        this.guesses = new ArrayList<>();
        this.guessResults = new ArrayList<>();
        this.gameState = GameState.ACTIVE;
        this.maxAttempts = 6;
        this.createdAt = LocalDateTime.now();
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getTargetWord() {
        return targetWord;
    }

    public void setTargetWord(String targetWord) {
        this.targetWord = targetWord;
    }

    public List<String> getGuesses() {
        return guesses;
    }

    public void setGuesses(List<String> guesses) {
        this.guesses = guesses;
    }

    public List<List<LetterResult>> getGuessResults() {
        return guessResults;
    }

    public void setGuessResults(List<List<LetterResult>> guessResults) {
        this.guessResults = guessResults;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getRemainingAttempts() {
        return maxAttempts - guesses.size();
    }

    public boolean isGameActive() {
        return gameState == GameState.ACTIVE;
    }

    public void addGuess(String guess, List<LetterResult> result) {
        guesses.add(guess);
        guessResults.add(result);
        
        // Update game state
        if (guess.equals(targetWord)) {
            gameState = GameState.WON;
        } else if (getRemainingAttempts() <= 0) {
            gameState = GameState.LOST;
        }
    }
}
