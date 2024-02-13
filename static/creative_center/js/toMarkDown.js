document.addEventListener('DOMContentLoaded', function() {
    var markdownTextarea = document.getElementById('markdown-textarea');
    var previewContainer = document.getElementById('preview-content');

    // 使用 Showdown 将 Markdown 转换成 HTML
    var converter = new showdown.Converter();
    markdownTextarea.addEventListener('input', function() {
        previewContainer.innerHTML = converter.makeHtml(markdownTextarea.value);
    });
});

// 提交文章的函数
function submitPost() {
    var postTitle = document.getElementById('post-title').value;
    var postExcerpt = document.getElementById('post-excerpt').value;
    // var markdownContent = document.getElementById('markdown-textarea').value;
    var previewContent = document.getElementById('preview-content').innerHTML;
    var selectedImage = document.getElementById('selected_image');

    // 创建一个包含所有文章数据的对象
    var postData = {
        title: postTitle,
        excerpt: postExcerpt,
        // content: markdownContent,
        text: previewContent,
        image: (selectedImage && selectedImage.src) ? selectedImage.src : null // 检查是否有上传图片
    };

    // 将数据序列化为JSON字符串
    var jsonData = JSON.stringify(postData);

    // 发送POST请求到API端点
    fetch('/api/upload_post', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: jsonData
    })
    .then(response => response.json())
    .then(data => {
        if (data.code === 200) { // 根据API返回的数据判断是否成功
            alert('文章已成功上传！');
        } else {
            alert('文章上传失败：' + data.content); // 显示错误信息
        }
    })
    .catch(error => {
        console.error('提交文章时发生错误:', error);
        alert('文章上传过程中发生错误，请稍后重试。');
    });
}