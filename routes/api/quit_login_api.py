import os
from typing import Union, Any, Dict
from http import HTTPStatus

from flask import request
from flask import make_response
from loguru import logger


def api_quit_login(
        UserOperations: Any,
        cache_methods: Any,
        CreateKeys: Any
    ) -> Dict[str, Union[str, int]]:
    """
    退出登录接口
    :params
        UserOperations Any: 用户数据操作,
        cache_methods Any: 调用缓存方法
        methods.CreateKeys Any: 新建Keys
    :return: Dict[str, Union[str, int]]
    """
    result = {"code": HTTPStatus.NOT_FOUND}
    if not request.cookies:
        result['content'] = "参数缺失！"
        return result
    
    userName = request.cookies.get("user")
    keys = request.cookies.get("keys")
    if not all([userName, keys]):
        result['content'] = "参数缺失！"
        return result
    
    # 读取用户数据用于判断
    get_result = UserOperations().get(userName)
    if get_result is None:
        result['content'] = "未找到此用户！"
        return result
    
    # 判断keys
    if keys != get_result['keys']:
        result['content'] = "数据不匹配！"
        return result
    
    # 删除缓存
    cache_path = os.path.join(cache_methods.CACHE_PATH, userName)
    if os.path.isdir(cache_path):
        for path, _, files in os.walk(cache_path):
            for file in files:
                file = os.path.join(path, file)
                try:
                    os.remove(file)
                except Exception:
                    logger.error(f"删除：{file}，出现错误！")
    else:
        logger.info(f"{cache_path}目录，不存在，跳过清除。")
    logger.info(f"已成功将：{userName}的缓存目录清除。")
    
    result["code"] = HTTPStatus.OK
    result["content"] = "/"
    # 清除所有cookies
    response = make_response(result)
    for key in request.cookies.keys():
        response.delete_cookie(key)

    return response
