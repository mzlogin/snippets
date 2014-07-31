#ifndef BUBBLESORT_H
#define BUBBLESORT_H

template<typename T>
void BubbleSort(T *arr, int n)
{
    bool bChange = true;
    for (int i = n - 1; bChange && i >= 0; i--)
    {
        bChange = false;
        for (int j = 0; j < i; j++)
        {
            if (arr[j] > arr[j+1])
            {
                T tmp = arr[j];
                arr[j] = arr[j+1];
                arr[j+1] = tmp;
                bChange = true;
            }
        }
    }
}
#endif // BUBBLESORT_H
