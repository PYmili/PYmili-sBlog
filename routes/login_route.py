from typing import Union, Any

from flask import request
from flask import redirect
from flask import render_template


def login(
        RandomSentence: Any,
        UserOperations: Any
    ) -> Union[render_template, redirect]:
    """
    登录界面
    :return: Union[render_template, redirect]
    """
    loginRenderTemplate = render_template(
        "login.html",
        RandomSentenceData=RandomSentence()
    )
    # 获取cookies
    UserName = request.cookies.get("user")
    keys = request.cookies.get("keys")

    # 检查
    if not all([keys, UserName]):
        return loginRenderTemplate

    # 验证user, keys
    Qurey_result = UserOperations().QueryUserData(UserName)
    if Qurey_result is None:
        return loginRenderTemplate
    
    if Qurey_result['keys'] == keys:
        return redirect("/content")

    return loginRenderTemplate
