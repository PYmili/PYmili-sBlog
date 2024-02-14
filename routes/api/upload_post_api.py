import os
from typing import Union, Any
from http import HTTPStatus

from flask import request


def upload_post(
        UserOperations: Any,
        BlogPostOperations: Any,
        CreateKeys: Any
    ) -> dict:
    """
    上传文章
    :params
        UserOperations Any: 用户数据操作,
        BlogPostOperations Any: 文章数据操作,
        methods.CreateKeys Any: 新建Keys
    :reutrn dict
    """
    result = {
        "code": HTTPStatus.NO_CONTENT,
        "content": "缺少参数！"
    }
    if not request.json or not request.cookies:
        return result
    
    title = request.json.get("title")
    excerpt = request.json.get("excerpt")
    text = request.json.get("text")
    image = request.json.get("image")
    if not all([title, excerpt, text, image]):
        return result
    
    userName = request.cookies.get("user")
    keys = request.cookies.get("keys")
    if not all([userName, keys]):
        return result
    
    get_result = UserOperations().get(userName)
    if not get_result:
        result['content'] = "此用户未找到！"
        return result
    if keys != get_result['keys']:
        result['content'] = "用户参数错误！"
        return result

    insert_result = BlogPostOperations().insert_new_article(
        author=userName,
        insert_data={
            "title": title,
            "excerpt": excerpt,
            "image": image,
            "text": text,
            "url": "/blog_post?value=" + CreateKeys()
        }
    )
    if not insert_result:
        result['content'] = "保存失败！"
        return result
    
    result['code'] = HTTPStatus.OK
    result['content'] = "保存成功！"
    return result
