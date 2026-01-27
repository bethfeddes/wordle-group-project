package com.wordle.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.wordle.dto.GuessRequest;
import com.wordle.dto.GuessResponse;
import com.wordle.model.Game;
import com.wordle.service.GameService;
import com.wordle.util.JsonUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class WordleHttpServer {

    private static final int PORT = 8080;
    private GameService gameService;

    public WordleHttpServer() {
        this.gameService = new GameService();
    }

    public void start() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        // CORS handler for OPTIONS requests
        server.createContext("/api/game/new", new NewGameHandler());
        server.createContext("/api/game/", new GameHandler());
        server.createContext("/api/game/guess", new GuessHandler());

        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Wordle Server started on port " + PORT);
    }

    class NewGameHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod()) || "OPTIONS".equals(exchange.getRequestMethod())) {
                handleCORS(exchange);

                if ("POST".equals(exchange.getRequestMethod())) {
                    Game game = gameService.createNewGame();
                    String response = JsonUtil.toJson(game);
                    sendResponse(exchange, 200, response);
                }
            } else {
                sendResponse(exchange, 405, "{\"error\":\"Method not allowed\"}");
            }
        }
    }

    class GameHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            String[] pathParts = path.split("/");

            if (pathParts.length >= 4 && "GET".equals(exchange.getRequestMethod())) {
                handleCORS(exchange);
                String gameId = pathParts[3];
                try {
                    Game game = gameService.getGame(gameId);
                    String response = JsonUtil.toJson(game);
                    sendResponse(exchange, 200, response);
                } catch (Exception e) {
                    sendResponse(exchange, 404, "{\"error\":\"Game not found\"}");
                }
            } else {
                sendResponse(exchange, 405, "{\"error\":\"Method not allowed\"}");
            }
        }
    }

    class GuessHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod()) || "OPTIONS".equals(exchange.getRequestMethod())) {
                handleCORS(exchange);

                if ("POST".equals(exchange.getRequestMethod())) {
                    String path = exchange.getRequestURI().getPath();
                    String[] pathParts = path.split("/");

                    if (pathParts.length >= 4) {
                        String gameId = pathParts[3];
                        String requestBody = readRequestBody(exchange);
                        GuessRequest guessRequest = JsonUtil.parseGuessRequest(requestBody);

                        GuessResponse response = gameService.submitGuess(gameId, guessRequest.getGuess());
                        String jsonResponse = JsonUtil.toJson(response);
                        sendResponse(exchange, 200, jsonResponse);
                    } else {
                        sendResponse(exchange, 400, "{\"error\":\"Invalid game ID\"}");
                    }
                }
            } else {
                sendResponse(exchange, 405, "{\"error\":\"Method not allowed\"}");
            }
        }
    }

    private void handleCORS(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type, Authorization");

        if ("OPTIONS".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(204, -1);
        }
    }

    private String readRequestBody(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public static void main(String[] args) {
        try {
            WordleHttpServer server = new WordleHttpServer();
            server.start();
        } catch (IOException e) {
            System.err.println("Failed to start server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
