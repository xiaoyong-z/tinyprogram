from tensorflow import keras
model = keras.models.load_model("epochs_10.h5")
print(model.summary())