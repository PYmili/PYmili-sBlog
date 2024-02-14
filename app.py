import os
import random
from http import HTTPStatus
from typing import Dict, Union

from flask import Flask
from flask import request
from flask import make_response
from flask import render_template
from flask import redirect
from flask import url_for
from flask_cors import CORS

# api
from api import RandomSentence  # 随机一言
from api import RandomPicture   # 随机图片
from api import UserOperations
from api import Gallery
from api import BlogPostOperations
from api import VerificationCodeService
from api import methods

# cache
import cache_methods
from cache_methods import CACHE_PATH

# routes
from routes import (
    home_route,
    login_route,
    content_route,
    creatvie_center_route
)
from routes.api import (
    upload_post_api,
    login_api,
    quit_login_api,
    register_api,
    login_random_img_api
)

app = Flask(
    __name__,
    static_folder="static",
    template_folder="templates"
)
CORS(app)


@app.route("/", methods=["GET"])
def index() -> render_template:
    """
    主页
    :return: render_template
    """
    return home_route.index()


@app.route("/login", methods=["GET"])
def login() -> Union[render_template, redirect]:
    """
    登录界面
    :return: Union[render_template, redirect]
    """
    return login_route.login(RandomSentence, UserOperations)


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
    登录后的内容显示主界面
    :return: Union[redirect, render_template]
    """
    return content_route.content(
        UserOperations,
        BlogPostOperations,
        cache_methods,
        methods.ToBase64
    )


@app.route("/blog_post")
def blog_post():
    """
    通过value值返回文章内容
    """
    if not request.args:
        return "参数缺失！"
    
    value = request.args.get("value")
    if not value:
        return "参数错误！"
    
    value = "/blog_post?value=" + value.strip()
    get_by_url_result = BlogPostOperations().get_by_url(value)
    if not get_by_url_result:
        return "文章不存在！"
    
    get_by_url_result = cache_methods.blog_post_image_method(get_by_url_result)
    
    return render_template(
        "blog_post.html",
        post=get_by_url_result
    )


@app.route("/creative_center")
def creative_center() -> Union[redirect, render_template]:
    """
    创作中心界面
    """
    return creatvie_center_route.creative_center(
        UserOperations,
        BlogPostOperations,
        cache_methods
    )


@app.route("/api/upload_post", methods=["POST"])
def upload_post() -> dict:
    """
    上传文章
    :reutrn dict
    """
    return upload_post_api.upload_post(
        UserOperations,
        BlogPostOperations,
        methods.CreateKeys
    )


@app.route("/api/login", methods=["POST"])
def api_login() -> Union[dict, make_response]:
    """
    登录接口
    :return: Union[dict, make_response]
    """
    return login_api.api_login(
        UserOperations,
        cache_methods,
        methods.CreateKeys
    )


@app.route("/api/quit_login", methods=["POST"])
def api_quit_login() -> Dict[str, Union[str, int]]:
    """
    退出登录接口
    :return: Dict[str, Union[str, int]]
    """
    return quit_login_api.api_quit_login(
        UserOperations,
        cache_methods,
        methods.CreateKeys
    )


@app.route("/api/register", methods=["POST"])
def api_register() -> Union[redirect, str]:
    """
    注册接口
    :return dict
    """
    return register_api.api_register(
        UserOperations,
        VerificationCodeService,
        methods.CreateKeys
    )


@app.route("/api/send_captcha", methods=["POST"])
def api_sendCaptcha() -> dict:
    """
    发送验证码
    :return dict
    """
    result = {"code": HTTPStatus.NOT_FOUND}
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
    return login_random_img_api.loginRandomImg()


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
        return HTTPStatus.NOT_FOUND


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
        port="8080",
        debug=True
    )
