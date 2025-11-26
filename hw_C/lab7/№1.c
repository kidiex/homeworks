#include <stdio.h>
#include <math.h>
#include <stdlib.h>

float funkc(float a, float b, float c, float x) {
        return a * (x * x) * sin(x) + b * x + c;
}

int main(int argc, char** argv) {
    float a, b, c, start, end, h;
    printf("Введите коэффицент а: \n");
    scanf("%f", &a);
    printf("Введите коэффицент b:\n");
    scanf("%f", &b);
    printf("Введите коэффицент c:\n");
    scanf("%f", &c);
    printf("Введите начало:\n");
    scanf("%f", &start);
    printf("Введите конец:\n");
    scanf("%f", &end);
    printf("Введите шаг:\n");
    scanf("%f", &h);
    if (((start > end) && (h > 0)) || ((start < end) && (h < 0))) {
        float m = start;
        start = end;
        end = m;
    }

    int len = abs((end - start) / h) + 1;
    float mas[len];
    int i = 0;
    while (start != end) {
        mas[i] = funkc(a, b, c, start);
        start += h;
        i++;
    }
    for (i = 0; len - 1; i++) {
        for (int j = len -2; j >= i; j--){
          if (mas[j] > mas[j+1]) {
            int l = mas[j];
            mas[j] = mas[j+1];
            mas[j+1] = l;
          }  
        } 
    }
        
        for (i = 0; i < len; i++) {
            printf("%f\n", mas[i]);
            return 0;
        }

    return 0;
}
