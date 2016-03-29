#!/usr/bin/env python
# -*- coding: utf-8 -*-
import hashlib

def calc_sha1(str1):
    sha1obj = hashlib.sha1()
    sha1obj.update(str1)
    sha1str = sha1obj.hexdigest()
    return sha1str

def main():
    str1 = raw_input('input str to calc sha1:')
    result = calc_sha1(str1)
    print(result)

if __name__ == '__main__':
    main()
