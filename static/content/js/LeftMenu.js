const body = document.querySelector('body'),
    shell = body.querySelector('nav'),
    toggle = body.querySelector(".toggle"),
    searchBtn = body.querySelector(".search-box"),
    modeSwitch = body.querySelector(".toggle-switch"),
    modeText = body.querySelector(".mode-text");
// 点击toggle元素时触发事件
toggle.addEventListener("click", () => {
    // 切换shell元素的close类
    shell.classList.toggle("close");
})
// 点击searchBtn元素时触发事件
searchBtn.addEventListener("click", () => {
    // 移除shell元素的close类
    shell.classList.remove("close");
})
// 点击modeSwitch元素时触发事件
modeSwitch.addEventListener("click", () => {
    var sun_moon = document.getElementById("sun-moon");

    // 切换body元素的dark类
    body.classList.toggle("dark");
    // 如果body元素包含dark类
    if (body.classList.contains("dark")) {
        sun_moon.className = "iconfont icon-moon";
        modeText.innerText = "白日模式";
    } else {
        sun_moon.className = "iconfont icon-sun";
        modeText.innerText = "夜间模式";
    }
});