# Lythm - Smart Lyrics-Mapping Based Music Player 🎶📝

**Lhythm** is a smart, lyrics-focused music player app designed for music lovers who want more than just playback.  
With built-in **lyrics mapping support**, it syncs and displays embedded lyrics as you listen — making every track a karaoke-ready experience.

Whether you're curating custom playlists, enjoying ad-free local music, or exploring tracks with synchronized lyrics,  
**Lythm** combines sleek design with powerful lyric-centric features for the ultimate listening experience.


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
  <img src="https://github.com/user-attachments/assets/9e7a59c4-3d49-4c87-9c9f-a6fb9b96e26c"  width="24%" />
  <img src="https://github.com/user-attachments/assets/f8eb0ac0-9baa-41f8-b717-ad074fadf102"  width="24%" />
  <img src="https://github.com/user-attachments/assets/28fb80af-4aae-4571-915f-df3906f7e48a"  width="24%" />
  <img src="https://github.com/user-attachments/assets/acdb24dc-3812-4c69-857d-cb2874e5d604"  width="24%" />
  <img src="https://github.com/user-attachments/assets/99c3ec54-8b85-4449-9958-eea09817031e"  width="24%" />
  <img src="https://github.com/user-attachments/assets/4c1e8770-2a4e-4b8a-b1ba-ecff2f502025"  width="24%" />
  <img src="https://github.com/user-attachments/assets/8e8fe111-8f1f-483e-ae59-9336f495246c"  width="24%" />
  <img src="https://github.com/user-attachments/assets/0870e8b5-f09e-4e95-9e46-c2ad5b6a77cd"  width="24%" />
  <img src="https://github.com/user-attachments/assets/fec372c9-c501-422c-ae7b-8f6e50ef8d03"  width="24%" />
 <img src="https://github.com/user-attachments/assets/fd5999e4-d7c7-46f8-a46d-92eb5fbeeb11"  width="24%" />
<img src="https://github.com/user-attachments/assets/7626ceb0-6ccb-48ec-84d8-c075a9f891d5"  width="24%" />
<img src="https://github.com/user-attachments/assets/f6265cce-1a98-48ca-bba4-6979e2535ba6"  width="24%" />

</div>


---

## App Architecture

**Lythm** follows the **MVVM (Model-View-ViewModel)** architecture combined with **Clean Architecture** principles. This ensures that the app is modular, maintainable, and scalable. Here's a brief breakdown:

- **Model**: Represents the data layer, which interacts with external sources like local storage (Content Resolver) and network APIs.
- **View**: Represents the UI, built with **Jetpack Compose**, which reacts to state changes in the ViewModel.
- **ViewModel**: The logic layer that holds UI-related data and interacts with UseCases to fetch or modify data. The ViewModel is lifecycle-conscious and designed to survive configuration changes like screen rotations.

---

## 🎵 Current Features:

- ▶️ **Basic Playback Controls**  
- 🎤 **Lyrics in Personalized Playlists**  
- 🔔 **Notification Controls**  
- 🎧 **Headphone Controls**  
- 🎚️ **Equalizer Support**
- ❤️ **Favorite Track Saving**  
- 📁 **Playlist Creation**  
- 🧭 **Bottom Navigation**  
- 🎤 **Embedded Lyrics Support** *(Most Special!)*  
- 📊 **Auto-Generated App Summary**  
- 🧠 **Sorting Options**  
- 🔍 **Powerful Search Functionality**  
- 🎨 **Realtime Theme Switching**  
- 📡 **Samsung Output Media Support**  
- ⌚ **Headphone & WearOS Song Control**  
- 📚 **Custom Multiple Playlist Support**  
- ⏰ **Sleep Timer / Alarm** 
- 📂 **File Manager Integration**  

### 🆕 v1.0.0 Features:
- ⏰ **Sleep Timer / Alarm**  
  Set a timer (Hours, Minutes, Seconds) to **auto-stop music** — perfect for sleeping or relaxing.

- 📂 **File Manager Integration**  
  Lhythm now appears in the **“Open with”** option from any file manager.

- 🎤 **Lyrics in Personalized Playlists**  
  Now see lyrics **inside your playlists** while the music plays.

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

- **Equalizer Control**:
  Lythm comes with a built-in audio equalizer that lets users fine-tune their sound experience. With customizable bands, bass boost, and preset profiles (like Rock, Jazz, Classical), users can shape the audio 
  output to match their personal taste or the genre they’re vibing with.

Also added in v1.0.0:

- **Alarm**:  
  Stop playback automatically using an in-app timer. Helpful when listening before sleep.

- **File Manager Support**:  
  Easily open audio files directly via "Open with Lythm".

- **Lyrics in Playlist View**:  
  Now shows lyrics directly inside your saved playlists while playing.

---

## How It Works

Lythm utilizes **Content Resolver** to fetch music files from the device’s storage. It identifies supported audio formats and dynamically generates a list of music that can be played.

---

## 📦 Versions:

- [**v0.1.0-beta**](https://github.com/Codexyze/Lhythm/releases/tag/v0.1.0-beta) – First Beta release  
- [**v0.2.0-beta**](https://github.com/Codexyze/Lhythm/releases/tag/v0.2.0-beta) – Second Beta release  
- [**v0.3.0-beta**](https://github.com/Codexyze/Lhythm/releases/tag/v0.3.0-beta) – Third Beta release  
- [**v1.0.0**](https://github.com/Codexyze/Lhythm/releases/tag/v1.0.0) – 🎉 Stable Release with Alarm, File Manager, and Playlist Lyrics

---

## Contributing

If you’d like to contribute to **Lythm**, feel free to fork the repo and submit a pull request with your proposed changes.

---

## 📥 Clone the Project

```bash
git clone https://github.com/Codexyze/Lhythm.git

```
---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---
