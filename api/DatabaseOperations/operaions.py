import os
import sqlite3
from datetime import datetime
from typing import Union, List, Dict

SqliteDB = os.path.join(os.getcwd(), "PYmili-s_Blog_Sqlite.db")
GalleryDB = os.path.join(os.getcwd(), "PYmili-s_Blog_Gallery.db")


class Operations:
    def __init__(self) -> None:
        self.SqliteConnect = sqlite3.connect(SqliteDB)
        self.cursor = self.SqliteConnect.cursor()

        # 创建 users 表（如果不存在）
        self.create_table()

    def create_table(self):
        create_table_query = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY,
                name TEXT NOT NULL,
                email TEXT UNIQUE,
                password TEXT,
                keys TEXT,
                avatar BLOB
            );
        """
        self.cursor.execute(create_table_query)
        self.SqliteConnect.commit()

    def get(self, userName: str) -> Union[dict, None]:
        """
        获取指定用户的所有数据

        :params
            userName str: 用户名
        """
        query = "SELECT * FROM users WHERE name = ?"
        self.cursor.execute(query, (userName,))
        # 假设用户名是唯一的，使用 fetchone 获取一条记录
        result = self.cursor.fetchone()

        if result:
            return {
                "id": result[0],
                "name": result[1],
                "email": result[2],
                "password": result[3],
                "keys": result[4],
                "avatar": result[5]  # 对于二进制数据，这里直接返回 Blob 数据
            }
        else:
            return None  # 如果没有找到该用户名的数据，则返回 None

    def create(self, name: str, email: str, password: str, keys: str, avatar: bytes) -> bool:
        """
        创建新用户

        :params
            name str: 用户名
            email str: 用户邮箱
            password str: 密码
            keys str: 其他密钥信息
            avatar bytes: 头像二进制数据
        """
        query = "SELECT * FROM users WHERE name = ?"
        self.cursor.execute(query, (name,))
        if self.cursor.fetchone():
            return False

        insert_query = "INSERT INTO users (name, email, password, keys, avatar) VALUES (?, ?, ?, ?, ?)"
        self.cursor.execute(insert_query, (name, email, password, keys, sqlite3.Binary(avatar)))
        self.SqliteConnect.commit()

        return True  # 成功创建


    def update(self, name: str, updates_dict: dict) -> bool:
        """
        更新指定用户名的信息

        :params
            name str: 用户名
            updates_dict dict: 包含要更新字段及其新值的字典，
                示例：
                    {
                        'email': 'new_email@example.com',
                        'password': 'new_password',
                        'keys': 'new_keys_info',
                        'avatar': b'new_avatar_data'
                    }
        """
        set_clause = ", ".join([f"{field} = ?" for field in updates_dict.keys() if updates_dict[field] is not None])
        values = list(updates_dict.values())
        values.append(name)

        update_query = f"UPDATE users SET {set_clause} WHERE name = ?"
        self.cursor.execute(update_query, tuple(values))
        rows_updated = self.cursor.rowcount  # 获取受影响的行数

        if rows_updated > 0:
            self.SqliteConnect.commit()
            return True  # 操作成功
        else:
            return False  # 操作失败，未找到匹配的用户

    def close(self):
        """关闭游标和连接"""
        self.cursor.close()
        self.SqliteConnect.close()


class GalleryOperations:
    def __init__(self) -> None:
        self.SqliteConnect = sqlite3.connect(GalleryDB)
        self.cursor = self.SqliteConnect.cursor()

        # 创建 user_gallery 表（如果不存在）
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


if __name__ in "__main__":
    oper = Operations()
    oper.create("PYmili", "#####", "123", "ehfiae", b'a')
    print(oper.get("PYmili"))
    Operations().update("PYmili", {"email": "mc2005wj@163.com", "password": "321", "keys": "eaifhe", "avatar": b'b'})
    print(Operations().get("PYmili"))
