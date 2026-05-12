from flask import Flask, request, jsonify
from flask_cors import CORS
import random

app = Flask(__name__)
CORS(app)

# Different realistic results
results = [
    {"disease": "Leaf Blight", "confidence": 87, "severity": "Medium"},
    {"disease": "Powdery Mildew", "confidence": 79, "severity": "Low"},
    {"disease": "Rust", "confidence": 93, "severity": "High"},
    {"disease": "Bacterial Spot", "confidence": 84, "severity": "Medium"},
    {"disease": "Healthy", "confidence": 96, "severity": "None"}
]

@app.route('/')
def home():
    return "<h1>🌾 Kisan Saathi AI Service Running (Light Mode) ✅</h1>"

@app.route('/api/detect', methods=['POST'])
def detect_disease():
    if 'image' not in request.files:
        return jsonify({"error": "No image provided"}), 400

    # Simple filename based logic
    filename = request.files['image'].filename.lower()
    
    if "healthy" in filename:
        result = results[4]   # Healthy
    else:
        result = random.choice(results)

    return jsonify({
        "disease": result["disease"],
        "confidence": result["confidence"],
        "severity": result["severity"],
        "treatment": ["नीम का तेल स्प्रे करें", "संक्रमित पत्तियां हटाएं", "बायो-फंगीसाइड का उपयोग"],
        "prevention": ["सही सिंचाई", "संतुलित खाद", "फसल चक्र"],
        "message": "Smart Mock AI (Offline)"
    })

if __name__ == '__main__':
    print("🌾 Kisan Saathi Light AI Service Started!")
    print("URL: http://localhost:5001")
    app.run(port=5001, debug=True)