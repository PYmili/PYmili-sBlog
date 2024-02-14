from typing import Union, Any

from flask import request
from flask import redirect


def api_register(
        UserOperations: Any,
        VerificationCodeService: Any,
        CreateKeys: Any
    ) -> Union[redirect, str]:
    """
    注册接口
    :params
        UserOperations Any: 用户数据操作,
        VerificationCodeService Any:初始化验证码服务类,
        CreateKeys Any: 生成新的Keys
    :return dict
    """
    if not request.form:
        return """<h1 algin="center">缺少参数！</h1>"""

    username = request.form.get("username")
    password = request.form.get("password")
    email = request.form.get("email")
    captcha = request.form.get("captcha")

    if not all([username, password, email]):
        return """<h1 algin="center">缺少参数！</h1>"""
    
    state, msg = VerificationCodeService().verify_code(email, captcha)
    if state is False:
        return f"""<h1 algin="center">{msg}</h1>"""
    
    create_result = UserOperations().create(
        name=username,
        password=password,
        email=email,
        keys=CreateKeys(),
        avatar=b'Null'
    )
    if create_result is True:
        return redirect("/login")
    
    if UserOperations().get(username):
        return redirect("/login")
    return f"""<h1 algin="center">{msg}</h1>"""
