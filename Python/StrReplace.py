import re

def normal():
    a = 'Hello World!'
    print a.replace('World', 'Python')

def viaRegex():
    a = 'Hello World!'
    pattern = re.compile('World')
    print pattern.sub('Python', a)

if __name__ == '__main__':
    normal()
    viaRegex()
