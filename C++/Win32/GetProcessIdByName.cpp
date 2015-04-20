#include <Windows.h>
#include <TlHelp32.h>
#include <tchar.h>

BOOL GetProcessIdByName(_TCHAR* pszName, DWORD& dwPid) 
{
	BOOL bGet = FALSE;
	HANDLE hSnapshot = CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS, 0);
	if (INVALID_HANDLE_VALUE != hSnapshot)
	{
		PROCESSENTRY32 pe = {0};
		pe.dwSize = sizeof(PROCESSENTRY32);
		BOOL bRet = Process32First(hSnapshot, &pe);
		while (bRet)
		{
			if (0 == _tcsicmp(pszName, pe.szExeFile))
			{
				bGet = TRUE;
				dwPid = pe.th32ProcessID;
				break;
			}

			bRet = Process32Next(hSnapshot, &pe);
		}
	}
	return bGet;
}

int _tmain(int argc, _TCHAR* argv[])
{
	DWORD dwPid;

	if (GetProcessIdByName(_T("explorer.exe"), dwPid))
	{
		_tprintf(_T("explorer.exe pid is %d\n"), dwPid);
	}

	system("pause");

	return 0;
}
