# @author: https://mazhuang.org
import os
from jtyoui.imagepdf import image

def pdf_to_image(pdf_file_path):
    dir_name, base_name = image._get_dir_name(pdf_file_path)
    dir_name = dir_name + os.sep + str(base_name[:-4])
    if not os.path.exists(dir_name):
        os.mkdir(dir_name)
    image.pdf_to_image(pdf_file_path, dir_name)

if __name__ == '__main__':
    pdf_dir = input('请拖入 PDF 所在文件夹：')
    pdf_dir = pdf_dir.strip()
    for dir_path, dir_names, file_names in os.walk(pdf_dir):
        for file_name in file_names:
            if os.path.splitext(file_name)[1] == '.pdf':
                pdf_file_path = os.path.join(dir_path, file_name)
                print('处理文件 %s' % pdf_file_path)
                pdf_to_image(pdf_file_path)
    print('处理完毕')