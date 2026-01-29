package com.wordle.exception;

public class GameException extends RuntimeException {
    private GameErrorCode errorCode;

    public GameException(GameErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public GameException(GameErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public GameErrorCode getErrorCode() {
        return errorCode;
    }

    public enum GameErrorCode {
        INVALID_INPUT("Invalid input format"),
        INVALID_WORD("Not a valid English word"),
        GAME_NOT_FOUND("Game session not found"),
        GAME_COMPLETED("Game is already finished"),
        INVALID_LENGTH("Word must be exactly 5 letters"),
        INVALID_CHARACTERS("Only Roman alphabet letters allowed");

        private final String message;

        GameErrorCode(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
