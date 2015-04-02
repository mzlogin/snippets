#include <Windows.h>

int main(int argc, char *argv[])
{
    HANDLE hMutex = NULL;

    do
    {
        hMutex = CreateMutex(NULL, FALSE, "Global\\73E21C80-1960-472F-BF0B-3EE7CC7AF17E");

        DWORD dwError = GetLastError();

        if (ERROR_ALREADY_EXISTS == dwError || ERROR_ACCESS_DENIED == dwError) 
        {
            break;
        }

        // do something here
        // ...

    } while (false);

    if (NULL != hMutex) 
    {
        CloseHandle(hMutex);
    }

    return 0;
}
