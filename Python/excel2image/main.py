# @author: https://mazhuang.org
import sys
import os
import imgkit
import openpyxl

def print_usage():
    print("""
    usage: python3 main.py file.xlsx
    file.xlsx -- 原始数据表格
    每个 Sheet 内必须有一列叫「SKU编码」
    """)


# 在 Windows 下 font-family 设置为 Souce Han Sans CN 字体不生效，用 思源黑体 CN 在 Windows 和 macOS 下均可
def get_template():
    return """
<!DOCTYPE html>
<html>

<head>
    <meta charset='UTF-8'>
    <style>
        body {
            margin: 0;
        }

        table {
            width: 780px;
            margin: 10px;
            border-right: 2px solid #000;
            border-bottom: 2px solid #000;
            border-collapse: collapse;
        }

        table tr td {
            padding: 4px;
            border-left: 2px solid #000;
            border-top: 2px solid #000;
            font-size: 19px;
            line-height: 28px;
            font-family: "思源黑体 CN", sans-serif;
        }

        table tr td:first-child {
            background-color: #f2f2f2;
            width: 220px;
            font-weight: bold;
        }
    </style>
</head>

<body>

    <table cellspacing="0" cellpadding="0">
    %s
    </table>

</body>

</html>
"""

def process(src_filename):
    src_workbook = openpyxl.load_workbook(filename = src_filename, read_only=True)
    for sheetname in src_workbook.sheetnames:
        src_sheet = src_workbook[sheetname]
        first_line = True
        headers = []
        sku_col = 0
        if not os.path.exists(sheetname):
            os.mkdir(sheetname)
        for row in src_sheet.rows:
            values = [str(cell.value).strip().replace('\n', '<br>') if cell.value else '' for cell in row]
            if first_line:
                first_line = False
                headers = values
                sku_col = headers.index('SKU编码')
                continue
            save_image(sheetname, headers, values, sku_col)

def save_image(sheetname, headers, values, sku_col):
    sku = values[sku_col]
    print('处理 %s 里的 %s' % (sheetname, sku))
    body = ""
    for i in range(len(headers)):
        if i == sku_col:
            continue
        if values[i] == '':
            continue
        body += "<tr><td>%s</td><td>%s</td></tr>" % (headers[i], values[i])
    html = get_template() % body
    options = {
        'width': 800,
        'encoding': 'UTF-8'
    }
    # html_file_name = "%s%s%s.html" % (sheetname, os.sep, sku)
    # html_file = open(html_file_name, 'w+', encoding='utf-8')
    # html_file.write(html)
    # html_file.close
    imgkit.from_string(html, '%s%s%s.jpg' % (sheetname, os.sep, sku), options)

if __name__ == '__main__':
    src_filename  =''

    if len(sys.argv) <= 1:
        print_usage()
        # src_filename = '参数.xlsx'
        exit(1)
    else:
        src_filename = sys.argv[1]
    
    process(src_filename)
