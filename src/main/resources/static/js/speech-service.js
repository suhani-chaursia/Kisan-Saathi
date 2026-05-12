// js/speech-service.js
const SpeechService = {
    speak(text) {
        if ('speechSynthesis' in window) {
            const utterance = new SpeechSynthesisUtterance(text);
            utterance.lang = 'hi-IN';
            utterance.rate = 0.92;      // Slow and clear for villagers
            utterance.pitch = 1.05;

            // Prefer Hindi voice
            const voices = speechSynthesis.getVoices();
            const hindiVoice = voices.find(voice => 
                voice.lang.includes('hi') || 
                voice.name.toLowerCase().includes('hindi')
            );
            if (hindiVoice) utterance.voice = hindiVoice;

            speechSynthesis.speak(utterance);
        }
    }
};