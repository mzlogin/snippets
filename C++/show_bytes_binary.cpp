#include <stdio.h>
#include <string.h>

typedef unsigned char byte;

template<typename T>
void show_bytes(T* p, size_t len)
{
    byte* pb = (byte*)p;
    for (int i = 0; i < len; i++)
    {
        printf("%.2x ", pb[i]);
    }
    printf("\n");
}

template<typename T>
void show_binary(T* p, size_t len)
{
    byte* pb = (byte*)p;
    for (int i = 0; i < len; i++)
    {
        for (int j = 8 - 1; j >= 0; j--)
        {
            byte b = (byte)1;
            b = b << j;
            b = b & pb[i];
            printf("%d", (bool)b);
        }
        printf(" ");
    }
    printf("\n");
}

int main(int argc, char *argv[])
{
    float f1 = 20;
    double d1 = 20;
    const char* p = "hello";
    show_bytes(&f1, sizeof(float));
    show_binary(&f1, sizeof(float));
    show_bytes(&d1, sizeof(double));
    show_binary(&d1, sizeof(double));
    show_bytes(p, strlen(p));
    show_binary(p, strlen(p));
    show_binary(p, strlen(p));
    return 0;
}
