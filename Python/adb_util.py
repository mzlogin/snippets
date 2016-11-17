#-*- encoding: utf-8 -*-

import os
import sys
import getopt
import re
import math

class AdbUtil(object):

    def __init__(self, device=''):
        self.device = device
        if self.device == '' or self.device == None:
            self.cmd_prefix = 'adb'
        else:
            self.cmd_prefix = 'adb -s %s' % self.device

    def touch(self, coordinate):
        cmd = '%s shell input tap %d %d' % (self.cmd_prefix, coordinate[0], coordinate[1])
        os.system(cmd)

    def get_resolution(self):
        pattern = re.compile(r'.* (\d+)x(\d+).*')
        cmd = '%s shell wm size' % self.cmd_prefix
        proc = os.popen(cmd)
        output = ''.join(proc.readlines())
        proc.close()
        rematch = pattern.match(output)
        if rematch is not None:
            resolution = [int(rematch.group(1)), int(rematch.group(2))]
            return resolution
        else:
            return None

    def get_density(self):
        pattern = re.compile(r'Physical density: (\d+)')
        cmd = '%s shell wm density' % self.cmd_prefix
        proc = os.popen(cmd)
        output = ''.join(proc.readlines())
        proc.close()
        rematch = pattern.match(output)
        if rematch is not None:
            density = int(rematch.group(1))
            return density
        else:
            return None

    def get_screen_size(self):
        '''
        notice: not exactly
        '''
        resolution = self.get_resolution()
        if resolution is None:
            return None

        density = self.get_density()
        if density is None:
            return None

        return (math.sqrt(math.pow(resolution[0],2)+math.pow(resolution[1],2))/density)

def usage():
    print("""
          usage: python adb_util.py [options]

          Available options:

          -s | --size       get screen size in Inch
          -r | --resolution get resolution
          -d | --density    get density
          """)

def main():
    try:
        opts, args = getopt.getopt(sys.argv[1:], 'srd', ['size', 'resolution', 'density'])
    except getopt.GetoptError as err:
        opts = []

    if len(opts) == 0:
        usage()
        opts = [('-s', '')]

    adb = AdbUtil()

    for o, a in opts:
        if o in ('-s', '--size'):
            size = adb.get_screen_size()
            if size is None:
                print('get screen size failed')
            else:
                print('screen size is %f inch' % size)
        elif o in ('-r', '--resolution'):
            resolution = adb.get_resolution()
            if resolution is None:
                print('get resolution failed')
            else:
                print('resolution is %dx%d' % (resolution[0], resolution[1]))
        elif o in ('-d', '--density'):
            density = adb.get_density()
            if density is None:
                print('get density failed')
            else:
                print('density is %d' % density)

if __name__ == '__main__':
    main()
