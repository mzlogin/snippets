#coding: utf-8
import requests
### require pycrypto module
from Crypto.Cipher import AES
import md5
import time

def str_md5(str1):
    m = md5.new()
    m.update(str1)
    return m.hexdigest()

def aes_enc(content, key):
    cipher = AES.new(key)
    return cipher.encrypt(pad(content)).encode('hex').upper()

def aes_dec(content, key):
    cipher = AES.new(key)
    return unpad(cipher.decrypt(content.decode('hex')))

BS = AES.block_size
pad =lambda s: s +(BS - len(s)% BS)* chr(BS - len(s)% BS)
unpad =lambda s : s[0:-ord(s[-1])]

partkey = 'smali'
sign = str_md5('%s3edczxcv' % partkey)

content = 'partkey=%s&sign=%s' % (partkey, sign)
key = 'bhu8nhy6!QAZ@WSX'

url = 'http://2935cc63796d47603.jie.sangebaimao.com/genkey.php'
data = aes_enc(content, key)

response = requests.post(url, data)

print aes_dec(response.text, key)

id = '1'
sign = str_md5('%s3edczxcv' % id)

content = 'id=%s&sign=%s&dateline=%d' % (id, sign, int(time.time()))
key = 'bhu8nhy6!QAZ@WSX'

url = 'http://2935cc63796d47603.jie.sangebaimao.com/data.php'
data = aes_enc(content, key)

response = requests.post(url, data)

print aes_dec(response.text, key)
