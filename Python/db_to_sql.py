import os

'''
dump all .db files in path_name dir to sql
'''
def db_to_sql(path_name):
    for dir_path, dir_names, file_names in os.walk(path_name):
        for file_name in file_names:
            if os.path.splitext(file_name)[1] == '.db':
                file_path = os.path.join(dir_path, file_name)
                sql_path = file_path + '.sql'
                cmd = 'sqlite3 %s .dump > %s' % (file_path, sql_path)
                os.system(cmd)

if __name__ == '__main__':
    path_name = raw_input('input path: ')
    db_to_sql(path_name)
