package com.wordle.model;

import java.util.ArrayList;
import java.util.List;

public class WordleGame {
    private String gameId;
    private String targetWord;
    private List<String> guesses;
    private List<List<Character>> feedback;
    private boolean gameOver;
    private boolean won;
    private int maxGuesses;
    private int wordLength;

    public WordleGame(String gameId, String targetWord) {
        this.gameId = gameId;
        this.targetWord = targetWord;
        this.guesses = new ArrayList<>();
        this.feedback = new ArrayList<>();
        this.gameOver = false;
        this.won = false;
        this.maxGuesses = 6;
        this.wordLength = 5;
    }

    public void submitGuess(String guess) {
        if (gameOver || guesses.size() >= maxGuesses) {
            return;
        }

        guesses.add(guess);
        List<Character> guessFeedback = calculateFeedback(guess);
        feedback.add(guessFeedback);

        if (guess.equals(targetWord)) {
            gameOver = true;
            won = true;
        } else if (guesses.size() >= maxGuesses) {
            gameOver = true;
        }
    }

    private List<Character> calculateFeedback(String guess) {
        List<Character> feedback = new ArrayList<>();
        char[] targetChars = targetWord.toCharArray();
        char[] guessChars = guess.toCharArray();
        boolean[] targetUsed = new boolean[targetChars.length];

        // First pass: mark correct positions
        for (int i = 0; i < guessChars.length; i++) {
            if (guessChars[i] == targetChars[i]) {
                feedback.add('G'); // Green - correct position
                targetUsed[i] = true;
            } else {
                feedback.add('X'); // Will be updated in second pass
            }
        }

        // Second pass: mark present but wrong position
        for (int i = 0; i < guessChars.length; i++) {
            if (feedback.get(i) == 'X') {
                boolean found = false;
                for (int j = 0; j < targetChars.length; j++) {
                    if (!targetUsed[j] && guessChars[i] == targetChars[j]) {
                        feedback.set(i, 'Y'); // Yellow - present but wrong position
                        targetUsed[j] = true;
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    feedback.set(i, 'B'); // Black - not present
                }
            }
        }

        return feedback;
    }

    public String toJson() {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"gameId\":\"").append(gameId).append("\",");
        json.append("\"guesses\":[");
        for (int i = 0; i < guesses.size(); i++) {
            if (i > 0) json.append(",");
            json.append("\"").append(guesses.get(i)).append("\"");
        }
        json.append("],");
        json.append("\"feedback\":[");
        for (int i = 0; i < feedback.size(); i++) {
            if (i > 0) json.append(",");
            json.append("\"");
            for (char c : feedback.get(i)) {
                json.append(c);
            }
            json.append("\"");
        }
        json.append("],");
        json.append("\"gameOver\":").append(gameOver).append(",");
        json.append("\"won\":").append(won).append(",");
        json.append("\"remainingGuesses\":").append(maxGuesses - guesses.size());
        json.append("}");
        return json.toString();
    }

    // Getters
    public String getGameId() { return gameId; }
    public String getTargetWord() { return targetWord; }
    public List<String> getGuesses() { return guesses; }
    public List<List<Character>> getFeedback() { return feedback; }
    public boolean isGameOver() { return gameOver; }
    public boolean isWon() { return won; }
    public int getMaxGuesses() { return maxGuesses; }
    public int getWordLength() { return wordLength; }
}
