import os
import sqlite3
from typing import Union, List, Dict

UserSqliteDB = os.path.join(os.getcwd(), "PYmili-s_Blog_Sqlite.db")


class Operations:
    def __init__(self) -> None:
        self.SqliteConnect = sqlite3.connect(UserSqliteDB)
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
                "avatar": result[5]
            }
        else:
            return None  # 如果没有找到该用户名的数据，则返回 None

    def create(self, name: str, email: str, password: str, keys: str, avatar: str) -> bool:
        """
        创建新用户

        :params
            name str: 用户名
            email str: 用户邮箱
            password str: 密码
            keys str: 其他密钥信息
            avatar str: 头像二进制数据
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
                        'avatar': 'new_avatar_data'
                    }
        """
        updates_dict['avatar'] = sqlite3.Binary(updates_dict['avatar'])
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
        