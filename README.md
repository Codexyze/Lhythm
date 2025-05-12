# Lythm - Music Player App

**Lhythm** is an intuitive music player app designed for music lovers who prefer storing their music files locally on their devices and enjoy playing them anytime. The app provides an easy-to-use interface, a variety of music organization features, and an ad-free experience for all users.

---

## App Architecture

**Lythm** follows the **MVVM (Model-View-ViewModel)** architecture combined with **Clean Architecture** principles. This ensures that the app is modular, maintainable, and scalable. Here's a brief breakdown:

- **Model**: Represents the data layer, which interacts with external sources like local storage (Content Resolver) and network APIs.
- **View**: Represents the UI, built with **Jetpack Compose**, which reacts to state changes in the ViewModel.
- **ViewModel**: The logic layer that holds UI-related data and interacts with UseCases to fetch or modify data. The ViewModel is lifecycle-conscious and designed to survive configuration changes like screen rotations.

---

## Key Features

The app offers several essential features to enhance the music listening experience. Some of the key features include:

- **Play Music (Basic)**: Allows users to play their locally stored music files with ease.
- **List Play**: Users can view their music collection in a list format and play songs sequentially or in random order.
- **Personal Playlist**: Users can create and manage their own playlists, adding or removing songs as they desire.
- **Sorting**: The app supports sorting of music files by year, artist, name, genre, etc., to make it easy for users to find their preferred tracks.
- **Lyrics Embedding**: Allows users to view embedded lyrics while listening to their favorite songs.

---

## Technology Stack

**Lythm** uses the following technologies to provide a seamless and efficient user experience:

- **Jetpack Compose**: A modern toolkit for building native Android UIs using a declarative approach.
- **MVVM (Model-View-ViewModel)**: The app architecture ensures separation of concerns and clear responsibilities between UI and logic.
- **Clean Architecture**: Helps maintain a clear separation of concerns, making the app easier to scale and maintain.
- **Use Cases**: Use Cases encapsulate the business logic, ensuring that the ViewModel remains thin and focused on UI-related operations.
- **Coil**: Used for image loading, like album art or cover images, to ensure smooth and fast image handling.
- **Splash API**: A feature to display a splash screen that loads essential data asynchronously at the start of the app.
- **Content Resolver**: Interacts with the device's storage to access and manage music files.
- **Fancy Toasts**: The app uses customized toasts to give a more engaging and visually appealing user experience.

---

## Features in Detail

- **Play Music (Basic)**:  
  The core feature of Lythm allows users to play any audio file stored on their device. The app detects supported audio formats and plays them using an efficient media player service.

- **List Play**:  
  Users can browse through their entire music library, sorted by different criteria (name, year, artist, etc.), and play their music in a list format. This helps users discover their music with ease.

- **Personal Playlist**:  
  Lythm allows users to create their own playlists. They can add or remove songs, manage playlist names, and access them quickly from the home screen.

- **Sorting**:  
  Sorting functionality enables users to arrange their music collection by artist, year, album, song name, and other metadata, giving them an organized and personalized experience.

- **Lyrics Embedding**:  
  Lyrics are embedded into the music files, so users can follow along with the lyrics while enjoying their favorite tracks.

---

## How It Works

Lythm utilizes **Content Resolver** to fetch music files from the device’s storage. It identifies supported audio formats and dynamically generates a list of music that can be played.

The **Splash Screen** initializes essential components (like the music list, playlists, etc.) asynchronously, making the startup time smooth and efficient. Once the app is ready, users can start exploring their music library right away.

---
## 📦 Versions :

- [**v0.1.0-beta**](https://github.com/Codexyze/Lhythm/releases/tag/v0.1.0-beta)

---
## Future Improvements

As the project continues to evolve, here are some features planned for future updates:

- **Audio Effects**: Implement various audio effects like equalizer, bass boost, etc.
- **Offline Mode**: Allow users to save their favorite playlists for offline listening.
- **Dark Mode**: Provide a dark theme for users who prefer a more subdued color scheme.
- **Social Sharing**: Allow users to share their favorite songs or playlists with others on social media.

---

## Contributing

If you’d like to contribute to **Lythm**, feel free to fork the repo and submit a pull request with your proposed changes.

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---
