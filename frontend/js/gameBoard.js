class GameBoard {
    constructor() {
        this.gameBoard = document.getElementById('gameBoard');
        this.currentRow = 0;
        this.currentCol = 0;
        this.maxRows = 6;
        this.maxCols = 5;
        this.board = [];
        
        this.initializeBoard();
    }

    initializeBoard() {
        this.gameBoard.innerHTML = '';
        this.board = [];
        
        for (let row = 0; row < this.maxRows; row++) {
            const rowElement = document.createElement('div');
            rowElement.className = 'row';
            rowElement.id = `row-${row}`;
            
            const boardRow = [];
            
            for (let col = 0; col < this.maxCols; col++) {
                const tile = document.createElement('div');
                tile.className = 'tile';
                tile.id = `tile-${row}-${col}`;
                rowElement.appendChild(tile);
                boardRow.push({ element: tile, letter: '', status: null });
            }
            
            this.gameBoard.appendChild(rowElement);
            this.board.push(boardRow);
        }
    }

    addLetter(letter) {
        if (this.currentCol < this.maxCols && this.currentRow < this.maxRows) {
            const tile = this.board[this.currentRow][this.currentCol];
            tile.letter = letter.toUpperCase();
            tile.element.textContent = letter.toUpperCase();
            tile.element.classList.add('filled');
            this.currentCol++;
            return true;
        }
        return false;
    }

    removeLetter() {
        if (this.currentCol > 0) {
            this.currentCol--;
            const tile = this.board[this.currentRow][this.currentCol];
            tile.letter = '';
            tile.element.textContent = '';
            tile.element.classList.remove('filled');
            return true;
        }
        return false;
    }

    getCurrentWord() {
        let word = '';
        for (let col = 0; col < this.currentCol; col++) {
            word += this.board[this.currentRow][col].letter;
        }
        return word;
    }

    isCurrentRowFull() {
        return this.currentCol === this.maxCols;
    }

    updateRow(letterResults) {
        if (letterResults.length !== this.maxCols) {
            console.error('Invalid letter results length');
            return;
        }

        // Animate the tiles flipping
        for (let col = 0; col < this.maxCols; col++) {
            const tile = this.board[this.currentRow][col];
            const letterResult = letterResults[col];
            
            // Add flip animation
            tile.element.style.animation = 'flip 0.5s';
            
            setTimeout(() => {
                // Update the tile based on the letter result
                tile.status = letterResult.status;
                
                switch (letterResult.status) {
                    case 'CORRECT':
                        tile.element.classList.add('correct');
                        break;
                    case 'PRESENT':
                        tile.element.classList.add('present');
                        break;
                    case 'ABSENT':
                        tile.element.classList.add('absent');
                        break;
                }
                
                tile.element.style.animation = '';
            }, 250 + col * 100); // Stagger the animations
        }

        // Move to the next row
        this.currentRow++;
        this.currentCol = 0;
    }

    reset() {
        this.currentRow = 0;
        this.currentCol = 0;
        this.initializeBoard();
    }

    getBoardState() {
        const state = [];
        for (let row = 0; row < this.currentRow; row++) {
            const rowData = [];
            for (let col = 0; col < this.maxCols; col++) {
                const tile = this.board[row][col];
                rowData.push({
                    letter: tile.letter,
                    status: tile.status
                });
            }
            state.push(rowData);
        }
        return state;
    }

    loadGameState(game) {
        this.reset();
        
        // Load previous guesses and their results
        if (game.guesses && game.guessResults) {
            for (let i = 0; i < game.guesses.length; i++) {
                const guess = game.guesses[i];
                const results = game.guessResults[i];
                
                // Add letters to the current row
                for (let j = 0; j < guess.length; j++) {
                    this.addLetter(guess[j]);
                }
                
                // Update the row with results
                if (results) {
                    this.updateRow(results);
                }
            }
        }
    }

    isGameOver() {
        return this.currentRow >= this.maxRows;
    }
}

// Export for use in other modules
window.GameBoard = GameBoard;
