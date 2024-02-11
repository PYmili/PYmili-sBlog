import os
import sqlite3
from typing import Union, List, Dict

BlogPostDB = os.path.join(os.getcwd(), "PYmili-s_Blog_Blog_Post.db")


class Operations:
    def __init__(self) -> None:
        self.SqliteConnect = sqlite3.connect(BlogPostDB)
        self.cursor = self.SqliteConnect.cursor()

        # 创建表（如果不存在）
        self.create_table()

    def create_table(self):
        create_table_query = """
            CREATE TABLE IF NOT EXISTS blog_posts (
                id INTEGER PRIMARY KEY,
                author TEXT NOT NULL,
                title TEXT NOT NULL,
                excerpt TEXT NOT NULL,
                url TEXT NOT NULL,
                image_type TEXT NOT NULL,
                image TEXT NOT NULL,
                text TEXT NOT NULL,
                upload_time TEXT DEFAULT CURRENT_TIMESTAMP
            );
        """
        self.cursor.execute(create_table_query)
        self.SqliteConnect.commit()

    def create(
            self,
            author: str,
            title: str,
            excerpt: str,
            url: str,
            image_type: str,
            image: str,
            text: str
        ) -> int:
        """
        插入新的博客文章记录
        
        :params
            author str: 作者名
            title str: 文章标题
            excerpt str: 文章摘要
            url str: 文章链接
            image_type str: 图片类型
            image str: 图片数据（base64形式）
            text: str: 正文
        
        :return:
            int: 新插入记录的id
        """
        insert_query = """
            INSERT INTO blog_posts (author, title, excerpt, url, image_type, image, text)
            VALUES (?, ?, ?, ?, ?, ?, ?);
        """
        self.cursor.execute(insert_query, (author, title, excerpt, url, image_type, image, text))
        self.SqliteConnect.commit()
        return self.cursor.lastrowid

    def get_by_author(self, author: str) -> List[Dict[str, Union[int, str, bytes]]]:
        """
        获取指定作者的所有博客文章记录
        
        :params
            author str: 作者名
        :return:
            List[Dict]: 包含博客文章记录信息的字典列表，每个字典包含：
              'id', 'author', 'title', 'excerpt', 'url', 'image_type' 和 'upload_time' 字段。
        """
        select_query = """
            SELECT * FROM blog_posts WHERE author = ?;
        """
        self.cursor.execute(select_query, (author,))
        rows = self.cursor.fetchall()

        records = []
        for row in rows:
            record = {
                'id': row[0],
                'author': row[1],
                'title': row[2],
                'excerpt': row[3],
                'url': row[4],
                'image_type': row[5],
                'image': row[6],
                'text': row[7],
                'upload_time': row[8]
            }
            records.append(record)

        return records

    def update_by_id_and_author(
            self,
            post_id: int,
            author: str,
            title: str = None,
            excerpt: str = None,
            url: str = None,
            image_type: str = None,
            image: bytes = None
        ) -> None:
        """
        更新指定作者和ID的博客文章记录
        
        :params
            post_id int: 博客文章ID
            author str: 作者名
            title str: （可选）新的文章标题
            excerpt str: （可选）新的文章摘要
            url str: （可选）新的文章链接
            image_type str: （可选）新的图片类型
            image str: （可选）新的图片数据（base64形式）
        """
        update_query = """
            UPDATE blog_posts SET
                title = COALESCE(?, title),
                excerpt = COALESCE(?, excerpt),
                url = COALESCE(?, url),
                image_type = COALESCE(?, image_type),
                image = COALESCE(?, image)
            WHERE id = ? AND author = ?;
        """
        params = [title, excerpt, url, image_type, image, post_id, author]
        params = [p for p in params if p is not None] + [post_id, author]

        self.cursor.execute(update_query, params)
        self.SqliteConnect.commit()
