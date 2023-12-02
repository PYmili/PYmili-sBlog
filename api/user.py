import os
import json
import string
import random
from datetime import datetime


def readUserData(userName: str) -> dict:
    """
    读取用户数据
    :return: dict
    """
    result = {}
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

    return result


def UpdateLoginTime(userName: str) -> bool:
    """
    更新登录时间
    :param userName:
    :return: bool
    """
    data = readUserData(userName)
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
    def __init__(self, userName: str, password: str = False, key: str = False):
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

    def verifyLoginTime(self):
        """
        验证登录时间是否大于30分钟
        :return:
        """
        data = readUserData(self.userName)
        if not data:
            return False

        self.NowTime = datetime.strptime(self.NowTime, "%Y-%m-%d %H:%M:%S").timestamp()
        last_login = datetime.strptime(data['Last_login'], "%Y-%m-%d %H:%M:%S").timestamp()
        if ((self.NowTime - last_login) // 60) > 30:
            return False

        return True

    def verify(self) -> bool:
        """
        验证用户是否合法
        :return: bool
        """
        data = readUserData(self.userName)
        if not data:
            return False

        if self.password != data['data']['pwd']:
            return False

        return True


# kp = KeyProcessing("PYmili")
# key = kp.generation()
# decKey = kp.decrypt(key)
# print(key)
# print(decKey)

# vl = verifyLogin("PYmili", "123", "PYmili;o2n0M2L3i-u1E2u-P0j2g_H1Z4E:E2Y4H:T3H7x")
# print(vl.verify())
