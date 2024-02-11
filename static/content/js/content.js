function selectCard(card) {
    // 移除所有卡片的 'active' 类
    const cards = document.querySelectorAll('.blog-post-card');
    cards.forEach(card => card.classList.remove('active'));

    // 添加 'active' 类到当前卡片
    card.classList.add('active');
}