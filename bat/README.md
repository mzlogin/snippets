# 自定义命令

**目录**

<!-- vim-markdown-toc GFM -->
* [strmd5](#strmd5)
* [base64](#base64)
* [timestamp](#timestamp)
* [say](#say)

<!-- vim-markdown-toc -->

## strmd5

计算字符串 md5 值。

```sh
usage:
        strmd5 <string>
```

## base64

加/解密 base64。

```sh
usage:
        base64 "<string>" <-e|-b>
```

`-e` 表示加密，`-b` 表示解密，字符串要使用双引号包围传入。

## timestamp

输出当前时间戳。

```sh
usage:
        timestamp
```

## say

朗诵传入的句子。

```sh
usage:
        say hello
        say "hello world"
        say "你好 世界"
```
