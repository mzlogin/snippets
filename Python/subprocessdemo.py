import subprocess

p = subprocess.Popen(['python', 'PathTraversal.py'], stdin=subprocess.PIPE ,shell=False)
p.communicate(input='D:/HelloWorld')
p.wait()
