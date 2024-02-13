// 单击菜单栏按钮，页面切换
document.addEventListener('DOMContentLoaded', function() {
    var navItems = document.querySelectorAll('.nav-item');
    navItems.forEach(function(item) {
        item.addEventListener('click', function(event) {
            event.preventDefault();
            var targetId = this.getAttribute('href').slice(1);
            var sections = document.querySelectorAll('.content-section');
            sections.forEach(function(section) {
                section.classList.remove('active');
            });
            document.getElementById(targetId).classList.add('active');
        });
    });
});

function handleImageUpload(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        
        reader.onload = function(e) {
            var imgPreview = document.getElementById('header-image-preview');
            imgPreview.innerHTML = '<img src="' + e.target.result + '" alt="Selected Image" id="selected_image">';
        }
        
        reader.readAsDataURL(input.files[0]);
    }
}