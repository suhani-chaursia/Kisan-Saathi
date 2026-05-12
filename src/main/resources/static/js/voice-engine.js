async function processCommand(transcript) {
    const loadingId = showLoading();
    
    try {
        let reply = "समझ गया भाई।";
        const lowerText = transcript.toLowerCase();

        console.log("🎤 User said:", transcript);

        if (!navigator.onLine) {
            if (lowerText.includes("मौसम") || lowerText.includes("बारिश") || lowerText.includes("तापमान")) {
                reply = "आपके इलाके में मौसम ठीक है। बारिश की कोई संभावना नहीं दिख रही।";
            } 
            else if (lowerText.includes("बोना") || lowerText.includes("बीज") || lowerText.includes("बुआई")) {
                reply = "गेहूं बोने का अच्छा समय नवंबर का पहला सप्ताह है।";
                navigateTo("sowing.html");
            } 
            else if (lowerText.includes("मुनाफा") || lowerText.includes("कमाई")) {
                reply = "2 एकड़ में औसतन 20 से 25 हजार रुपये मुनाफ़ा हो सकता है।";
                navigateTo("profit.html");
            } 
            else if (lowerText.includes("बीमारी") || lowerText.includes("रोग") || lowerText.includes("कीड़ा") || 
                     lowerText.includes("दाग") || lowerText.includes("फंगस") || lowerText.includes("पीला")) {
                
                reply = "फसल स्वास्थ्य जाँच खोल रहा हूँ और AI से तुरंत जाँच कर रहा हूँ...";
                // Important: Sirf ek baar navigate + auto=true
                setTimeout(() => {
                    window.location.href = "crop-health.html?auto=true";
                }, 800);
            } 
            else {
                reply = "मैं ऑफलाइन हूँ। बेसिक जानकारी दे सकता हूँ। बोआई, मौसम या बीमारी के बारे में बताइए।";
            }
        } 
        else {
            // Online Mode
            const result = await ApiService.processVoiceCommand(transcript);
            reply = result.reply || "समझ गया भाई।";

            if (lowerText.includes("बीमारी") || lowerText.includes("रोग") || lowerText.includes("दाग") || 
                lowerText.includes("फंगस") || lowerText.includes("पीला") || lowerText.includes("कीड़ा")) {
                
                reply = "समझ गया! फसल स्वास्थ्य जाँच खोल रहा हूँ और AI जाँच शुरू कर रहा हूँ...";
                setTimeout(() => {
                    window.location.href = "crop-health.html?auto=true";
                }, 800);
            }
        }

        // Reply speak aur message
        addMessage(reply, 'bot');
        SpeechService.speak(reply);

        // handleVoiceNavigation ko avoid kar rahe hain double navigation se
        // handleVoiceNavigation(transcript);   ← is line ko comment out kar do

    } catch (e) {
        console.error(e);
        const fallback = "कुछ समस्या आई है। फिर से बोलिए।";
        addMessage(fallback, 'bot');
        SpeechService.speak(fallback);
    }

    removeLoading(loadingId);
}