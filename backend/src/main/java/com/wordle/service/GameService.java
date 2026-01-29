package com.wordle.service;

import com.wordle.dto.GuessResponse;
import com.wordle.dto.ValidationResult;
import com.wordle.exception.GameException;
import com.wordle.model.Game;
import com.wordle.model.LetterResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class GameService {

    private WordService wordService;
    private ValidationService validationService;
    private Map<String, Game> activeGames = new ConcurrentHashMap<>();

    public GameService() {
        this.wordService = new WordService();
        this.validationService = new ValidationService(wordService);
    }

    public Game createNewGame() {
        String gameId = UUID.randomUUID().toString();
        String targetWord = wordService.getRandomWord();
        Game game = new Game(gameId, targetWord);
        activeGames.put(gameId, game);
        return game;
    }

    public GuessResponse submitGuess(String gameId, String userInput) {
        Game game = activeGames.get(gameId);
        if (game == null) {
            return GuessResponse.invalid("Game session not found");
        }

        if (!game.isGameActive()) {
            return GuessResponse.invalid("Game is already finished");
        }

        // Validate input
        ValidationResult validation = validationService.validateInput(userInput);
        if (!validation.isValid()) {
            return GuessResponse.invalid(validation.getErrorMessage());
        }

        String guess = validation.getNormalizedInput();
        List<LetterResult> letterResults = evaluateGuess(guess, game.getTargetWord());

        game.addGuess(guess, letterResults);

        return GuessResponse.valid(letterResults, game.getGameState(), game.getRemainingAttempts());
    }

    private List<LetterResult> evaluateGuess(String guess, String targetWord) {
        List<LetterResult> results = new ArrayList<>();
        char[] targetChars = targetWord.toCharArray();
        char[] guessChars = guess.toCharArray();
        boolean[] targetUsed = new boolean[5];

        // First pass: mark correct positions
        for (int i = 0; i < 5; i++) {
            if (guessChars[i] == targetChars[i]) {
                results.add(new LetterResult(guessChars[i], LetterResult.LetterStatus.CORRECT));
                targetUsed[i] = true;
            } else {
                results.add(null); // Will be filled in second pass
            }
        }

        // Second pass: mark present but wrong position
        for (int i = 0; i < 5; i++) {
            if (results.get(i) == null) {
                boolean found = false;
                for (int j = 0; j < 5; j++) {
                    if (!targetUsed[j] && guessChars[i] == targetChars[j]) {
                        results.set(i, new LetterResult(guessChars[i], LetterResult.LetterStatus.PRESENT));
                        targetUsed[j] = true;
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    results.set(i, new LetterResult(guessChars[i], LetterResult.LetterStatus.ABSENT));
                }
            }
        }

        return results;
    }

    public Game getGame(String gameId) {
        Game game = activeGames.get(gameId);
        if (game == null) {
            throw new GameException(GameException.GameErrorCode.GAME_NOT_FOUND);
        }
        return game;
    }

    public Map<String, Game> getActiveGames() {
        return new ConcurrentHashMap<>(activeGames);
    }

    public void removeGame(String gameId) {
        activeGames.remove(gameId);
    }
}
