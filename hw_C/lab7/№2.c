#include <stdio.h>
#include <stdlib.h>
#define N 15
int main()
{
 int A[N]; int B[N] = {0};
 for(int i = 0; i < N; i++){
   A[i]  = rand()%11;
 }
 for (int i = 0; i < N; i++){
     if (B[A[i]] == 0) {
         printf("%d ", A[i]);
         B[A[i]] = 1;
     }
     }
}
