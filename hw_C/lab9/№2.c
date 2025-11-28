#include <stdio.h>
#include <stdlib.h>
#include <time.h>
int main() {
srand(time(NULL));
int N = 5;
float arr[N][N];
 
for (int i = 0; i < N; i++) {
    for (int j = 0; j < N; j++) {
        arr[i][j] = (float)(rand() % 11 );
    }
}

for (int i = 0; i < N; i++) {
    float sum = 0.0f;
    for(int j = 0; j < N; j++) {
        sum += arr[i][j];
    }
    for (int j = 0; j < N; j++) {
        arr[i][j] /= sum;
    }
        
}

for (int i = 0; i < N; i++) {
    for (int j = 0; j < N; j++) {
        printf(" | %.2f ", arr[i][j]);
    }
    printf("|\n");
}
 
return 0;
}
