document.addEventListener("DOMContentLoaded", function() {
    const video = document.getElementById("bg-video");
    const contentArea = document.getElementById("content");
  
    // 监听窗口滚动事件
    window.addEventListener("scroll", function() {
      if (isElementInViewport(contentArea)) {
        video.pause(); // 当内容进入视口时暂停视频
      } else {
        video.play(); // 当内容离开视口时恢复播放视频
      }
    });
  
    // 判断元素是否在视口中
    function isElementInViewport(el) {
      const rect = el.getBoundingClientRect();
      return (
        rect.top >= 0 &&
        rect.left >= 0 &&
        rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) &&
        rect.right <= (window.innerWidth || document.documentElement.clientWidth)
      );
    }
  });