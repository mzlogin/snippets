import os

def pathTraversal(pathName):
    for dirPath, dirNames, fileNames in os.walk(pathName):
        for fileName in fileNames:
            # if os.path.splitext(fileName)[1] == '.txt':
            filePath = os.path.join(dirPath, fileName)
            print filePath

if __name__ == '__main__':
    pathName = raw_input('input path: ')
    pathTraversal(pathName)
