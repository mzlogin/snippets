/**
@file : FileRelation.h
@author : ChumpMa#gmail.com
@website: http://www.mazhuang.org
@brief : 检测/设置/取消文件关联
@version : 0.0.0.1
@date : 2014-01-08
*/

#ifndef FILE_RELATION_H
#define FILE_RELATION_H

#include <Windows.h>
#include <ShlObj.h>
#include <Shlwapi.h>

#pragma comment(lib, "shlwapi")

/**
 检测指定文件扩展名的程序关联情况
 @param [in] wszExt 文件扩展名
 @param [in] wszTypeKey 注册文件类型
 @param [out] bRet 已成功关联返回true，未关联返回false
 @return 检测成功返回true，检测出错返回false
 */
bool CheckFileRelation(const WCHAR *wszExt, const WCHAR *wszTypeKey, bool& bRet)
{
	bool bSucceed = false;
	bRet = false;
	HKEY hExtKey;
	WCHAR szPath[_MAX_PATH] = {0}; 
	DWORD dwSize=sizeof(szPath); 

	WCHAR szSubKey[MAX_PATH] = {0};
	swprintf_s(szSubKey, L"Software\\Classes\\%s", wszExt);
	LONG lRet = RegOpenKeyEx(HKEY_CURRENT_USER, szSubKey, 0, KEY_QUERY_VALUE, &hExtKey);

	if (ERROR_SUCCESS == lRet)
	{
		memset(szPath, 0, _MAX_PATH * sizeof(WCHAR));
		lRet = RegQueryValueEx(hExtKey, NULL, NULL, NULL,(LPBYTE)szPath,&dwSize);
		if (ERROR_SUCCESS == lRet)
		{
			bSucceed = true;
			if (0 == _wcsicmp(szPath,wszTypeKey))
			{
				bRet = true;
			}
		}
		RegCloseKey(hExtKey);
	}

	OSVERSIONINFOEX osvi = { 0 };
	osvi.dwOSVersionInfoSize = sizeof(OSVERSIONINFOEX);
	BOOL bOSRet = GetVersionEx ((OSVERSIONINFO *) &osvi);
	if(bOSRet && (VER_PLATFORM_WIN32_NT == osvi.dwPlatformId))
	{
		memset(szSubKey, 0, MAX_PATH * sizeof(WCHAR));
		if (osvi.dwMajorVersion <= 5)
		{
			swprintf_s(szSubKey, L"Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\FileExts\\%s", wszExt);
		}
		else
		{
			swprintf_s(szSubKey, L"Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\FileExts\\%s\\UserChoice", wszExt);
		}

		lRet = RegOpenKeyEx(HKEY_CURRENT_USER, szSubKey, 0, KEY_QUERY_VALUE, &hExtKey);

		if (ERROR_SUCCESS == lRet)
		{
			memset(szPath, 0, _MAX_PATH * sizeof(WCHAR));
			lRet = RegQueryValueEx(hExtKey, L"Progid", NULL, NULL,(LPBYTE)szPath,&dwSize);
			if (ERROR_SUCCESS == lRet)
			{
				if ((0 != wcslen(szPath)) &&(0 != _wcsicmp(szPath,wszTypeKey)))
				{
					bRet = false;
				}
			}
			RegCloseKey(hExtKey);
		}
	}

	return bSucceed;
}

/** 
 关联文件类型打开方式、右键菜单、图标
 @param [in] wszExt 文件扩展名： .txt，.md  带“."
 @param [in] wszAppPath 用于打开wszExt类型文件的程序
 @param [in] wszTypeKey 注册文件类型，如txtfiles
 @param [in] wszIconPath 用于设置wszExt类型文件的默认图标的ICO文件路径
 @param [in] wszFileDescribe 用于wszExt类型文件的文件类型描述
 @param [in] wszRClickDescribe 用于wszExt类型文件的右键菜单项描述
 */
void RegisterFileRelation(
	const WCHAR* wszExt, 
	const WCHAR* wszAppPath, 
	const WCHAR* wszTypeKey,
	const WCHAR* wszIconPath, 
	const WCHAR* wszFileDescribe, 
	const WCHAR* wszRClickDescribe)
{
	WCHAR wszTemp[MAX_PATH];
	HKEY hKey;

	memset(wszTemp, 0, MAX_PATH * sizeof(WCHAR));
	wsprintf(wszTemp, L"Software\\Classes\\%s", wszExt);
	if (ERROR_SUCCESS == RegCreateKey(HKEY_CURRENT_USER,wszTemp,&hKey))
	{
		RegSetValue(hKey, L"",REG_SZ,wszTypeKey,wcslen(wszTypeKey)+1);
		RegCloseKey(hKey);
	}

	memset(wszTemp, 0, MAX_PATH * sizeof(WCHAR));
	wsprintf(wszTemp, L"Software\\Classes\\%s", wszTypeKey);
	if (ERROR_SUCCESS == RegCreateKey(HKEY_CURRENT_USER,wszTemp,&hKey))
	{
		RegSetValue(hKey, L"",REG_SZ,wszFileDescribe,wcslen(wszFileDescribe)+1);
		RegCloseKey(hKey);
	}

	memset(wszTemp, 0, MAX_PATH * sizeof(WCHAR));
	wsprintf(wszTemp, L"Software\\Classes\\%s\\DefaultIcon",wszTypeKey);
	if (ERROR_SUCCESS == RegCreateKey(HKEY_CURRENT_USER,wszTemp,&hKey))
	{
		RegSetValue(hKey, L"",REG_SZ,wszIconPath,wcslen(wszIconPath)+1);
		RegCloseKey(hKey);
	}

	memset(wszTemp, 0, MAX_PATH * sizeof(WCHAR));
	wsprintf(wszTemp, L"Software\\Classes\\%s\\Shell",wszTypeKey);
	if (ERROR_SUCCESS == RegCreateKey(HKEY_CURRENT_USER,wszTemp,&hKey))
	{
		RegSetValue(hKey, L"",REG_SZ, L"Open", wcslen(L"Open")+1);
		RegCloseKey(hKey);
	}

	memset(wszTemp, 0, MAX_PATH * sizeof(WCHAR));
	wsprintf(wszTemp, L"Software\\Classes\\%s\\Shell\\Open", wszTypeKey);
	if (ERROR_SUCCESS == RegCreateKey(HKEY_CURRENT_USER, wszTemp, &hKey))
	{
		RegSetValue(hKey, L"", REG_SZ, wszRClickDescribe, wcslen(wszRClickDescribe) + 1);
		RegCloseKey(hKey);
	}

	memset(wszTemp, 0, MAX_PATH * sizeof(WCHAR));
	wsprintf(wszTemp, L"Software\\Classes\\%s\\Shell\\%s\\Command",wszTypeKey, L"Open");
	if (ERROR_SUCCESS == RegCreateKey(HKEY_CURRENT_USER,wszTemp,&hKey))
	{
		memset(wszTemp, 0, MAX_PATH * sizeof(WCHAR));
		wsprintf(wszTemp, L"\"%s\" \"%%1\"",wszAppPath);
		RegSetValue(hKey, L"",REG_SZ,wszTemp,wcslen(wszTemp)+1);
		RegCloseKey(hKey);
	}

	memset(wszTemp, 0, MAX_PATH * sizeof(WCHAR));
	wsprintf(wszTemp, L"Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\FileExts\\%s", wszExt);
	if (ERROR_SUCCESS == RegCreateKey(HKEY_CURRENT_USER, wszTemp, &hKey))
	{
		RegSetValueEx(hKey, L"Progid", 0, REG_SZ, (LPBYTE)wszTypeKey, wcslen(wszTypeKey) * sizeof(WCHAR));
		RegCloseKey(hKey);
	}

	memset(wszTemp, 0, MAX_PATH * sizeof(WCHAR));
	wsprintf(wszTemp, L"Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\FileExts\\%s\\OpenWithProgids", wszExt);
	if (ERROR_SUCCESS == RegCreateKey(HKEY_CURRENT_USER, wszTemp, &hKey))
	{
		RegSetValueEx(hKey, wszTypeKey, 0, REG_SZ, NULL, NULL);
		RegCloseKey(hKey);
	}

	memset(wszTemp, 0, MAX_PATH * sizeof(WCHAR));
	wsprintf(wszTemp, L"Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\FileExts\\%s\\UserChoice", wszExt);
	if (ERROR_SUCCESS == RegCreateKey(HKEY_CURRENT_USER, wszTemp, &hKey))
	{
		RegSetValueEx(hKey, L"Progid", 0, REG_SZ, (LPBYTE)wszTypeKey, wcslen(wszTypeKey) * sizeof(WCHAR));
		RegCloseKey(hKey);
	}

	SHChangeNotify(SHCNE_ASSOCCHANGED, SHCNF_IDLIST, NULL, NULL); 
}

/** 
 上面RegisterFileRelation的反函数，参数解释
 @see RegisterFileRelation
 */
void UnRegisterFileRelation(
	const WCHAR* wszExt,
	const WCHAR* wszTypeKey,
	const WCHAR* wszRClickDescribe)
{
	WCHAR wszTemp[MAX_PATH];
	HKEY hKey;
	LONG lRet;
	DWORD dwSize;

	memset(wszTemp, 0, MAX_PATH * sizeof(WCHAR));
	wsprintf(wszTemp, L"Software\\Classes\\%s", wszExt);
	if (ERROR_SUCCESS == RegOpenKey(HKEY_CURRENT_USER,wszTemp,&hKey))
	{
		wmemset(wszTemp, 0, MAX_PATH);
		lRet = RegQueryValueEx(hKey, NULL, NULL, NULL, (LPBYTE)wszTemp, &dwSize);
		if (ERROR_SUCCESS == lRet)
		{
			if (0 == _wcsicmp(wszTemp, wszTypeKey))
			{
				RegSetValue(hKey, L"",REG_SZ,L"",wcslen(L"")+1);
			}
		}
		RegCloseKey(hKey);
	}

	wmemset(wszTemp, 0, MAX_PATH);
	wsprintf(wszTemp, L"Software\\Classes\\%s\\DefaultIcon",wszTypeKey);
	RegDeleteKey(HKEY_CURRENT_USER, wszTemp);

	memset(wszTemp, 0, MAX_PATH * sizeof(WCHAR));
	wsprintf(wszTemp, L"Software\\Classes\\%s\\Shell\\Open\\Command", wszTypeKey);
	RegDeleteKey(HKEY_CURRENT_USER, wszTemp);
	PathRemoveFileSpec(wszTemp);
	RegDeleteKey(HKEY_CURRENT_USER, wszTemp);
	PathRemoveFileSpec(wszTemp);
	RegDeleteKey(HKEY_CURRENT_USER, wszTemp);
	PathRemoveFileSpec(wszTemp);
	RegDeleteKey(HKEY_CURRENT_USER, wszTemp);

	memset(wszTemp, 0, MAX_PATH * sizeof(WCHAR));
	wsprintf(wszTemp, L"Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\FileExts\\%s", wszExt);
	if (ERROR_SUCCESS == RegOpenKey(HKEY_CURRENT_USER, wszTemp, &hKey))
	{
		wmemset(wszTemp, 0, MAX_PATH);
		lRet = RegQueryValueEx(hKey, L"Progid", NULL, NULL, (LPBYTE)wszTemp, &dwSize);
		if (ERROR_SUCCESS == lRet)
		{
			if (0 == _wcsicmp(wszTemp, wszTypeKey))
			{
				RegSetValueEx(hKey, L"Progid", 0, REG_SZ, (LPBYTE)L"", wcslen(L"") * sizeof(WCHAR));
			}
		}
		RegCloseKey(hKey);
	}

	memset(wszTemp, 0, MAX_PATH * sizeof(WCHAR));
	wsprintf(wszTemp, L"Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\FileExts\\%s\\OpenWithProgids", wszExt);
	if (ERROR_SUCCESS == RegOpenKey(HKEY_CURRENT_USER, wszTemp, &hKey))
	{
		RegDeleteValue(hKey, wszTypeKey);
		RegCloseKey(hKey);
	}

	memset(wszTemp, 0, MAX_PATH * sizeof(WCHAR));
	wsprintf(wszTemp, L"Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\FileExts\\%s\\UserChoice", wszExt);
	if (ERROR_SUCCESS == RegOpenKey(HKEY_CURRENT_USER, wszTemp, &hKey))
	{
		wmemset(wszTemp, 0, MAX_PATH);
		lRet = RegQueryValueEx(hKey, L"Progid", NULL, NULL, (LPBYTE)wszTemp, &dwSize);
		if (ERROR_SUCCESS == lRet)
		{
			if (0 == _wcsicmp(wszTemp, wszTypeKey))
			{
				RegSetValueEx(hKey, L"Progid", 0, REG_SZ, (LPBYTE)L"", wcslen(L"") * sizeof(WCHAR));
			}
		}
		RegCloseKey(hKey);
	}

	SHChangeNotify(SHCNE_ASSOCCHANGED, SHCNF_IDLIST, NULL, NULL); 
}

#endif // FILE_RELATION_H
