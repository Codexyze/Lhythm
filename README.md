# Lythm - Music Player App

**Lhythm** is an intuitive music player app designed for music lovers who prefer storing their music files locally on their devices and enjoy playing them anytime. The app provides an easy-to-use interface, a variety of music organization features, and an ad-free experience for all users.

---

<div style="display: flex; flex-wrap: wrap;">
  <img src="https://github.com/user-attachments/assets/52a162dd-5c08-41fa-8ece-4e20cc398548" width="24%" />
  <img src="https://github.com/user-attachments/assets/86fafd0f-ecb1-43c3-852b-90e97846819f" width="24%" />
  <img src="https://github.com/user-attachments/assets/9c770016-4db3-41aa-b435-43bfc0237f0a" width="24%" />
  <img src="https://github.com/user-attachments/assets/b13f1c27-35e2-42a2-9375-7548eee439cb" width="24%" />
  <img src="https://github.com/user-attachments/assets/335d65ea-dcec-4ff1-ade4-946a8539b71f" width="24%" />
  <img src="https://github.com/user-attachments/assets/6bd76060-541e-4924-94f8-5b8ee9d8e3ef" width="24%" />
  <img src="https://github.com/user-attachments/assets/e693d197-5b4c-40e1-b028-12ce26f6da2b" width="24%" />
  <img src="https://github.com/user-attachments/assets/576e6448-7aa1-4670-8fd5-fd56d26fbae9" width="24%" />
</div>

---

## App Architecture

**Lythm** follows the **MVVM (Model-View-ViewModel)** architecture combined with **Clean Architecture** principles. This ensures that the app is modular, maintainable, and scalable. Here's a brief breakdown:

- **Model**: Represents the data layer, which interacts with external sources like local storage (Content Resolver) and network APIs.
- **View**: Represents the UI, built with **Jetpack Compose**, which reacts to state changes in the ViewModel.
- **ViewModel**: The logic layer that holds UI-related data and interacts with UseCases to fetch or modify data. The ViewModel is lifecycle-conscious and designed to survive configuration changes like screen rotations.

---


## 🎵 Current  Features :

- ▶️ **Basic Playback Controls**  
  Play, pause, skip to next, rewind 10 seconds, and forward 10 seconds effortlessly.

- 🔔 **Notification Controls**  
  Control your music right from the notification bar.

- 🎧 **Headphone Controls**  
  Control your music with headphones/earphones.

- ❤️ **Favorite Track Saving**  
  Mark your favorite tracks and find them anytime.

- 📁 **Playlist Creation**  
  Create and manage your own playlists with ease.

- 🧭 **Bottom Navigation**  
  Simple and intuitive bottom bar navigation for a smooth user experience.

- 🎤 **Embedded Lyrics Support** *(Most Special!)*  
  View lyrics embedded within the song — karaoke night just got easier!

- 📊 **Auto-Generated App Summary**  
  Automatically generated info summary that gives you a quick snapshot of your music stats.

- 🧠 **Sorting Options**  
  Sort your songs by:
  - Ascending / Descending order  
  - Artist  
  - Year  
  - Favorite  
  - Composer

- 🔍 **Powerful Search Functionality**  
  Search in:
  - 🎼 All Songs  
  - 🎶 Your Playlists  

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


---
## 📦 Versions :

- [**v0.1.0-beta**](https://github.com/Codexyze/Lhythm/releases/tag/v0.1.0-beta) : **First Beta** release

---
## Future Improvements

As the project continues to evolve, here are some features planned for future updates:

- **Audio Effects**: Implement various audio effects like equalizer, bass boost, etc.
- **Offline Mode**: Allow users to save their favorite playlists for offline listening.
- **Dark Mode**: Provide a dark theme for users who prefer a more subdued color scheme.
- **Social Sharing**: Allow users to share their favorite songs or playlists with others on social media.
- **Personal Playlist**: Allows users to create their own playlists. They can add or remove songs, manage playlist names, and access them quickly from the home screen.

---

## Contributing

If you’d like to contribute to **Lythm**, feel free to fork the repo and submit a pull request with your proposed changes.

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---
