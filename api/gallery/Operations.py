import os
from typing import Union, List, Dict

from . import DBOperations
from ..methods import ToBase64


class Gallery(DBOperations.Operations):
    """Gallery 图库数据操作"""
    def __init__(self) -> None:
        super().__init__()

    def get_records_by_id_range(self, start: int = 1, end: int = 24) -> Dict[str, Union[List, str, int]]:
        """
        根据ID区间获取用户上传图片记录
        
        :params
            start int: 区间起始位置（包含）
            end int: 区间结束位置（包含）
        :return:
            List[Dict]: 包含图片记录信息的字典列表，每个字典包含 'id', 'user_name', 'image_data' 和 'upload_time' 字段
        """
        result = {
            "code": 400,
            "content": []
        }
        if start > end:
            result['message'] = f"错误：起始值应小于或等于结束值。start: {start} > end: {end}"
            return result
        
        query = "SELECT * FROM user_gallery WHERE id BETWEEN ? AND ?"
        self.cursor.execute(query, (start, end))

        records = self.cursor.fetchall()
        if not records:
            result['message'] = f"警告：在指定的ID范围内找不到任何记录。start: {start} > end: {end}"
            return result
        
        # 正常读取数据
        for record in records:
            result['content'].append({
                "id": record[0],
                "user_name": record[1],
                "image_data": ToBase64(record[2], record[3]),
                "image_type": record[3],
                "upload_time": record[4]
            })

        return result

    def count_records(self) -> int:
        """
        获取 user_gallery 表中的记录总数
        
        :return:
            int: 记录总数
        """
        query = "SELECT COUNT(id) FROM user_gallery"
        self.cursor.execute(query)
        count = self.cursor.fetchone()[0]

        return count
