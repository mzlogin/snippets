<?php
/*
 * 用法： 
 * 将本文件放在 discuz 的根目录下，访问
 * http://discuz_url/try_register_and_login_discuz.php?username=username&password=password&email=email
 * 
 * 如果 username 存在，且 password 正确，则自动登录；
 * 如果 username 不存在，则自动使用 [username, password, email] 注册，并自动登录；
 * 其它情况直接跳转到 discuz 首页。
 */

define('APPTYPEID', 0);
define('CURSCRIPT', 'register');

require_once './source/class/class_core.php';
require_once './source/function/function_member.php';
require_once './config/config_ucenter.php';
require_once './uc_client/client.php';

$discuz = C::app();
$discuz->init();
loaducenter();

$username = isset($_GET['username']) ? $_GET['username'] : '';
$password = isset($_GET['password']) ? $_GET['password'] : '';
$email = isset($_GET['email']) ? $_GET['email'] : '';

// 先尝试登录，用户不存在则注册后再登录
if ($username == '' || $password == '' || $email == '') {
} else {
  $uid = mz_login($username, $password);
  if ($uid == -1) {
    $uid = mz_register($username, $password, $email);
    if ($uid > 0) {
      $uid = mz_login($username, $password);
    }
  } else if ($uid == -2) {
  }
}
header('Location: ./forum.php');

function mz_login($username, $password) {
  $userdata = uc_user_login($username, $password);
  list($uid, $username, $password, $email) = $userdata;
  if ($uid > 0) {
    // 登录成功
    echo uc_user_synlogin($uid);
    $arr_user = userlogin($username, $password);
    setloginstatus($arr_user['member']);
  } else {
    // 登录失败
    // -1 用户不存在
    // -2 密码错误
  }
  return $uid;
}

function mz_register($username, $password, $email) {
  $uid = uc_user_register($username, $password, $email);
  if ($uid > 0) {
    // 注册成功
    $init_arr = array(0,0,0,0,0,0,0,0,0);
    $groupid = 10;
    C::t('common_member')->insert($uid, $username, md5(random(10)), $email, $_G['clientip'], $groupid, $init_arr);
  } else {
    // 注册失败
    // -1 用户名不合法
    // -2 包含不允许注册的词语
    // -3 用户名已经存在
    // -4 Email 格式有误
    // -5 Email 不允许注册
    // -6 Email 已经被注册
  }
  return $uid;
}
?>
