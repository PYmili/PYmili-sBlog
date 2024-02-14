import string
import random
from typing import Union

from . import DBoperations
from ..methods import ToBase64
from ..methods import CreateKeys


class UserOperations(DBoperations.Operations):
    """继承 Operations 类用于对用户的注册，查询，更新数据。"""
    def __init__(self) -> None:
        super().__init__()

    def register(self, userName: str, otherData: dict) -> dict:
        """
        注册用户

        :params 
            userName: 用户名称
            otherData [json]: 其他数据
                email: str, password: str, avatar: bytes
        :return dict
        """
        result = {
            "code": 400,
            "content": "创建失败！"
        }
        if not otherData:
            result['content'] = "缺少用户数据！"
            return result
        
        if self.get(userName):
            # 用户已存在
            result['content'] = "用户已存在！"
            return result

        create_result = self.create(
            name=userName,
            email=otherData['email'],
            password=otherData['password'],
            keys=CreateKeys(),
            avatar=otherData['avatar']
        )
        return create_result
    
    def QueryUserData(self, userName: str) -> Union[dict, None]:
        """
        查询用户数据

        :params
            userName: 用户名
        :return Union[dict, None]
        """
        return self.get(userName)
    