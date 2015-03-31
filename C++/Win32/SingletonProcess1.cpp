#include <Windows.h>

#pragma data_seg("Shared")
volatile long g_lInstances = 0;
#pragma data_seg()

#pragma comment(linker, "/Section:Shared,RWS")

int main(int argc, char *argv[])
{
    if (g_lInstances != 0)
    {
        return -1;
    }

    InterlockedExchangeAdd(&g_lInstances, 1);

    // do something here
    // ...

    InterlockedExchangeAdd(&g_lInstances, -1);

    return 0;
}
