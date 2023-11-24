import requests

API = "https://api.xygeng.cn/one"


def RandomSentence() -> dict:
    """
    随机一言
    :return: dict
    """
    result = {
        "code": 404,
        "tag": None,
        "name": None,
        "origin": None,
        "content": None
    }

    with requests.get(API) as get:
        if get.status_code == 200 and get.json()['code'] == 200:
            __json = get.json()
            result["code"] = __json['code']
            result['tag'] = __json['data']['tag']
            result['name'] = __json['data']['name']
            result['origin'] = __json['data']['origin']
            result['content'] = __json['data']['content']

    return result
