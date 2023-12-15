import os
import json
from datetime import datetime
from typing import Union


def readData(userName: Union[str, None] = None) -> list:
    """
    读取用户的Json数据
    :param userName: 用户名
    :return: list
    """
    result = []

    if userName:
        # 更新数据
        UpDataUserData(userName)

        __path = os.path.join(
            os.path.split(__file__)[0],
            "data", "gallery_data", userName
        )

        if os.path.isdir(__path) is False:
            return result

        __path = os.path.join(__path, "data.json")
        with open(__path, "r", encoding="utf-8") as rfp:
            result.append(json.loads(rfp.read()))

    walkPath = os.path.join(
        os.path.split(__file__)[0],
        "data", "gallery_data"
    )
    for paths, dirs, files in os.walk(walkPath):
        for file in files:
            if file != "data.json":
                continue
            with open(os.path.join(paths, file), "r", encoding="utf-8") as rfp:
                result.append(json.loads(rfp.read()))

    return result


def UpDataUserData(userName: str) -> bool:
    """
    更新用户的数据
    :param userName:
    :return: bool
    """
    __getcwd: str = os.path.split(__file__)[0]

    if not userName:
        return False
    __path = os.path.join(
        __getcwd,
        "data", "gallery_data", userName
    )
    if os.path.isdir(__path) is False:
        return False

    # 缓存数据
    with open(os.path.join(__path, "data.json"), "r", encoding="utf-8") as rfp:
        user_cache_data = json.loads(rfp.read())

    # 创建新文件，用于填入新数据
    wfp = open(os.path.join(__path, "data.json"), "w+", encoding="utf-8")

    walkPath = os.path.join(
        __getcwd.split("api")[0],
        "static", "gallery", "data", userName
    )
    if os.path.isdir(walkPath) is False:
        return False

    for _, _, files in os.walk(walkPath):
        for file in files:
            if user_cache_data['images']:
                for cache_img in user_cache_data['images']:
                    if file in cache_img.keys():
                        file = None

            if not file:
                continue
            user_cache_data['images'].append(
                {
                    file: datetime.now().strftime("%Y-%m-%d %H:%M:%S")
                }
            )

    wfp.write(json.dumps(user_cache_data, indent=4, ensure_ascii=False))
    wfp.close()

    return True


class Gallery:
    def __init__(
            self,
            userName: Union[str, None] = None,
            page: Union[str, None] = None
    ) -> None:
        self.userName = userName
        if page is None:
            page = "0-20"
        self.page = page

    def getData(self) -> Union[list, None]:
        """
        获取数据
        :return: Union[dict, None]
        """
        result = []

        UserData = readData(self.userName)
        if not UserData:
            return None
        start = eval(self.page.split('-')[0])
        end = eval(self.page.split('-')[-1])
        for data in UserData:
            for images in data['images'][start:end]:
                for _path, _time in images.items():
                    result.append(
                        {
                            "url": fr"/static/gallery/data/{data['userName']}"+"/"+_path,
                            "uploaded": _time,
                            "userName": data["userName"]
                        }
                    )

        return result

    def total(self) -> int:
        """
        获取图片的总数
        :return: int
        """
        result = 0
        UserData = readData()
        if not UserData:
            return result

        for data in UserData:
            for images in data['images']:
                result += 1

        return result


if __name__ in "__main__":
    print(UpDataUserData("PYmili"))
    g = Gallery(page="0-1")
    print(g.getData())
    print(g.total())