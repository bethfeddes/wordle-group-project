package com.wordle.util;

import com.wordle.dto.*;
import com.wordle.model.*;

import java.util.List;

public class JsonUtil {

    public static String toJson(Object obj) {
        if (obj == null)
            return "null";

        if (obj instanceof String) {
            return "\"" + escapeJson((String) obj) + "\"";
        }

        if (obj instanceof Number || obj instanceof Boolean) {
            return obj.toString();
        }

        if (obj instanceof ValidationResult) {
            ValidationResult vr = (ValidationResult) obj;
            return String.format("{\"valid\":%s,\"errorMessage\":%s,\"normalizedInput\":%s}",
                    vr.isValid(),
                    vr.getErrorMessage() != null ? toJson(vr.getErrorMessage()) : "null",
                    vr.getNormalizedInput() != null ? toJson(vr.getNormalizedInput()) : "null");
        }

        if (obj instanceof GuessResponse) {
            GuessResponse gr = (GuessResponse) obj;
            return String.format(
                    "{\"success\":%s,\"message\":%s,\"letterResults\":%s,\"gameState\":%s,\"remainingAttempts\":%d}",
                    gr.isSuccess(),
                    toJson(gr.getMessage()),
                    toJson(gr.getLetterResults()),
                    toJson(gr.getGameState().toString()),
                    gr.getRemainingAttempts());
        }

        if (obj instanceof GuessRequest) {
            GuessRequest gr = (GuessRequest) obj;
            return String.format("{\"guess\":%s}", toJson(gr.getGuess()));
        }

        if (obj instanceof Game) {
            Game game = (Game) obj;
            return String.format(
                    "{\"gameId\":%s,\"targetWord\":\"HIDDEN\",\"guesses\":%s,\"gameState\":%s,\"remainingAttempts\":%d}",
                    toJson(game.getGameId()),
                    toJson(game.getGuesses()),
                    toJson(game.getGameState().toString()),
                    game.getRemainingAttempts());
        }

        if (obj instanceof GameState) {
            return "\"" + obj.toString() + "\"";
        }

        if (obj instanceof List) {
            StringBuilder sb = new StringBuilder("[");
            List<?> list = (List<?>) obj;
            for (int i = 0; i < list.size(); i++) {
                if (i > 0)
                    sb.append(",");
                sb.append(toJson(list.get(i)));
            }
            sb.append("]");
            return sb.toString();
        }

        if (obj instanceof LetterResult) {
            LetterResult lr = (LetterResult) obj;
            return String.format("{\"letter\":\"%c\",\"status\":\"%s\"}", lr.getLetter(), lr.getStatus());
        }

        return "\"" + obj.toString() + "\"";
    }

    public static GuessRequest parseGuessRequest(String json) {
        json = json.trim();
        if (json.startsWith("{") && json.endsWith("}")) {
            json = json.substring(1, json.length() - 1).trim();
            String[] parts = json.split(":");
            if (parts.length >= 2) {
                String guess = parts[1].trim();
                if (guess.startsWith("\"") && guess.endsWith("\"")) {
                    guess = guess.substring(1, guess.length() - 1);
                }
                return new GuessRequest(guess);
            }
        }
        return new GuessRequest("");
    }

    private static String escapeJson(String str) {
        if (str == null)
            return "";
        return str.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
