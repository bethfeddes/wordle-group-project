## Input
when winning the game, the wrong amount of tries is displayed. i won in 3 guesses but the message was "congratulations! you guessed the word in 4 tries!", and whenever i win in 6 tries, it says "congratulations! You guessed the word in 1 try!"

## AI Response
I see the issue! The calculation for the number of tries is incorrect. Let me find and fix the win message calculation in the JavaScript:
Found the issue! The calculation 6 - this.gameBoard.currentRow + 1 is wrong. Let me fix app.js
Perfect! ✅ Fixed the win message calculation

The problem was:
Old formula: 6 - this.gameBoard.currentRow + 1
When you win in 3 guesses (currentRow = 3): 6 - 3 + 1 = 4 ❌
When you win in 6 guesses (currentRow = 6): 6 - 6 + 1 = 1 ❌
New formula: this.gameBoard.currentRow
When you win in 3 guesses (currentRow = 3): 3 ✅
When you win in 6 guesses (currentRow = 6): 6 ✅
Now the win message will correctly show:

"Congratulations! You won in 1 try!" (when winning on first guess)
"Congratulations! You won in 3 tries!" (when winning on third guess)
"Congratulations! You won in 6 tries!" (when winning on sixth guess)
The fix applies to both the on-screen message and the modal message.
