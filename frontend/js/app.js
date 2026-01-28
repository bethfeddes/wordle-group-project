class WordleGame {
    constructor() {
        this.apiService = new ApiService();
        this.gameBoard = new GameBoard();
        this.keyboard = new Keyboard();
        
        this.currentGame = null;
        this.isProcessing = false;
        
        this.initializeElements();
        this.setupEventListeners();
        this.startNewGame();
    }

    initializeElements() {
        this.messageElement = document.getElementById('message');
        this.attemptsElement = document.getElementById('attemptsLeft');
        this.newGameBtn = document.getElementById('newGameBtn');
        this.resetBtn = document.getElementById('resetBtn');
        this.gameOverModal = document.getElementById('gameOverModal');
        this.gameOverTitle = document.getElementById('gameOverTitle');
        this.gameOverMessage = document.getElementById('gameOverMessage');
        this.playAgainBtn = document.getElementById('playAgainBtn');
    }

    setupEventListeners() {
        // Keyboard events
        document.addEventListener('keyPress', (event) => {
            this.handleKeyPress(event.detail.key);
        });

        // Button events
        this.newGameBtn.addEventListener('click', () => this.startNewGame());
        this.resetBtn.addEventListener('click', () => this.resetCurrentGame());
        this.playAgainBtn.addEventListener('click', () => {
            this.hideGameOverModal();
            this.startNewGame();
        });

        // Close modal when clicking outside
        this.gameOverModal.addEventListener('click', (event) => {
            if (event.target === this.gameOverModal) {
                this.hideGameOverModal();
            }
        });
    }

    async startNewGame() {
        try {
            this.showMessage('Creating new game...');
            this.isProcessing = true;

            const game = await this.apiService.createNewGame();
            this.currentGame = game;
            
            this.gameBoard.reset();
            this.keyboard.reset();
            this.updateGameInfo();
            this.showMessage('');
            
            this.isProcessing = false;
        } catch (error) {
            const errorMessage = this.apiService.handleApiError(error, 'Failed to create new game');
            this.showMessage(errorMessage);
            this.isProcessing = false;
        }
    }

    async resetCurrentGame() {
        if (!this.currentGame) {
            this.startNewGame();
            return;
        }

        try {
            this.showMessage('Resetting game...');
            this.isProcessing = true;

            const game = await this.apiService.getGameState(this.currentGame.gameId);
            this.currentGame = game;
            
            this.gameBoard.loadGameState(game);
            this.keyboard.reset();
            
            // Update keyboard with previous guess results
            if (game.guessResults) {
                game.guessResults.forEach(letterResults => {
                    this.keyboard.updateKeyboard(letterResults);
                });
            }
            
            this.updateGameInfo();
            this.showMessage('');
            
            this.isProcessing = false;
        } catch (error) {
            const errorMessage = this.apiService.handleApiError(error, 'Failed to reset game');
            this.showMessage(errorMessage);
            this.isProcessing = false;
        }
    }

    async handleKeyPress(key) {
        if (this.isProcessing || !this.currentGame) return;

        if (key === 'enter') {
            await this.submitGuess();
        } else if (key === 'backspace') {
            this.gameBoard.removeLetter();
        } else if (key >= 'a' && key <= 'z') {
            this.gameBoard.addLetter(key);
            this.keyboard.animateKeyPress(key);
        }
    }

    async submitGuess() {
        if (!this.gameBoard.isCurrentRowFull()) {
            this.showMessage('Please enter a 5-letter word');
            return;
        }

        if (this.gameBoard.isGameOver()) {
            this.showMessage('Game is over. Start a new game.');
            return;
        }

        try {
            this.isProcessing = true;
            this.showMessage('Checking guess...');

            const currentWord = this.gameBoard.getCurrentWord();
            const response = await this.apiService.submitGuess(this.currentGame.gameId, currentWord);

            if (response.success) {
                // Update the game board with the results
                this.gameBoard.updateRow(response.letterResults);
                
                // Update the keyboard
                this.keyboard.updateKeyboard(response.letterResults);
                
                // Update game info
                this.currentGame.gameState = response.gameState;
                this.updateGameInfo();
                
                // Check for game over
                if (response.gameState === 'WON') {
                    await this.handleGameWin();
                } else if (response.gameState === 'LOST') {
                    await this.handleGameLoss();
                } else {
                    this.showMessage('');
                }
            } else {
                this.showMessage(response.message);
            }
        } catch (error) {
            // Check if this is a validation error from the server
            if (error.message && !error.message.includes('Failed to fetch') && !error.message.includes('Network')) {
                // Show the actual server error message
                this.showMessage(error.message);
            } else {
                // Show generic connection error for actual network issues
                const errorMessage = this.apiService.handleApiError(error, 'Failed to submit guess');
                this.showMessage(errorMessage);
            }
        } finally {
            this.isProcessing = false;
        }
    }

    async handleGameWin() {
        // Fetch the updated game state to get the target word
        try {
            const updatedGame = await this.apiService.getGameState(this.currentGame.gameId);
            this.currentGame = updatedGame;
        } catch (error) {
            console.error('Failed to fetch updated game state:', error);
        }
        
        const attempts = 6 - this.gameBoard.currentRow + 1;
        this.showMessage(`🎉 Congratulations! You won in ${attempts} ${attempts === 1 ? 'try' : 'tries'}!`);
        
        this.gameOverTitle.textContent = '🎉 You Won!';
        this.gameOverMessage.textContent = `Congratulations! You guessed the word in ${attempts} ${attempts === 1 ? 'try' : 'tries'}!`;
        this.showGameOverModal();
    }

    async handleGameLoss() {
        // Fetch the updated game state to get the target word
        try {
            const updatedGame = await this.apiService.getGameState(this.currentGame.gameId);
            this.currentGame = updatedGame;
        } catch (error) {
            console.error('Failed to fetch updated game state:', error);
        }
        
        this.showMessage(`😔 Game Over! The word was ${this.currentGame.targetWord}`);
        
        this.gameOverTitle.textContent = '😔 Game Over';
        this.gameOverMessage.textContent = `The word was: ${this.currentGame.targetWord.toUpperCase()}`;
        this.showGameOverModal();
    }

    updateGameInfo() {
        if (this.currentGame) {
            this.attemptsElement.textContent = this.currentGame.remainingAttempts || 6;
        }
    }

    showMessage(message) {
        this.messageElement.textContent = message;
    }

    showGameOverModal() {
        this.gameOverModal.style.display = 'block';
    }

    hideGameOverModal() {
        this.gameOverModal.style.display = 'none';
    }
}

// Initialize the game when the page loads
document.addEventListener('DOMContentLoaded', () => {
    new WordleGame();
});
