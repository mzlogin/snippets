import requests

def text2code(text):
    '''
    convert a string to wave code
    '''
    ret = None
    get_wave_params = {'type' : 'text', 'content' : text}
    response = requests.post('http://rest.sinaapp.com/api/post', data=get_wave_params)
    if response.status_code == 200:
        try:
            data = response.json()
            ret = data['code']
        except: # json() may cause ValueError
            pass
    return ret

def code2text(code):
    '''
    convert a wave code to string
    '''
    ret = None
    get_text_params = {'code' : code}
    response = requests.get('http://rest.sinaapp.com/api/get', params=get_text_params)
    if (response.status_code == 200):
        try:
            data = response.json()
            ret = data['content']
        except:
            pass
    return ret

def main():
    text = 'Flame-Team'
    code = text2code(text)
    if code is not None:
        print text + ' to code is ' + code
    text_restore = code2text(code)
    if text_restore is not None:
        print code + ' to text is ' + text_restore

if __name__ == '__main__':
    main()
