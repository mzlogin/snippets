from ctypes import *

dll = CDLL('lib.dll')
print dll.add(1, 2)
