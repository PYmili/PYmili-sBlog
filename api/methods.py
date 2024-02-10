import base64
import string
import random


def ToBase64(source: bytes, file_type: str) -> str:
    result = ""
    header = f"data:image/{file_type};base64,"
    result = header + base64.b64encode(source).decode()

    return result


def CreateKeys(length: int = 64) -> str:
    """
    创建一个指定长度的随机字符串作为keys值。

    参数:
    length (int): 随机字符串的长度，默认为16个字符。

    返回:
    str: 生成的keys值。
    """
    # 定义可能的字符集，包括字母（大小写）和数字
    characters = string.ascii_letters + string.digits
    
    # 使用random.choice生成指定长度的随机字符串
    keys = ''.join(random.choice(characters) for _ in range(length))
    
    return keys
