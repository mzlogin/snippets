'http://stackoverflow.com/questions/1040655/ms-speech-from-command-line/1040825#1040825

set s = CreateObject("SAPI.SpVoice")
s.Speak Wscript.Arguments(0), 3
s.WaitUntilDone(1000)
