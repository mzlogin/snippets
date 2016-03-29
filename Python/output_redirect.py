import sys

class OutputRedirect:
    def __init__(self, filename):
        self.old_stdout = sys.stdout
        sys.stdout = open(filename, 'w')

    def __del__(self):
        sys.stdout = self.old_stdout
