import os

def Db2Sql(pathName):
    for dirPath, dirNames, fileNames in os.walk(pathName):
        for fileName in fileNames:
            if os.path.splitext(fileName)[1] == '.db':
                filePath = os.path.join(dirPath, fileName)
                sqlPath = filePath + '.sql'
                cmd = 'sqlite3 %s .dump > %s' % (filePath, sqlPath)
                os.system(cmd)

if __name__ == '__main__':
    pathName = raw_input('input path: ')
    Db2Sql(pathName)
