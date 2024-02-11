import os
import sqlite3
from typing import Union, List, Dict

GalleryDB = os.path.join(os.getcwd(), "PYmili-s_Blog_Gallery.db")


class Operations:
    def __init__(self) -> None:
        self.SqliteConnect = sqlite3.connect(GalleryDB)
        self.cursor = self.SqliteConnect.cursor()

        # 创建表（如果不存在）
        self.create_table()

    def create_table(self):
        create_table_query = """
            CREATE TABLE IF NOT EXISTS user_gallery (
                id INTEGER PRIMARY KEY,
                user_name TEXT NOT NULL,
                image_data BLOB NOT NULL,
                image_type TEXT NOT NULL,
                upload_time TEXT DEFAULT CURRENT_TIMESTAMP
            );
        """
        self.cursor.execute(create_table_query)
        self.SqliteConnect.commit()

    def upload(self, user_name: str, image_data: bytes, image_type: str) -> bool:
        """
        创建用户上传图片记录
        
        :params
            user_name str: 用户名
            image_data bytes: 图片的二进制数据
            image_type str: 图片的类型（后缀名）如: png
        """
        insert_query = "INSERT INTO user_gallery (user_name, image_data, image_type) VALUES (?, ?, ?)"
        self.cursor.execute(insert_query, (user_name, sqlite3.Binary(image_data), image_type))
        self.SqliteConnect.commit()

        return True  # 操作成功

    def get(self, user_name: str) -> List[Dict[str, Union[int, str, bytes]]]:
        """
        获取指定用户名的所有上传图片记录
        
        :params
            user_name str: 用户名
        :return:
            List[Dict]: 包含图片记录信息的字典列表，每个字典包含：
              'id', 'user_name', 'image_data', 'image_type' 和 'upload_time' 字段。
        """
        query = "SELECT * FROM user_gallery WHERE user_name = ?"
        self.cursor.execute(query, (user_name,))
        records = self.cursor.fetchall()

        result = []
        for record in records:
            result.append({
                "id": record[0],
                "user_name": record[1],
                "image_data": record[2],
                "image_type": record[3],
                "upload_time": record[4]
            })

        return result

    def close(self):
        """关闭游标和连接"""
        self.cursor.close()
        self.SqliteConnect.close()
