#include "file.h"
#include <stdio.h>
#include <string.h>
#include <Windows.h>
#include <Shlwapi.h>

void MZWin32::RecursiveTraveralPath(const wchar_t* wszPath)
{
	wchar_t wszFind[MAX_PATH] = {0};
	wchar_t wszFindBack[MAX_PATH] = {0};
	wcscpy_s(wszFind, wszPath);
	PathRemoveBackslash(wszFind);
	wcscpy_s(wszFindBack, wszFind);
	wcscat_s(wszFind, L"\\*.*");

	WIN32_FIND_DATA fd;
	HANDLE hFind = ::FindFirstFile(wszFind, &fd);
	if (INVALID_HANDLE_VALUE != hFind)
	{
		do 
		{ 
			if (0 == wcscmp(fd.cFileName, L".") || 0 == wcscmp(fd.cFileName, L".."))
			{
				continue;
			}

			if ((fd.dwFileAttributes & FILE_ATTRIBUTE_DIRECTORY) == FILE_ATTRIBUTE_DIRECTORY)
			{
				wchar_t wszChildPath[MAX_PATH] = {0};
				swprintf_s(wszChildPath, L"%s\\%s", wszFindBack, fd.cFileName);
				RecursiveTraveralPath(wszChildPath);
			}
			else
			{
				wprintf_s(L"%s\\%s\n", wszFindBack, fd.cFileName);
			}
		} while (FindNextFile(hFind, &fd));
		FindClose(hFind);
	}
}
