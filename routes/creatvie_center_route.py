import os
from typing import Union, Any

from flask import request
from flask import redirect
from flask import render_template


def creative_center(
        User: Any,
        BlogPostOperations: Any,
        cache_methods: Any
) -> Union[redirect, render_template]:
    """
    创作中心界面
    :params
        UsersDatabase.Users Any: 用户数据操作,
        BlogPostOperations Any: 文章数据操作,
        cache_methods Any: 缓存操作
    """
    content = redirect("/content")

    if not request.cookies:
        return content
    
    userName = request.cookies.get("user")
    keys = request.cookies.get("keys")
    if not all([userName, keys]):
        return content
    
    Query_result = None
    with User() as user:
        Query_result = user.QueryUserData(userName)
    if not Query_result:
        return content
    
    if keys != Query_result['user_key']:
        return content
    
    get_by_result = BlogPostOperations().get_by_author(userName)

    # 对缓存进行处理
    if get_by_result:
        get_by_result = cache_methods.blog_posts_image_method(get_by_result)

    return render_template(
        "creative_center.html",
        blog_posts=get_by_result
    )