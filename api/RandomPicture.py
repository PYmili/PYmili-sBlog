import random
from typing import List

import requests

headers = {
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 "
                  "Safari/537.36 Edg/119.0.0.0"
}


def joe_api() -> List[dict]:
    """
    获取joe_api的图片列表
    :return: List[dict]
    """
    data = {
        "routeType": "wallpaper_list",
        "cid": 26,
        "start": 48 * random.randint(0, 100),  # 0-209
        "count": 48
    }
    # headers['Cookie'] = ("__51uvsct__Jj6fnfTGDZ7iNop5=1; __51vcke__Jj6fnfTGDZ7iNop5=3ec468cd-2389-5fb2-9181"
    #                      "-82060692ab80; __51vuft__Jj6fnfTGDZ7iNop5=1700836447654; "
    #                      "__vtins__Jj6fnfTGDZ7iNop5=%7B%22sid%22%3A%20%2228746867-912a-5cde-bb2e-97b60f047001%22%2C"
    #                      "%20%22vd%22%3A%2011%2C%20%22stt%22%3A%20277372%2C%20%22dr%22%3A%2051642%2C%20%22expires%22"
    #                      "%3A%201700838525022%2C%20%22ct%22%3A%201700836725022%7D")
    # headers['Referer'] = "https://www.toubiec.cn/webizhi.html"
    # headers['Origin'] = "https://www.toubiec.cn"
    with requests.post(
            "https://www.toubiec.cn/joe_api",
            data=data,
            headers=headers
    ) as post:
        if post.status_code == 200:
            return post.json()['data']
        else:
            return []


def btstu_img() -> List[dict]:
    """
    获取 https://api.btstu.cn/doc/sjbz.php 中随机图片
    :return: List[dict]
    """
    result = []
    __url = "https://api.btstu.cn/sjbz/api.php?lx=dongman&format=json"

    for count in range(10):
        with requests.get(__url, headers=headers) as get:
            if get.status_code == 200:
                result.append(
                    {
                        "url": get.json()['imgurl']
                    }
                )

    return result


def hanImgApiFunction(url: str) -> List[dict]:
    """
    获取 韩小韩 图片api
    :param url: str
    :return: List[dict]
    """
    result = []
    with requests.get(url, headers=headers) as get:
        if get.status_code == 200:
            result.append(
                {
                    "url": get.json()['imgurl']
                }
            )

    return result


def hanLandscapeImg() -> List[dict]:
    """
    获取韩小韩风景api
    :return: List[dict]
    """
    result = hanImgApiFunction(
        "https://api.vvhan.com/api/view?type=json"
    )
    return result


def hanAcgImg() -> List[dict]:
    """
    获取韩小韩动漫api
    :return: List[dict]
    """
    result = hanImgApiFunction(
        "https://api.vvhan.com/api/acgimg?type=json"
    )
    return result


class RandomPicture:
    def __init__(self) -> None:
        self.apiFunction = [
            joe_api,
            # btstu_img,
            hanLandscapeImg,
            hanAcgImg
        ]

    def __run(self) -> str:
        result = ""
        __data = random.choice(self.apiFunction)()
        if __data:
            result = random.choice(__data)['url']

        return result

    def __str__(self) -> str:
        return self.__run()


# print(RandomPicture())