import md5

def str_md5(str):
    m = md5.new()
    m.update(str)
    return m.hexdigest()

if __name__ == '__main__':
    print str_md5("com.sohu.inputmethod.sogou")
