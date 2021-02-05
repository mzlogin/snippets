# @author: https://mazhuang.org
import sys
import re
import openpyxl
import requests
import imgkit
from bs4 import BeautifulSoup

# 1. 读取 Excel，得到 SKU -- CAS 映射
# 2. 依次处理，不抛异常
#   2.1 根据 CAS 号获取目标链接列表
#   2.2 依次打开目标链接，保存数据或截图
# 3. 输出处理结果统计

def print_usage():
    print("""
    usage: python3 main.py file.xlsx
    file.xlsx -- 原始数据表格
    """)

def fetch_cas(spec):
    pattern = re.compile(r'.*CAS.*?(\d{4,}-\d{2,}-\d{1,}).*')
    rematch = pattern.match(spec)
    if rematch is None:
        return None
    else:
        return rematch.group(1)

def fetch_sku_and_cas(src_filename):
    src_workbook = openpyxl.load_workbook(filename = src_filename, read_only = True)
    src_sheet = src_workbook[src_workbook.sheetnames[0]]
    first_line = True
    sku_cas_dict = {}
    for row in src_sheet.rows:
        if first_line:
            first_line = False
            continue
        sku = str(row[1].value).strip()
        spec = str(row[12].value).strip()
        sku_cas_dict[sku] = fetch_cas(spec)
    return sku_cas_dict

def process_sku(sku, cas):
    if cas is None:
        return '无 CAS'

    search_url = 'http://www.macklin.cn/search/%s'  % cas
    resp = requests.get(search_url)
    if resp.status_code != 200:
        return '搜索 CAS 失败'
    resp.encoding = resp.apparent_encoding

    soup = BeautifulSoup(resp.text, 'html.parser')
    link_list = soup.find_all(href=re.compile('/products/[A-Z]{1}\d{6}'))
    if link_list is None or len(link_list) == 0:
        return '搜索 CAS 没有结果'

    current = 1
    for link_item in link_list:
        link = link_item.get('href', None)
        if link is None:
            return '搜索到的结果链接为空'
        imgkit.from_url(link, '%s-%d.jpg' % (sku, current))
        current = current + 1
    
    return None

def process(src_filename):
    sku_cas_dict = fetch_sku_and_cas(src_filename)
    for sku, cas in sku_cas_dict.items():
        print("处理 %s, %s" % (sku, cas))
        result = process_sku(sku, cas)
        if result is None:
            print('处理成功')
        else:
            print('处理 %s 失败，原因：%s' % (sku, result))

if __name__ == '__main__':
    src_filename = ''

    if len(sys.argv) <= 1:
        print_usage()
        exit(1)
    else:
        src_filename = sys.argv[1]

    process(src_filename)