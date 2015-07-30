import md5
import ctypes

def str_md5(str1):
    m = md5.new()
    m.update(str1)
    return m.hexdigest()

if __name__ == '__main__':
    while True:
        str1 = raw_input('input string to calc md5: ')
        str1md5 = str_md5(str1)
        print 'md5 is: ' + str1md5
        high16 = str1md5[0:16]
        print 'high16 raw is: ' + high16
        print 'high16 is: %d' % ctypes.c_int64(int(high16, 16)).value
