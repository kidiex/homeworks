#include <stdio.h>
#include <stdlib.h>
#include <math.h>

float funkc (float x) {
    return (2.5 * pow(x, 2) - 0.1) / (tan(x) + sin(x));
}

int main () {
    int N;
    float a = 4.0, b = 6.0;
    
    printf("Введите N: ");
    scanf("%d", &N);
    
    float arr[N+1];
    float h = (b-a)/N;
    int i = 0;
    
    
    for (; a <= b; a += h, i++) arr[i] = funkc(a);
    float I1, I2;

    for (i = 1; i <= N-1; i++) {
        I1 += arr[i];
    }
    I1 *= h;
    
    float sum_odd, sum_even;
    for (i = 1; i <= N-1; i += 2) {
        sum_odd += arr[i];
    }
    for (i = 2; i <= N - 2; i += 2) {
        sum_even += arr[i];
    }
    I2 = h / 3 * (arr[0] + arr[N] + 4 * sum_odd + 2 * sum_even);

    I2 = fabs(I2);
    I1 = fabs(I1);
    printf("По трапеций: %.5f , По симпсона: %.5f \n", I1, I2);


    return 0;
}
