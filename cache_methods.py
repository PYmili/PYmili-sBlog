import os
from loguru import logger

# 存放文件缓存路径
CACHE_PATH = os.path.join(os.getcwd(), "static\\cache")


def blog_posts_image_method(query_post_result: list) -> list:
    count = 0
    for query_post in query_post_result:
        post_path = os.path.join(CACHE_PATH, query_post["author"])
        url = query_post['url'].split('value=')[-1]
        if os.path.isdir(post_path):
            post_path = os.path.join(post_path, url)
        if os.path.isdir(post_path) is False:
            try:
                os.makedirs(post_path)
            except Exception:
                logger.error(f"创建文件夹：{post_path}时，出现错误！")

        # 判断image是否缓存，未缓存将自动缓存。
        image = f"/static/cache/{query_post['author']}/{url}/image.jpg"
        image_cache = os.path.join(post_path, "image.jpg")
        if os.path.isfile(image_cache) is False:
            with open(image_cache, "wb") as wfp:
                wfp.write(query_post['image'])
        
        query_post_result[count]['image'] = image
        count += 1
    
    return query_post_result


def blog_post_image_method(query_post_result: dict) -> dict:
    return blog_posts_image_method([query_post_result])[0]
