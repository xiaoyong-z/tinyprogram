from tensorflow import keras
model = keras.models.load_model("epochs_7.h5")
print(model.summary())