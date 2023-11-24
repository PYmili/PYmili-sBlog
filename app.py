from flask import Flask
from flask import render_template

# api
from api import RandomSentence


app = Flask(
    __name__,
    static_folder="static",
    template_folder="templates"
)


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
    return render_template(
        "login.html",
        RandomSentenceData=RandomSentence()
    )


if __name__ in "__main__":
    app.run(
        host="0.0.0.0",
        port="8000",
        debug=True
    )
