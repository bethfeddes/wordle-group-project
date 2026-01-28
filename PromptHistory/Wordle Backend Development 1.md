Input: Working on backend game logic. Normalize all user input to uppercase. Only allow the Roman Alphabet to be typed in. Do not take numbers or symbols as input. Do not allow the user to enter an incomplete guess (less than 5 letters). Handle/reject if the user tries to enter more than 5 letters in one guess. Allow the user to guess the same word in the same run.
Only allow answers that are real words in the English language (using the vault used to select the correct answer). If user attempts to enter an invalid word, display a message saying the entry is invalid. Do not take it as a guess.
Allow the user to play multiple times. Each run will have a new random word chosen as the correct answer. 

AI Response: (showed code being uploaded)
  Input Processing Flow
  Receive Input: Raw user input from frontend
  Normalize: Convert to uppercase
  Alphabet Check: Verify Roman alphabet only
  Length Check: Ensure exactly 5 characters
  Word Validation: Check against word vault
  Game Processing: If valid, process as guess
  Error Response: Return specific error messages for invalid inputs
  The backend game logic design addresses all requirements:

  Input Normalization: Uppercase conversion and Roman alphabet validation
  Length Validation: Exactly 5 letters required
  Word Validation: Against the English word vault
  Error Handling: Specific error messages for invalid inputs
  Multiple Games: Session management for repeated play
  Same Word Guessing: No restrictions on duplicate guesses
  The design follows object-oriented principles with clear separation of concerns and comprehensive validation logic.

Prompt: Can this be done without Maven?

AI Response: Yes, this can be done without Maven. I'll create a plain Java implementation using only standard libraries and a simple
  (showed blocks of new code/removal of code)
  Yes, the backend can be implemented without Maven. I've created a complete plain Java implementation using only standard library components:

  Key Changes Made:

  Removed Spring Dependencies: All @Service, @Autowired, and Spring imports removed
  Manual Dependency Injection: Services now use constructor injection
  Built-in HTTP Server: Uses Java's com.sun.net.httpserver.HttpServer
  Custom JSON Serialization: Manual JSON parsing and generation
  Simplified Architecture: Same object-oriented design without framework overhead
  New Main Components:
  
  WordleHttpServer: Main server class with HTTP handlers
  JsonUtil: Manual JSON serialization for API responses
  Updated Services: Plain Java classes with constructor injection

Removed now irrelevant files.
