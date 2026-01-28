class ApiService {
    constructor() {
        this.baseUrl = 'http://localhost:8080/api';
    }

    async createNewGame() {
        try {
            const response = await fetch(`${this.baseUrl}/game/new`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                crossDomain: true
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const game = await response.json();
            return game;
        } catch (error) {
            console.error('Error creating new game:', error);
            throw error;
        }
    }

    async getGameState(gameId) {
        try {
            const response = await fetch(`${this.baseUrl}/game/${gameId}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
                crossDomain: true
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const game = await response.json();
            return game;
        } catch (error) {
            console.error('Error getting game state:', error);
            throw error;
        }
    }

    async submitGuess(gameId, guess) {
        try {
            const response = await fetch(`${this.baseUrl}/game/${gameId}/guess`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    guess: guess.toUpperCase()
                }),
                crossDomain: true
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const guessResponse = await response.json();
            return guessResponse;
        } catch (error) {
            console.error('Error submitting guess:', error);
            throw error;
        }
    }

    // Helper method to handle API errors consistently
    handleApiError(error, defaultMessage = 'An error occurred') {
        console.error(error);
        
        if (error.message.includes('Failed to fetch')) {
            return 'Cannot connect to the game server. Please make sure the backend is running on localhost:8080.';
        }
        
        if (error.message.includes('HTTP error')) {
            return 'Server error. Please try again.';
        }
        
        return defaultMessage;
    }
}

// Export for use in other modules
window.ApiService = ApiService;
