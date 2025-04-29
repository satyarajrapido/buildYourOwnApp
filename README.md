# PR Review

You’ve built an interesting game application that works well. However, there is significant scope for code improvements. If you can accommodate the suggested changes, we can consider you for further rounds:
- Follow the Clean Architecture pattern properly.
- Ensure the ViewModel interacts only with UseCases.
- Introduce the Repository pattern.
- Ensure UseCases interact only with the Repository.
- Ensure the Repository interacts only with DataSources.



# Guess - Multiplayer Drawing & Guessing Game

APK : https://drive.google.com/file/d/1rftNkSC9nt3oGRk3gR41hfnjjtZpzXpZ/view?usp=sharing

Working Video : https://drive.google.com/file/d/1Vnm7b9QpvvbyaVK6nZsew5B42g8EKFdO/view?usp=sharing

## About the App

Guess is a multiplayer drawing and guessing game inspired by Skribbl. Players can join a room where one player draws, and others try to guess the word based on the drawing. The first player to guess correctly wins the round, making it a fun and engaging social game.

## Features

🎨 **Live Drawing**: One player draws while others guess in real time.

💬 **Real-Time Chat**: Integrated chat for communication.

🏆 **Competitive Gameplay**: The fastest correct guess wins the round.

🔄 **Seamless Navigation**: Smooth transitions with Compose Navigation.

## Tech Stack

### The app is built using modern Android development tools:

**Jetpack Compose** for UI

**Compose Navigation** for seamless screen transitions

**Coroutines with Flows** for handling asynchronous tasks efficiently

**Hilt** for Dependency Injection

**Stream Chat SDK** for real-time chat between players

**Firebase Realtime Database** for live drawing broadcasts

**Sketchbook Library** to create canvas for drawing

The app follows **MVVM** clean architecture

## Challenges Faced

Integrating Stream Chat SDK into the game.

Implementing real-time drawing synchronization with Firebase.

## Limitations

UI design is minimal due to time constraints.

Some edge cases could be handled better.

More features can be added to enhance the user experience.

## Next Milestones 🚀

✨ UI Improvements: Adding animations and a polished design.

🔗 Invite Code Sharing: Easy room access via shareable codes.

🎨 Drawing Enhancements: Multiple color support and adjustable pen width.
