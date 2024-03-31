from typing import Union, List, Dict, Tuple
from loguru import logger
from pymysql import connect

from .operations import DataBaseConnection


class Users(DataBaseConnection):
    def __init__(self) -> None:
        super().__init__()

    def select_all_user(self) -> Union[List[Tuple], None]:
        """
        获取users表中的所有用户数据。
        """
        query = "SELECT * FROM users;"
        try:
            self.cursor.execute(query)
            results = self.cursor.fetchall()
        except Exception as e:
            logger.error(e)
            return None

        return list(results)

    def select_by_username(self, username: str) -> Union[Dict, None]:
        """
        通过用户名获取users表中的用户数据。
        """
        if not username:
            return None
        
        query = "SELECT * FROM users WHERE username = %s;"
        try:
            self.cursor.execute(query, (username, ))
            results = self.cursor.fetchall()
        except Exception as e:
            logger.error(e)
            return None
        
        for result in results:
            return {
                "username": result[1],
                "password": result[2],
                "email": result[3],
                "user_key": result[4],
                "late_login_time": result[5],
                "is_admin": result[6],
                "avatar": result[7]
            }

    def insert_user(self, *args) -> bool:
        """
        在数据库中的user表插入新用户
        """
        query = """
        INSERT INTO users (username, password, email, user_key) 
            VALUES (%s, %s, %s, %s);
        """
        try:
            self.cursor.execute(query, args)
        except Exception as e:
            logger.error(e)
            return False
        return True
    
    def delete_user(self, username: str, key: str) -> Union[bool, None]:
        """
        通过用户名和key删除用户
        """
        by_username_result = self.select_by_username(username)
        if not by_username_result:
            # 用户不存在
            return None
        
        query = """
        DELETE FROM users WHERE 
            username = %s AND user_key = %s;
        """
        try:
            self.cursor.execute(query, (username, key))
            # 检查受影响的行数
            if self.cursor.rowcount > 0:
                # 如果有受影响的行，则返回True表示删除成功
                return True
            else:
                # 如果没有受影响的行，则返回False表示删除失败
                return False
        except Exception as e:
            logger.error(e)
            return False
        
    def update_by_username(self, username: str, data: dict) -> bool:
        """
        通过用户名更新数据库中的数据。
        """
        # 构建更新语句
        update_columns = ', '.join([f"{key} = %s" for key in data.keys()])
        query = f"""
        UPDATE users 
        SET {update_columns} 
        WHERE username = %s
        """
        try:
            # 执行更新语句，注意将 username 添加到 data.values() 中作为参数
            self.cursor.execute(query, list(data.values()) + [username])
            return True
        except Exception as e:
            # 发生异常时回滚事务并记录错误信息
            self.connection.rollback()
            logger.error(e)
            return False
    
    def __enter__(self):
        super().__enter__()
        return self
