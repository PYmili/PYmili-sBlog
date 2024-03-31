function showLoader() {
    document.getElementById("loading-overlay").style.display = "block";
} 
  
function hideLoader() {
    document.getElementById("loading-overlay").style.display = "none";
}  

// 寻找页面上的“退出登录”元素
var quitLink = document.querySelector('.quit a');

// 为“退出登录”元素添加点击事件监听器
quitLink.addEventListener('click', function(event) {
    // 阻止默认的锚点跳转行为（href="#"）
    event.preventDefault();

    // 在这里补充退出登录的逻辑，例如：
    quitEvent();
});

function quitEvent() {
    showLoader(); // 显示加载动画
    var cookie = document.cookie
    if (cookie === "") {
        alert("退出失败！");
        console.log("未获取到cookie");
        return
    }

    // 向api发送请求
    fetch("/api/quit_login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({}),
    })
    .then(response => response.json())
    .then(data => {
        hideLoader(); // 关闭动画
        // 处理 API 返回的数据
        if (data['code'] == 200) {
            window.location.href = data['content'];
        } else {
            alert("退出失败！");
            console.log(data['content'])
            console.log(document.cookie);
        }
    })
    .catch(error => {
        hideLoader(); // 关闭动画
        console.error("获取数据时出错：", error)
    });
}  