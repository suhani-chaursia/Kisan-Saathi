// js/api-service.js
const ApiService = {
  async processVoiceCommand(text) {
    try {
      const response = await fetch('/api/voice/process', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ text: text })
      });
      return await response.json();
    } catch (error) {
      console.error("Backend Error:", error);
      return { intent: "unknown", reply: "सर्वर से कनेक्ट नहीं हो पा रहा।" };
    }
  },

  // New: Get Weather using current location
  async getWeatherByLocation() {
    return new Promise((resolve) => {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
          async (position) => {
            const lat = position.coords.latitude;
            const lon = position.coords.longitude;
            
            try {
              const res = await fetch(
                `https://api.open-meteo.com/v1/forecast?latitude=${lat}&longitude=${lon}&current=temperature_2m,precipitation&daily=precipitation_sum,temperature_2m_max&timezone=Asia/Kolkata`
              );
              const data = await res.json();
              
              const temp = Math.round(data.current.temperature_2m);
              const rain = data.daily.precipitation_sum[0] || 0;
              
              resolve({
                temp: temp,
                rain: rain,
                reply: `आपके इलाके में अभी ${temp}°C है। ${rain > 8 ? 'भारी बारिश' : 'हल्की बारिश'} की संभावना है।`
              });
            } catch(e) {
              resolve({ temp: 32, rain: 5, reply: "आपके इलाके में मौसम ठीक है।" });
            }
          },
          () => {
            // Fallback if location permission denied
            resolve({ temp: 32, rain: 5, reply: "रीवा क्षेत्र में मौसम सामान्य है।" });
          }
        );
      } else {
        resolve({ temp: 32, rain: 5, reply: "मौसम जानकारी उपलब्ध नहीं है।" });
      }
    });
  }
};