package com.wordle.dto;

import com.wordle.model.GameState;
import com.wordle.model.LetterResult;

import java.util.List;

public class GuessResponse {
    private boolean success;
    private String message;
    private List<LetterResult> letterResults;
    private GameState gameState;
    private int remainingAttempts;

    public GuessResponse(boolean success, String message, List<LetterResult> letterResults, GameState gameState, int remainingAttempts) {
        this.success = success;
        this.message = message;
        this.letterResults = letterResults;
        this.gameState = gameState;
        this.remainingAttempts = remainingAttempts;
    }

    public static GuessResponse invalid(String message) {
        return new GuessResponse(false, message, null, null, 0);
    }

    public static GuessResponse valid(List<LetterResult> results, GameState state, int attempts) {
        return new GuessResponse(true, "Valid guess", results, state, attempts);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<LetterResult> getLetterResults() {
        return letterResults;
    }

    public GameState getGameState() {
        return gameState;
    }

    public int getRemainingAttempts() {
        return remainingAttempts;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setLetterResults(List<LetterResult> letterResults) {
        this.letterResults = letterResults;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void setRemainingAttempts(int remainingAttempts) {
        this.remainingAttempts = remainingAttempts;
    }
}
