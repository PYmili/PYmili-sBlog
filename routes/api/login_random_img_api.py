import os
import random
from typing import  Any, Dict
from http import HTTPStatus

from flask import request


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
