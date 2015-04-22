#include <Windows.h>
#include <TlHelp32.h>
#include <tchar.h>

void TraverseProcessWithTlHelp32() 
{
	HANDLE hSnapshot = CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS, 0);
	if (INVALID_HANDLE_VALUE == hSnapshot)
	{
		_tprintf(_T("CreateToolhelp32Snapshot failed\n"));
	}
    else
    {
        PROCESSENTRY32 pe = {0};
        pe.dwSize = sizeof(PROCESSENTRY32);
        BOOL bRet = Process32First(hSnapshot, &pe);
        while (bRet)
        {
            _tprintf(_T("PID is %d, PNAME is %s\n"), pe.th32ProcessID, pe.szExeFile);
            bRet = Process32Next(hSnapshot, &pe);
        }
        CloseHandle(hSnapshot);
    }
}

int _tmain(int argc, _TCHAR* argv[])
{
	TraverseProcessWithTlHelp32();

	system("pause");

	return 0;
}
