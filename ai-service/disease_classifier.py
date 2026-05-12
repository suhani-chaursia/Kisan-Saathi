from PIL import Image
import numpy as np
import tensorflow as tf

model = None

def load_model():
    global model
    if model is None:
        model = tf.lite.Interpreter(model_path="plant_disease_model.tflite")
        model.allocate_tensors()
    return model

def predict_disease(image_file):
    # Simple mock for now (real model call later)
    return {
        "disease": "Leaf Blight",
        "confidence": 87,
        "severity": "Medium",
         "treatment": ["नीम तेल स्प्रे", "संक्रमित पत्ती हटाएं"],
        "prevention": ["सही सिंचाई", "फसल चक्र अपनाएं"]
    }