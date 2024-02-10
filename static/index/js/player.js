const audio = new Audio("/static/index/resource/audio.flac");
const playPauseButton = document.getElementById("play-pause");
const seekBar = document.getElementById("seek-bar");
const currentTime = document.getElementById("current-time");
const duration = document.getElementById("duration");

document.addEventListener("DOMContentLoaded", function () {
    playPauseButton.innerHTML = "▶";
    playPauseButton.addEventListener("click", function () {
        if (audio.paused) {
            audio.play();
            playPauseButton.innerHTML = "❚❚"; // 切换为暂停图标
        } else {
            audio.pause();
            playPauseButton.innerHTML = "▶"; // 切换为播放图标
        }
    });

    audio.addEventListener("timeupdate", function () {
        const currentMinutes = Math.floor(audio.currentTime / 60);
        const currentSeconds = Math.floor(audio.currentTime % 60);
        currentTime.textContent = currentMinutes + ":" + (currentSeconds < 10 ? "0" : "") + currentSeconds;

        seekBar.value = audio.currentTime;
    });

    audio.addEventListener("durationchange", function () {
        const totalMinutes = Math.floor(audio.duration / 60);
        const totalSeconds = Math.floor(audio.duration % 60);
        duration.textContent = totalMinutes + ":" + (totalSeconds < 10 ? "0" : "") + totalSeconds;
        seekBar.max = audio.duration;
    });

    seekBar.addEventListener("input", function () {
        audio.currentTime = seekBar.value;
    });
});

// 循环播放
audio.addEventListener("ended", function () {
    audio.currentTime = 0;
    playPauseButton.innerHTML = "▶"; // 切换为播放图标
    playPauseButton.click();
});

// 收缩
const collapseButton = document.getElementById("collapse-button");
const musicPlayer = document.querySelector(".music-player");

collapseButton.addEventListener("click", function () {
    if (collapseButton.textContent === "❮") {
        // 如果按钮当前是 "❮"，则切换为 "❯"
        collapseButton.textContent = "❯";
    } else {
        // 否则，切换回 "❮"
        collapseButton.textContent = "❮";
    }
    musicPlayer.classList.toggle("collapsed");
});
