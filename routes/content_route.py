import os
from typing import Union, Any

from flask import request
from flask import redirect
from flask import render_template


def content(
        UserOperations: Any,
        BlogPostOperations: Any,
        cache_methods: Any,
        ToBase64: Any
    ) -> Union[redirect, render_template]:
    """
    登录后的内容显示主界面
    :params
        UserOperations Any: 用于获取用户数据
        BlogPostOperations Any: 用于获取文章数据
        cache_methods Any: 调用缓存方法
        methods.ToBase64 Any: 用于base64数据转换
    :return: Union[redirect, render_template]
    """
    # 获取用户名与key值
    UserName = request.cookies.get("user")
    keys = request.cookies.get("keys")

    login_template = redirect("/login")

    # 检查数据
    if not keys or not UserName:
        return login_template

    # 验证数据
    Qurey_result = UserOperations().QueryUserData(UserName)
    if Qurey_result is None:
        return login_template
    
    # 读取文章数据
    blog_posts = []
    query_post_result = BlogPostOperations().get_by_author(UserName)

    # 对缓存进行处理
    if query_post_result:
        blog_posts = cache_methods.blog_posts_image_method(query_post_result)
    
    avatar = os.path.join(cache_methods.CACHE_PATH, UserName, "avatar.jpg")
    if os.path.isfile(avatar):
        avatar = f"/static/cache/{UserName}/avatar.jpg"
    else:
        avatar = ToBase64(Qurey_result['avatar'], "jpg")

    if Qurey_result['keys'] == keys:
        return render_template(
            "content.html",
            userName=UserName,
            userAvatar=avatar,
            blog_posts=blog_posts
        )

    return login_template
