"""
@function: 随机一言功能
"""
from typing import Dict

import requests

API = "https://api.xygeng.cn/one"


def RandomSentence() -> Dict[str, int]:
    """
    随机一言
    @code 返回状态码(默认404)
    @content 返回内容
    :return: Dict[str, int]
    """
    result = {
        "code": 404,
        "tag": None,
        "name": None,
        "origin": None,
        "content": None
    }

    try:
        with requests.get(API) as get:
            if get.status_code == 200 and get.json()['code'] == 200:
                __json = get.json()
                result["code"] = __json['code']
                result['tag'] = __json['data']['tag']
                result['name'] = __json['data']['name']
                result['origin'] = __json['data']['origin']
                result['content'] = __json['data']['content']
    except requests.exceptions.SSLError:
        # 发生ssl错误
        return result
    except requests.exceptions.ConnectionError:
        # 链接错误
        return result

    return result
