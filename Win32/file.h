/**
@file : file.h
@author : ChumpMa#gmail.com
@brief : 对文件的常用操作的API使用示例
@version : 0.0.0.1
@date : 2013-10-28
*/

#pragma once

namespace MZWin32
{
	/** 递归遍历文件夹并将所有文件依次输出
	@param wszPath 要遍历的文件夹
	*/
	void RecursiveTraveralPath(const wchar_t* wszPath);
};
