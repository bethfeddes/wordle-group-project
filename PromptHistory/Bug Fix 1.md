Input: Whenever I guess a word not in the vault, it says: Cannot connect to the game server. Please make sure the backend is running on localhost:8080. Produce a more accurate error message for a word outside the vault.

Went back and forth with the AI, fixes weren't working. AI had me check the browser console for errors. After feeding it these, it successfully fixed the program displaying the wrong phrase for an invalid word.

AI Response that finally fixed the bug: Perfect! The issue is that when a guess is invalid, the GuessResponse has a null gameState, but the JSON utility is trying to call toString() on it. Let me fix the

  JsonUtil.java
  Example code
  Now restart the backend server: example code
  Now you should see the proper error message "Not a valid English word" instead of the generic connection error!
  The issue was that invalid guesses return a GuessResponse with null gameState, but the JSON utility was trying to call toString() on null, causing a server crash.
