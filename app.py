import os
import random
from http import HTTPStatus

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
    user = request.cookies.get("user")
    key = request.cookies.get("key")

    if key or user:
        vl = verifyLogin(userName=user, key=key)
        if vl.verifyKey() and vl.verifyLoginTime():
            return redirect("/content")

    return render_template(
        "login.html",
        RandomSentenceData=RandomSentence()
    )


@app.route("/content", methods=["GET"])
def content():
    """
    登录后的界面
    :return: render_template
    """
    userName = request.cookies.get("user")
    key = request.cookies.get("key")

    if not key or not userName:
        return render_template("index.html")

    vl = verifyLogin(userName=userName, key=key)
    if vl.verifyKey() and vl.verifyLoginTime():
        return render_template(
            "content.html",
            userName=userName,
            userIcon=readUserData(userName)['userIcon']
        )

    return redirect("/")


@app.route("/api/loginRandomImg", methods=["GET"])
def loginRandomImg():
    """
    login界面随机手机背景
    :return: json
    """
    result = {
        "code": HTTPStatus.NOT_FOUND
    }

    mobile = request.args.get("mobile")
    if not mobile:
        return result

    __contentImgPath = os.path.join(
        os.getcwd(),
        "static/login/img/content_img"
    )

    if mobile == "phone":
        __WalkPath = os.path.join(
            __contentImgPath,
            "phone"
        )
    else:
        __WalkPath = os.path.join(
            __contentImgPath,
            "pc"
        )

    __cacheFiles = []
    for paths, dirs, files in os.walk(__WalkPath):
        for file in files:
            __cacheFiles.append(
                f"static/login/img/content_img/{mobile}/"+file
            )
    if __cacheFiles:
        result['content'] = random.choice(__cacheFiles)

    return result


@app.route("/randomImage", methods=["GET"])
def randomImage():
    """
    随机图片
    :return: str
    """
    result = str(RandomPicture())
    return redirect(result)


@app.route("/api/login", methods=["POST"])
def api_login():
    """
    登录接口
    :return: json
    """
    result = {"code": HTTPStatus.NOT_FOUND}
    if not request.json:
        return result

    user = request.json["user"]
    pwd = request.json["pwd"]

    vl = verifyLogin(
        userName=user,
        password=pwd
    )
    kp = KeyProcessing(userName=user)
    key = kp.generation()
    if vl.verify() is True:
        result['code'] = HTTPStatus.OK
        result['key'] = key

        resp = make_response(result)
        resp.set_cookie("user", user, httponly=True, max_age=1800)
        resp.set_cookie("key", key, httponly=True, max_age=1800)

        kp.save()
        UpdateLoginTime(user)

        return resp

    return result


if __name__ in "__main__":
    app.run(
        host="0.0.0.0",
        port="8000",
        debug=True
    )
