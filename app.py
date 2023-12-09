import os
import random
from http import HTTPStatus
from typing import Dict, Union

from flask import Flask
from flask import request
from flask import make_response
from flask import render_template
from flask import redirect
from flask_cors import CORS

# api
from api import RandomSentence  # 随机一言
from api import RandomPicture   # 随机图片
from api import (
    verifyLogin,
    KeyProcessing,
    UpdateLoginTime,
    readUserData
)


app = Flask(
    __name__,
    static_folder="static",
    template_folder="templates"
)
CORS(app)


@app.route("/", methods=["GET"])
def index():
    """
    主页
    :return: render_template
    """
    return render_template("index.html")


@app.route("/login", methods=["GET"])
def login():
    """
    登录界面
    :return: render_template
    """
    loginRenderTemplate = render_template(
        "login.html",
        RandomSentenceData=RandomSentence()
    )
    # 获取cookies
    user = request.cookies.get("user")
    key = request.cookies.get("key")

    # 检查cookies
    if not key or not user:
        return loginRenderTemplate

    # 验证user, key
    vl = verifyLogin(userName=user, key=key)
    if vl.verifyKey() and vl.verifyLoginTime():
        return redirect("/content")

    return loginRenderTemplate


@app.route("/content", methods=["GET"])
def content() -> Union[redirect, render_template]:
    """
    登录后的界面
    :return: Union[redirect, render_template]
    """
    # 获取用户名与key值
    userName = request.cookies.get("user")
    key = request.cookies.get("key")

    # 检查数据
    if not key or not userName:
        return redirect("/")

    # 验证数据
    vl = verifyLogin(userName=userName, key=key)
    if vl.verifyKey() and vl.verifyLoginTime():
        return render_template(
            "content.html",
            userName=userName,
            userIcon=readUserData(userName)['userIcon']
        )

    return redirect("/")


@app.route("/api/loginRandomImg", methods=["GET"])
def loginRandomImg() -> Dict[str, int]:
    """
    login界面随机手机背景
    :return: Dict
    """
    result = {
        "code": HTTPStatus.NOT_FOUND
    }

    # 获取mobile值，手机端或电脑端
    mobile = request.args.get("mobile")
    if not mobile:
        return result

    __contentImgPath = os.path.join(
        os.getcwd(),
        "static/login/img/content_img"
    )

    # 生成扫描文件夹路径
    __WalkPath = os.path.join(
        __contentImgPath,
        mobile
    )
    if os.path.isdir(__WalkPath) is False:
        return result

    # 扫描符合条件的图片
    __ContentAllFiles = []
    for _, _, files in os.walk(__WalkPath):
        for file in files:
            # 判断文件是否存在
            if os.path.isfile(os.path.join(__WalkPath, file)):
                __ContentAllFiles.append(
                    os.path.join(
                        f"/static/login/img/content_img/{mobile}/",
                        file
                    )
                )

    if __ContentAllFiles:
        # 随机获取，并设置code值
        result['content'] = random.choice(__ContentAllFiles)
        result['code'] = HTTPStatus.OK

    return result


@app.route("/randomImage", methods=["GET"])
def randomImage() -> Union[redirect, int]:
    """
    随机图片
    :return: Union[redirect, int]
    """
    result = str(RandomPicture())
    if RandomPicture:
        return redirect(result)
    else:
        return HTTPStatus.NO_CONTENT


@app.route("/api/login", methods=["POST"])
def api_login() -> Union[dict, make_response]:
    """
    登录接口
    :return: Union[dict, make_response]
    """
    result = {"code": HTTPStatus.NOT_FOUND}
    if not request.json:
        return result

    # 用户及密码获取
    user = request.json["user"]
    pwd = request.json["pwd"]

    vl = verifyLogin(
        userName=user,
        password=pwd
    )
    kp = KeyProcessing(userName=user)
    key = kp.generation()
    __verify = vl.verify()
    if __verify['result'] is True:
        # 通过验证
        result['code'] = HTTPStatus.OK
        result['key'] = key
        result['content'] = __verify['content']

        resp = make_response(result)
        resp.set_cookie(
            "user",
            user,
            httponly=True,
            max_age=1800
        )
        resp.set_cookie(
            "key",
            key,
            httponly=True,
            max_age=1800
        )

        # 保存新生成的key，并刷新登录时间
        kp.save()
        UpdateLoginTime(user)

        return resp

    result['content'] = __verify['content']
    return result


if __name__ in "__main__":
    app.run(
        host="0.0.0.0",
        port="8000",
        debug=True
    )
