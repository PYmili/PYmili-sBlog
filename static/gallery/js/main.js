document.addEventListener("DOMContentLoaded", function () {
    const imageArea = document.getElementById("imageArea");
    const pageNumbersContainer = document.getElementById("pageNumbers");

    // 模拟的图片数据
    const apiUrl = "/galleryImageList";
    const totalImagesUrl = "/galleryImageTotal";
    let currentPage = 0;
    let totalImages = 0;
    let totalPage = 0;

    // 初始化页面，显示默认数量的图片
    updateTotalImages()
    .then(() => {
        updatePerPage();
        updatePageNumbers();
    });

    // 将 changePage 和 goToPage 函数移到全局作用域
    window.changePage = function(delta) {
        if (delta == 1) {
            currentPage ++;
        } else if (delta == 0) {
            currentPage --;
        }

        updatePerPage();
        updatePageNumbers();
    };

    window.goToPage = function(pageNumber) {
        currentPage = pageNumber;
        updatePerPage();
        updatePageNumbers();
    };

    function updatePerPage() {
        let startIndex, endIndex;
    
        if (currentPage >= totalPage) {
            startIndex = Math.max(0, totalImages - 24);
            endIndex = totalImages;
        } else {
            startIndex = currentPage * 24;
            endIndex = startIndex + 24;
        }
    
        // 清空图片展示区域
        imageArea.innerHTML = '';
    
        // 发送带有范围参数的 POST 请求
        fetch(apiUrl, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                userName: "",
                page: `${startIndex}-${endIndex}`,
            }),
        })
        .then(response => response.json())
        .then(data => {
            // 处理 API 返回的数据并显示图片
            displayImages(data.content || []);
        })
        .catch(error => console.error("获取数据时出错：", error));
    }    

    function updateTotalImages() {
        // 返回 fetch 的 promise
        return fetch(totalImagesUrl, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({}),
        })
        .then(response => response.json())
        .then(data => {
            // 将获取的字符串转换为整数
            totalPage = Math.ceil(data.content / 20);
            totalImages = data.content;
        })
        .catch(error => {
            console.error("获取总图片数量时出错：", error);
        });
    }

    function updatePageNumbers() {
        const pageNumbersHTML = [];

        for (let index = 1; index <= 3; index++) {
            if (currentPage+index > totalPage) {
                continue;
            } else {
                pageNumbersHTML.push(`<button class="page-number" onclick="goToPage(${currentPage + index})">${currentPage + index}</button>`);
            }
        }
        pageNumbersHTML.push(`<button class="page-number" onclick="goToPage(${totalPage})">最后一页：${totalPage}</button>`);
        pageNumbersContainer.innerHTML = pageNumbersHTML.join(' ');
    }

    function displayImages(imageData) {
        for (let i = 0; i < imageData.length; i++) {
            const imageWrapper = document.createElement("div");
            imageWrapper.classList.add("image-wrapper");

            const imgElement = document.createElement("img");
            imgElement.src = imageData[i].url;
            imgElement.alt = `Image ${i + 1}`;

            const infoElement = document.createElement("div");
            infoElement.classList.add("image-info");
            infoElement.innerHTML = `<p>由 ${imageData[i].userName} 于 ${imageData[i].uploaded} 上传</p>`;

            imageWrapper.appendChild(imgElement);
            imageWrapper.appendChild(infoElement);
            imageArea.appendChild(imageWrapper);
        }
    }
});
