from typing import Union, Dict, Any

from . import DBoperaions


class BlogPostOperations(DBoperaions.Operations):
    def __init__(self) -> None:
        super().__init__()

    def insert_new_article(self, author: str, insert_data: dict) -> Union[int, None]:
        """
        在数据库中插入新文章
        
        :params
            author str: 作者
            insert_data: 插入的新文章
                例如：{
                    [title str]: [文章标题]
                    [excerpt str]: [文章摘要]
                    [url str]: [文章链接]
                    [image_type str]: [图片类型]
                    [image str]: [图片数据 base64]
                    [text str]: [正文]
                }
        :return Union[int, None]
        """

        if not insert_data['url']:
            return None
        
        if type(insert_data['image']).__name__ != "str":
            return None
        
        all_url_query = """SELECT url FROM blog_posts;"""
        self.cursor.execute(all_url_query)
        select_result = self.cursor.fetchall()
        if insert_data['url'] in select_result:
            return None

        create_result = self.create(
            author=author,
            title=insert_data['title'] if insert_data['title'] else "NULL",
            excerpt=insert_data['excerpt'] if insert_data['excerpt'] else "NULL",
            url=insert_data['url'],
            image_type=insert_data['image_type'] if insert_data['image_type'] else "jpg",
            image=insert_data['image'],
            text=insert_data['text'] if insert_data['text'] else "NULL"
        )
        return create_result
    
    def all_blog_post(self) -> int:
        """
        获取所有文章总数
        :return int
        """
        query = """SELECT count(*) FROM blog_posts;"""
        self.cursor.execute(query)
        select_result = self.cursor.fetchone()[0]
        return select_result
    
    def get_by_url(self, url: str) -> Dict[str, Any]:
        """
        通过url值获取数据

        :params 
            url str: 需要判断的值，唯一的。
        :return Dict[str, Any]
        """
        qurey = """SELECT * FROM blog_posts WHERE url = ?;"""
        self.cursor.execute(qurey, (url,))
        fetchall = self.cursor.fetchall()
        if not fetchall:
            return {}
        data = fetchall[0]

        return {
            "id": data[0],
            "author": data[1],
            "title": data[2],
            "excerpt": data[3],
            "url": data[4],
            "image_type": data[5],
            "image": data[6],
            "text": data[7],
            'upload_time': data[8]
        }
    