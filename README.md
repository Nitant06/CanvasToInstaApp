# Interactive Photo Canvas to Instagram Stories

This is a native Android application built using **Kotlin** and **Jetpack Compose** as a submission for the Cactro Mobile Developer Hiring Test. 

The app allows users to capture photos, annotate them with freehand drawing and stickers, add captions, and share the result directly to Instagram Stories.

## Features

- **Custom Camera Interface:** Built using **CameraX** for high-quality image capture.
- **Interactive Canvas:**
  - **Freehand Drawing:** Users can draw on the photo using a touch gesture canvas.
  - **Draggable Stickers:** Stickers can be added and dragged anywhere on the screen using pointer input gestures.
- **Caption Support:** Users can overlay text captions on the image.
- **Image Processing:** A custom `BitmapHelper` merges the background image, drawing layers, and stickers into a single JPEG file.
- **Direct Sharing:** Utilizes Android `Intents` and `FileProvider` to share the final image directly to Instagram Stories without the system share sheet.

## Tech Stack

- **Language:** Kotlin
- **UI Toolkit:** Jetpack Compose (Material 3)
- **Architecture:** MVVM (Model-View-ViewModel)
- **Camera:** Android CameraX
- **Image Loading:** Coil
- **Navigation:** Jetpack Navigation Compose
- **File Handling:** Android FileProvider

## How to Run

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/YOUR_USERNAME/CanvasToInstaApp.git

2. **Open in Android Studio:**
- Open the project in Android Studio
- Sync Gradle
  
3. **Run on Device**:
- Connect a physical Android device (Recommended) or use an Emulator.
- Note: To test the "Share to Stories" feature, the device must have the Instagram app installed. If not, a Toast message will appear confirming the file generation was successful.
