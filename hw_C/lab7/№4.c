#include <stdio.h>
#include <stdlib.h>
#define N 20
int main() {
    int i = 0,j = 0,c = 0, C[N], D[N], E[2*N] = {0};
    
    for (int i = 0; i < N; i++) {   // Забиваю массив С  
        C[i] = rand() % 21;
    }
    
    for ( i = 0; i < N-1; i ++ ) // по методу пузырька сортирую массив С
    for ( j = N-2; j >= i; j -- )  
      if ( C[j] > C[j+1] ) 
      {
        c = C[j]; 
        C[j] = C[j+1]; 
        C[j+1] = c;
      }
     

    for (int i = 0; i < N; i++) {   // Забиваю массив D 
        D[i] = rand() % 21;
    }
    for ( i = 0; i < N-1; i ++ ) // по методу пузырька сортирую массив D
    for ( j = N-2; j >= i; j -- )  
      if ( D[j] > D[j+1] ) 
      {
        c = D[j]; 
        D[j] = D[j+1]; 
        D[j+1] = c;
      }
    
i = 0; j = 0; c = 0; // сброс
    while (i < N && j < N) {  // Объеденяю 
       if (C[i] <= D[j]) {
        E[c] = C[i];
        i++;
       } else {
        E[c] = D[j];
        j++;
    }
    c++;
  }
  while (i < N) {
        E[c] = C[i];
        i++;
        c++;
    }
    
    while (j < N) {
        E[c] = D[j];
        j++;
        c++;
    }

  printf("Массив С = \n");
  for (i = 0; i < N; i++) {
    printf("%d\n ", C[i]);
  }   
  
  printf("Массив D = \n");
  for (i = 0; i < N; i++) {
    printf("%d\n ", D[i]);
  } 

  printf("Оъедененный массив Е = \n");
  for (i = 0; i < 2*N; i++) {
    printf("%d\n ", E[i]);
  } 
}
    
    
