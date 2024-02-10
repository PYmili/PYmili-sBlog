import os
import random
import datetime
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
from api import UserOperations
from api import Gallery
from api import VerificationCodeService
from api import methods


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
    UserName = request.cookies.get("UserName")
    keys = request.cookies.get("keys")

    # 检查
    if not keys or not UserName:
        return loginRenderTemplate

    # 验证user, keys
    Qurey_result = UserOperations().QueryUserData(UserName)
    if Qurey_result is None:
        return loginRenderTemplate
    
    if Qurey_result['keys'] == keys:
        return redirect("/content")

    return loginRenderTemplate


@app.route("/register")
def register() -> render_template:
    """
    注册界面
    :return render_template
    """
    return render_template("register.html")


@app.route("/content", methods=["GET"])
def content() -> Union[redirect, render_template]:
    """
    登录后的界面
    :return: Union[redirect, render_template]
    """
    # 获取用户名与key值
    UserName = request.cookies.get("user")
    keys = request.cookies.get("keys")

    login_template = redirect("/login")

    # 检查数据
    if not keys or not UserName:
        return login_template

    # 验证数据
    Qurey_result = UserOperations().QueryUserData(UserName)
    if Qurey_result is None:
        return login_template
    
    if Qurey_result['keys'] == keys:
        return render_template(
            "content.html",
            userName=UserName,
            userAvatar=Qurey_result['avatar']
        )

    return login_template


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

    Query_result = UserOperations().QueryUserData(user)
    if Query_result is None:
        result['content'] = "未找到此用户！"
        return result
    
    if pwd == Query_result['password']:
        # 通过验证
        result['code'] = HTTPStatus.OK
        result['keys'] = Query_result['keys']
        result['content'] = f"登录成功！欢迎: {user}！"

        # 设置cookie，有效期为30分钟
        resp = make_response(result)
        resp.set_cookie(
            "user",
            user,
            httponly=True,
            max_age=1800
        )
        resp.set_cookie(
            "keys",
            Query_result['keys'],
            httponly=True,
            max_age=1800
        )
        return resp

    result['content'] = "未找到此用户！"
    return result


@app.route("/api/register", methods=["POST"])
def api_register() -> Union[redirect, str]:
    """
    注册接口
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
        keys=methods.CreateKeys(),
        avatar=b'Null'
    )
    if create_result is True:
        return redirect("/login")
    
    if UserOperations().get(username):
        return redirect("/login")
    return f"""<h1 algin="center">{msg}</h1>"""


@app.route("/api/send_captcha", methods=["POST"])
def api_sendCaptcha() -> dict:
    """
    发送验证码
    :return dict
    """
    result = {"code": HTTPStatus.NO_CONTENT}
    to_email_adder = request.json.get("to_email_adder")
    if not to_email_adder:
        return result

    VCS = VerificationCodeService()
    if VCS.send_code(to_email_adder) is True:
        result["code"] = HTTPStatus.OK

    return result


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


@app.route("/gallery", methods=["GET"])
def gallery():
    """
    图库界面
    :return:
    """
    return render_template("gallery.html")


@app.route("/galleryImageTotal", methods=["POST", "GET"])
def galleryImageTotal():
    """
    返回图库中所有图片的总数
    :return:
    """
    return {
        "content": Gallery().count_records()
    }


@app.route("/galleryImageList", methods=["POST"])
def galleryImageList():
    """
    API
    获取图库中的数据
    :return:
    """
    result = {
        "code": HTTPStatus.NOT_FOUND
    }

    if not request.json:
        return result
    
    page = request.json["page"]
    if not page:
        return result
    
    page = page.split("-")
    start = int(page[0])
    end = int(page[1])

    data = Gallery().get_records_by_id_range(start, end)
    if data:
        result["content"] = data
        result["code"] = HTTPStatus.OK

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


# 其他个人制作界面
@app.route("/firefly")
def firefly():
    """
    给流萤制作的界面
    """
    return render_template("firefly.html")


if __name__ in "__main__":
    app.run(
        host="0.0.0.0",
        port="8000",
        debug=True
    )
