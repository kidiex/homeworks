#include <stdio.h>
#include <stdlib.h>
#define N 20
int main() {
float A[N]; 
float B[N] = {0}; 
int count_B = 0;

printf("Массив A:\n");
for (int i = 0; i < N; i++) {
    A[i] = (float)rand() / RAND_MAX * 20.0 -10.0;
    printf("A[%d] = %.2f\n", i, A[i]);
}


for (int i = 0; i < N; i++) {
    if (A[i] >= 0) {
        B[count_B] = A[i];
        count_B++;
    }    
    }
    printf("Массив B\n");
    for (int i = 0; i < count_B; i++) {
        
        printf("B[%d] = %.2f\n", i, B[i]);
    }
 }

