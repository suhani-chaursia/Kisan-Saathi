let currentLang = 'hi';

   function toggleLanguage() {
     currentLang = currentLang === 'hi' ? 'en' : 'hi';
     
     const btn = document.getElementById('lang-btn');
     btn.textContent = currentLang === 'hi' ? 'हिंदी' : 'ENGLISH';

     // Update current page texts
     document.getElementById('greeting').textContent = 
       currentLang === 'hi' ? 'नमस्ते किसान जी 👋' : 'Hello Farmer Ji 👋';

     document.getElementById('weather-title').textContent = 
       currentLang === 'hi' ? '🌦 आज का मौसम' : '🌦 Today\'s Weather';

     document.getElementById('quick-title').textContent = 
       currentLang === 'hi' ? 'क्या करना चाहते हो?' : 'What do you want to do?';

     document.getElementById('alert').textContent = 
       currentLang === 'hi' ? '⚠️ तेज बारिश हो सकती है' : '⚠️ Heavy Rain Possible';

     document.getElementById('sowing-text').textContent = currentLang === 'hi' ? 'बोआई' : 'Sowing';
     document.getElementById('crop-text').textContent = currentLang === 'hi' ? 'फसल बीमारी' : 'Crop Disease';
     document.getElementById('weather-text').textContent = currentLang === 'hi' ? 'मौसम अलर्ट' : 'Weather Alert';
     document.getElementById('profit-text').textContent = currentLang === 'hi' ? 'मुनाफा बताओ' : 'Profit';
     document.getElementById('voice-text').textContent = currentLang === 'hi' ? 'बोलकर पूछो' : 'Voice Assistant';
	 
	 
	  window.toggleLanguage=toggleLanguage;
	 window.onload = loadLanguage;

	 
	 
   }