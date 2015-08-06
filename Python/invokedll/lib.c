// gcc lib.c -shared -o lib.dll
int __declspec(dllexport) add(int x, int y) {
    return x + y;
}
