package com.wordle.dto;

public class ValidationResult {
    private boolean valid;
    private String errorMessage;
    private String normalizedInput;

    public ValidationResult(boolean valid, String errorMessage, String normalizedInput) {
        this.valid = valid;
        this.errorMessage = errorMessage;
        this.normalizedInput = normalizedInput;
    }

    public static ValidationResult valid(String normalizedInput) {
        return new ValidationResult(true, null, normalizedInput);
    }

    public static ValidationResult invalid(String errorMessage) {
        return new ValidationResult(false, errorMessage, null);
    }

    public boolean isValid() {
        return valid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getNormalizedInput() {
        return normalizedInput;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setNormalizedInput(String normalizedInput) {
        this.normalizedInput = normalizedInput;
    }
}
