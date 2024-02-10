let ShowImageNumber = 24; // 一页显示图片数量
let currentPage = 0; // 当前页数
let totalImages = 0; // 总图片数
let totalPage = 0; // 总页数

document.addEventListener("DOMContentLoaded", function () {
    const imageArea = document.getElementById("imageArea");
    const pageNumbersContainer = document.getElementById("pageNumbers");

    // 模拟的图片数据
    const apiUrl = "/galleryImageList";
    const totalImagesUrl = "/galleryImageTotal";

    // 初始化页面，显示默认数量的图片
    updateTotalImages()
    .then(() => {
        updatePerPage();
        updatePageNumbers();
    });

    // 将 changePage 和 goToPage 函数移到全局作用域
    window.changePage = function(delta) {
        if (delta == 1) {
            currentPage++;
        } else if (delta == 0) {
            currentPage--;
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
            startIndex = totalImages - ShowImageNumber;
            endIndex = totalImages;
        } else {
            startIndex = currentPage * ShowImageNumber;
            endIndex = startIndex + ShowImageNumber;
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
                page: `${startIndex}-${endIndex}`
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
            totalPage = Math.ceil(data.content / ShowImageNumber);
            if (data.content % ShowImageNumber == 0) {
                totalPage++;
            }
            totalImages = data.content;
        })
        .catch(error => {
            console.error("获取总图片数量时出错：", error);
        });
    }

    function updatePageNumbers() {
        const pageNumbersHTML = [];
    
        // 显示当前页码的前两页和后两页
        for (let index = Math.max(1, currentPage - 2); index <= Math.min(totalPage, currentPage + 2); index++) {
            pageNumbersHTML.push(`<button class="page-number" onclick="goToPage(${index})">${index}</button>`);
        }
    
        // 如果总页数大于 3，显示最后一页按钮
        if (totalPage > 3) {
            pageNumbersHTML.push(`<button class="page-number" onclick="goToPage(${totalPage})">最后一页：${totalPage}</button>`);
        }
    
        pageNumbersContainer.innerHTML = pageNumbersHTML.join(' ');
    }    

    function displayImages(Data) {
        for (let i = 0; i < Data.content.length; i++) {
            const imageWrapper = document.createElement("div");
            imageWrapper.classList.add("image-wrapper");

            const imgElement = document.createElement("img");
            imgElement.src = Data.content[i].image_data;
            imgElement.alt = `Image ${i + 1}`;

            const infoElement = document.createElement("div");
            infoElement.classList.add("image-info");
            infoElement.innerHTML = `<p>由 ${Data.content[i].user_name} 于 ${Data.content[i].upload_time} 上传</p>`;

            imageWrapper.appendChild(imgElement);
            imageWrapper.appendChild(infoElement);
            imageArea.appendChild(imageWrapper);
        }
    }
});
