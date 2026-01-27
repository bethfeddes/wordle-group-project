package com.wordle.model;

public class LetterResult {
    private char letter;
    private LetterStatus status;

    public LetterResult(char letter, LetterStatus status) {
        this.letter = letter;
        this.status = status;
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public LetterStatus getStatus() {
        return status;
    }

    public void setStatus(LetterStatus status) {
        this.status = status;
    }

    public enum LetterStatus {
        CORRECT,
        PRESENT,
        ABSENT
    }
}
