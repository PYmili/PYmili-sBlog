let resizeTimer;

async function fetchBackgroundImage(apiEndpoint) {
  try {
    const response = await fetch(apiEndpoint);

    if (!response.ok) {
      throw new Error('网络响应不正常');
    }

    const data = await response.json();
    // console.log(data);

    // 访问 code 和 content 属性
    const code = data.code;
    const content = data.content;

    // 在这里根据 code 和 content 的值来设置背景图片
    const contentElement = document.querySelector('.content');
    contentElement.style.backgroundImage = `url(${content})`;
    contentElement.style.backgroundRepeat = 'no-repeat';
    contentElement.style.backgroundSize = 'cover';
    contentElement.style.backgroundPosition = 'center'; // 将背景图像水平和垂直居中

  } catch (error) {
    console.error('获取BackgroundImage时获取操作出现问题：', error);
  }
}

// 在窗口大小改变时调用 fetchBackgroundImage 函数
window.addEventListener('resize', function () {
  clearTimeout(resizeTimer);

  resizeTimer = setTimeout(async function () {
    var isMobile = false;

    if (window.innerWidth <= 767) {
      isMobile = true;
    }

    if (isMobile) {
      // 如果是手机，获取手机背景图片数据
      await fetchBackgroundImage('/api/loginRandomImg?mobile=phone');
    } else {
      // 如果不是手机，获取PC背景图片数据
      await fetchBackgroundImage('/api/loginRandomImg?mobile=pc');
    }
  }, 300); // 设置延迟时间，例如300毫秒
});

// 页面加载时初始化一次
window.addEventListener('load', async function () {
    // 在页面加载时检测一次屏幕宽度
    var isMobile = window.innerWidth <= 767;
  
    if (isMobile) {
      // 如果是手机，获取手机背景图片数据
      await fetchBackgroundImage('/api/loginRandomImg?mobile=phone');
    } else {
      // 如果不是手机，获取PC背景图片数据
      await fetchBackgroundImage('/api/loginRandomImg?mobile=pc');
    }
});


// 检查用户名是否包含非法字符
function isValidUsername(username) {
  // 使用正则表达式检查用户名是否只包含字母、数字和下划线
  var regex = /^[a-zA-Z0-9_]+$/;
  return regex.test(username);
}

// 定义登录函数
function login() {
    // 获取用户名和密码输入框的值
    var usernameValue = document.getElementById("usernameInput").value;
    var passwordValue = document.getElementById("passwordInput").value;

    // 检查是否填入数据
    // if (usernameValue == null || passwordValue == null) {
    //     alert('请输入用户名和密码！');
    //     return;
    // }

    // 检查用户名和密码是否有数据
    if (usernameValue.trim() === '' || passwordValue.trim() === '') {
        alert('请输入用户名或密码！');
        return;
    }
    // 检查用户名是否包含非法字符
    if (!isValidUsername(usernameValue)) {
      alert('用户名包含非法字符，请只使用字母、数字和下划线！');
      return;
    }

    // 构造 POST 请求数据
    var postData = {
        user: usernameValue,
        pwd: passwordValue
    };

    // 发送 POST 请求到 /api/login 接口
    fetch('/api/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        credentials: 'include',
        body: JSON.stringify(postData)
    })
    .then(response => response.json())
    .then(data => {
        // console.log(data);
        // 处理从服务器返回的数据
        if (data['code'] == 200) {
            window.location.assign("/content");
            // key = data['key']
            // var ContentUrl = `/content?user=${usernameValue}&key=${key}`
            // window.location.assign(ContentUrl);
        } else {
            alert(data['content']);
        }
    })
    .catch(error => {
        console.error('发生错误:', error);
    });
}