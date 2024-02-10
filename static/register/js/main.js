function sendActualCaptcha() {
    var to_email_adder = document.getElementById("email");
    // console.log(to_email_adder.value);
    // 构造 POST 请求数据
    var postData = {
        to_email_adder: to_email_adder.value
    };
    // 在这里调用后端API发送验证码并根据实际情况处理返回结果
    fetch('/api/send_captcha', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        credentials: 'include',
        body: JSON.stringify(postData)
    })
    .then(response => response.json())
    .then(data => {
        if (data['code'] == 200) {
            alert("发送成功！");
            return
        } else {
            alert("发送失败！");
        }
    })
    .catch(error => {
        console.error('发生错误:', error);
    });
}
  
function sendCaptcha(button) {
    button.disabled = true; // 立即禁用按钮
    let countdownNum = 60;
    const countdownElement = document.getElementById("countdown");

    // 更改按钮为灰色
    countdownElement.style.backgroundColor = `grey`;

    // 设置倒计时并每秒更新
    const countdownInterval = setInterval(() => {
        countdownNum--;
        countdownElement.textContent = "重新发送（" + countdownNum + "）";

        if (countdownNum <= 0) {
            clearInterval(countdownInterval);
            // 恢复按钮样式
            countdownElement.textContent = "获取验证码";
            countdownElement.style.backgroundColor = `#4CAF50`;
            button.disabled = false; // 重置按钮状态为可点击
        }
    }, 1000);

    // 调用实际的发送验证码方法或请求
    sendActualCaptcha();
    
    // 阻止默认事件，防止在倒计时期间按钮被点击
    button.addEventListener('click', function(event) {
        event.preventDefault();
        return false;
    });
}