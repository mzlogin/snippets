import md5

def str_md5(str):
    m = md5.new()
    m.update(str)
    return m.hexdigest()

if __name__ == '__main__':
    while True:
        str = raw_input('input string to calc md5: ')
        print str_md5(str)
