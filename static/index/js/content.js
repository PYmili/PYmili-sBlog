document.addEventListener("DOMContentLoaded", function () {
    const siteBuildTimeElement = document.getElementById("site-build-time");
    const daysElement = document.getElementById("days");
    const hoursElement = document.getElementById("hours");
    const minutesElement = document.getElementById("minutes");
    const secondsElement = document.getElementById("seconds");

    const siteBuildDate = new Date("2023-11-24"); // 替换为您的网站建立日期

    function updateSiteBuildTime() {
        const currentDate = new Date();
        const timeDifference = currentDate - siteBuildDate;
        const totalSeconds = Math.floor(timeDifference / 1000);

        const days = Math.floor(totalSeconds / 86400);
        const hours = Math.floor((totalSeconds % 86400) / 3600);
        const minutes = Math.floor((totalSeconds % 3600) / 60);
        const seconds = totalSeconds % 60;

        daysElement.textContent = days;
        hoursElement.textContent = hours < 10 ? "0" + hours : hours;
        minutesElement.textContent = minutes < 10 ? "0" + minutes : minutes;
        secondsElement.textContent = seconds < 10 ? "0" + seconds : seconds;
    }

    // 初始更新
    updateSiteBuildTime();

    // 定期更新已建站时间
    setInterval(updateSiteBuildTime, 1000);
});
