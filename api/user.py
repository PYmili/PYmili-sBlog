import os
import json
import string
import random
import base64
from datetime import datetime
from typing import Union, Dict


def readUserData(userName: str) -> Union[dict, None]:
    """
    读取用户数据
    :return: Union[dict, None]
    """
    result = None
    __WalkPath = os.path.join(
        os.getcwd(),
        "api/data/user"
    )
    for paths, dirs, files in os.walk(__WalkPath):
        for file in files:
            if file != userName + ".json":
                continue

            filePath = os.path.join(paths, file)
            with open(filePath, "r", encoding="utf-8") as rfp:
                result = json.loads(rfp.read())
                result['userPath'] = filePath

    if not result:
        return None

    userIcon = str(ToBase64(result['userIcon']))
    if userIcon:
        result['userIcon'] = userIcon

    return result


class ToBase64:
    def __init__(self, source: str) -> None:
        self.source = source

    def __repr__(self) -> str:
        result = ""
        if os.path.isfile(self.source):
            with open(self.source, "rb") as rfp:
                header = f"data:image/{os.path.splitext(self.source)[-1].strip('.')};base64,"
                result = header + str(base64.b64encode(rfp.read()).decode("utf-8"))

        return result


def UpdateLoginTime(userName: str) -> bool:
    """
    更新登录时间
    :param userName:
    :return: bool
    """
    data = readUserData(userName)
    if not data:
        return False

    data['Last_login'] = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    with open(data['userPath'], "w+", encoding="utf-8") as wfp:
        wfp.write(json.dumps(data, indent=4))

    return True


class KeyProcessing:
    def __init__(self, userName: str):
        self.userName = userName
        self.NowTime = datetime.now().strftime("%Y-%m-%d_%H:%M:%S")
        self.key = ""

    def generation(self) -> str:
        """
        生成
        :return: str
        """

        characters = string.ascii_letters
        random_string = ''.join(random.choice(characters) for _ in range(20))

        self.key = ''.join(a + b for a, b in zip(random_string, self.NowTime + ' '))
        self.key = self.userName+";"+self.key

        return self.key

    def save(self) -> bool:
        """
        保存key
        :return: bool
        """
        data = readUserData(self.userName)

        if not data:
            return False

        data['login_key'] = self.key
        __WalkPath = os.path.join(
            os.getcwd(),
            "api/data/user"
        )

        with open(data['userPath'], "w+", encoding="utf-8") as wfp:
            wfp.write(json.dumps(data, indent=4))

        return True

    @staticmethod
    def decrypt(_str: str) -> str:
        """
        解密
        :param _str: str
        :return: str
        """
        result = ""

        for i in _str.split(";")[-1]:
            if i in string.ascii_letters:
                continue
            elif i == "_":
                i = " "

            result += i

        return result


class verifyLogin:
    def __init__(
            self,
            userName: str,
            password: str = False,
            key: str = False
    ) -> None:
        """
        用于检查用户的登录页面的数据
        :param userName: 用户名
        :param password: 密码
        :param key: key值
        """
        self.userName = userName
        self.password = password
        self.NowTime = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
        self.key = key

    def verifyKey(self) -> bool:
        """
        验证key是否合法
        :return: bool
        """
        data = readUserData(self.userName)
        if not data:
            return False

        if self.key != data['login_key']:
            return False

        return True

    def verifyLoginTime(self) -> bool:
        """
        验证登录时间是否大于30分钟
        :return: bool
        """
        data = readUserData(self.userName)
        if not data:
            return False

        self.NowTime = datetime.strptime(self.NowTime, "%Y-%m-%d %H:%M:%S").timestamp()
        last_login = datetime.strptime(data['Last_login'], "%Y-%m-%d %H:%M:%S").timestamp()
        if ((self.NowTime - last_login) // 60) > 30:
            return False

        return True

    def verify(self) -> Dict[str, int]:
        """
        验证用户是否合法
        :return: Dict[str, int]
        """
        result = {"result": False}
        data = readUserData(self.userName)
        if not data:
            result["content"] = "未找到此用户！"
        elif self.password != data['data']['pwd']:
            result["content"] = "密码错误！"
        else:
            result['result'] = True
            result["content"] = "登录成功！"

        return result


# kp = KeyProcessing("PYmili")
# key = kp.generation()
# decKey = kp.decrypt(key)
# print(key)
# print(decKey)

# vl = verifyLogin("PYmili", "123", "PYmili;o2n0M2L3i-u1E2u-P0j2g_H1Z4E:E2Y4H:T3H7x")
# print(vl.verify())
