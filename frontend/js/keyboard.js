class Keyboard {
    constructor() {
        this.keyboard = document.getElementById('keyboard');
        this.keyButtons = {};
        this.initializeKeyboard();
        this.setupEventListeners();
    }

    initializeKeyboard() {
        const buttons = this.keyboard.querySelectorAll('button[data-key]');
        buttons.forEach(button => {
            const key = button.getAttribute('data-key');
            this.keyButtons[key] = button;
        });
    }

    setupEventListeners() {
        // Virtual keyboard clicks
        Object.keys(this.keyButtons).forEach(key => {
            this.keyButtons[key].addEventListener('click', () => {
                this.handleKeyPress(key);
            });
        });

        // Physical keyboard input
        document.addEventListener('keydown', (event) => {
            const key = event.key.toLowerCase();
            
            if (key === 'enter') {
                this.handleKeyPress('enter');
            } else if (key === 'backspace') {
                this.handleKeyPress('backspace');
            } else if (key.length === 1 && key >= 'a' && key <= 'z') {
                this.handleKeyPress(key);
            }
        });
    }

    handleKeyPress(key) {
        // Trigger a custom event that the main app can listen to
        const event = new CustomEvent('keyPress', { detail: { key } });
        document.dispatchEvent(event);
    }

    updateKey(letter, status) {
        const key = letter.toLowerCase();
        if (this.keyButtons[key]) {
            const button = this.keyButtons[key];
            
            // Only update if the new status is "better" than the current one
            const currentStatus = this.getKeyStatus(button);
            if (this.shouldUpdateStatus(currentStatus, status)) {
                // Remove existing status classes
                button.classList.remove('correct', 'present', 'absent');
                
                // Add new status class
                switch (status) {
                    case 'CORRECT':
                        button.classList.add('correct');
                        break;
                    case 'PRESENT':
                        button.classList.add('present');
                        break;
                    case 'ABSENT':
                        button.classList.add('absent');
                        break;
                }
            }
        }
    }

    getKeyStatus(button) {
        if (button.classList.contains('correct')) return 'CORRECT';
        if (button.classList.contains('present')) return 'PRESENT';
        if (button.classList.contains('absent')) return 'ABSENT';
        return null;
    }

    shouldUpdateStatus(currentStatus, newStatus) {
        // Status priority: CORRECT > PRESENT > ABSENT > null
        const statusPriority = {
            'CORRECT': 3,
            'PRESENT': 2,
            'ABSENT': 1,
            null: 0
        };

        return statusPriority[newStatus] > statusPriority[currentStatus];
    }

    updateKeyboard(letterResults) {
        letterResults.forEach(letterResult => {
            this.updateKey(letterResult.letter, letterResult.status);
        });
    }

    reset() {
        Object.values(this.keyButtons).forEach(button => {
            button.classList.remove('correct', 'present', 'absent');
        });
    }

    // Add visual feedback for key press
    animateKeyPress(key) {
        if (this.keyButtons[key]) {
            const button = this.keyButtons[key];
            button.style.transform = 'scale(0.95)';
            setTimeout(() => {
                button.style.transform = '';
            }, 100);
        }
    }
}

// Export for use in other modules
window.Keyboard = Keyboard;
