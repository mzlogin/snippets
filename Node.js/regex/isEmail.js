function isEmail(str){ 
  var reg = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+\.){1,63}[a-z0-9]+$/; 
  return reg.test(str); 
} 

var emails = [
  '1111',
  '222@foxmail.com',
  '3@qq.com',
  undefined,
  '',
  ' '
];

for (var i = 0; i < emails.length; i++) {
  console.log(isEmail(emails[i]));
}
