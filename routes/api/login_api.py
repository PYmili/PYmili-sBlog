import os
from typing import Union, Any
from http import HTTPStatus

from flask import request
from flask import make_response


def api_login(
        UserOperations: Any,
        cache_methods: Any,
        CreateKeys: Any
    ) -> Union[dict, make_response]:
    """
    登录接口
    :params
        UserOperations Any: 用户数据操作,
        cache_methods Any: 调用缓存方法
        methods.CreateKeys Any: 新建Keys
    :return: Union[dict, make_response]
    """
    result = {"code": HTTPStatus.NOT_FOUND}
    if not request.json:
        return result

    # 用户及密码获取
    user = request.json["user"]
    pwd = request.json["pwd"]

    Query_result = UserOperations().QueryUserData(user)
    if Query_result is None:
        result['content'] = f"未找到此用户！"
        return result
    
    if pwd != Query_result['password']:
        result['content'] = "密码错误！"
        return result
    
    # 将avatar缓存到本地static
    if Query_result['avatar']:
        avatar_cache_path = os.path.join(cache_methods.CACHE_PATH, user, "avatar.jpg")
        try:
            os.mkdir(os.path.join(cache_methods.CACHE_PATH, user))
        except OSError:
            pass

        with open(avatar_cache_path, "wb") as wfp:
            wfp.write(Query_result['avatar'])

    # 数据库更新keys
    NewKeys = CreateKeys()
    update_result = UserOperations().update(
        name=Query_result['name'],
        updates_dict={
            "email": Query_result['email'],
            "password": Query_result['password'],
            "keys": NewKeys,
            "avatar": Query_result['avatar']
        }
    )
    if update_result is False:
        result['content'] = f"更新Keys失败！"
        return result

    # 通过验证
    result['code'] = HTTPStatus.OK
    result['keys'] = NewKeys
    result['content'] = f"登录成功！欢迎: {user}！"

    # 设置cookie，有效期为30分钟, httponly: 是否允许js获取cookie
    resp = make_response(result)
    resp.set_cookie(
        "user",
        user,
        httponly=False,
        max_age=1800
    )
    resp.set_cookie(
        "keys",
        result['keys'],
        httponly=False,
        max_age=1800
    )
    return resp
