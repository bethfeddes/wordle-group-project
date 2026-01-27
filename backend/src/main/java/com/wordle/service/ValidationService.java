package com.wordle.service;

import com.wordle.dto.ValidationResult;

import java.util.Set;

public class ValidationService {

    private WordService wordService;

    public ValidationService(WordService wordService) {
        this.wordService = wordService;
    }

    public ValidationResult validateInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            return ValidationResult.invalid("Input cannot be empty");
        }

        // Normalize to uppercase
        String normalized = input.toUpperCase().trim();

        // Check for Roman alphabet only
        if (!isRomanAlphabet(normalized)) {
            return ValidationResult.invalid("Only Roman alphabet letters allowed");
        }

        // Validate length (exactly 5 letters)
        if (!isValidLength(normalized)) {
            return ValidationResult.invalid("Word must be exactly 5 letters");
        }

        // Check against word vault
        if (!isValidWord(normalized)) {
            return ValidationResult.invalid("Not a valid English word");
        }

        return ValidationResult.valid(normalized);
    }

    private boolean isRomanAlphabet(String input) {
        return input.matches("[A-Z]+");
    }

    private boolean isValidLength(String input) {
        return input.length() == 5;
    }

    private boolean isValidWord(String word) {
        return wordService.containsWord(word);
    }

    public boolean isValidWord(String word, Set<String> wordVault) {
        return wordVault.contains(word.toUpperCase());
    }
}
