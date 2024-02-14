from flask import request
from flask import render_template


def index() -> render_template:
    """
    主页
    :return: render_template
    """
    # 获取cookies
    UserName = request.cookies.get("user")
    keys = request.cookies.get("keys")

    if not all([UserName, keys]):
        return render_template("index.html", login_text="登录")
    
    return render_template("index.html", login_text="控制台")