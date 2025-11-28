#include <stdio.h>
#include <stdlib.h>
#include <time.h>
int main() {
srand(time(NULL));
int N = 5;
int arr[N][N];

for (int i = 0; i < N; i++) {
    for (int j = 0; j < N; j++) {
        arr[i][j] = rand() % 10;
    }
}

printf("Изначальный:\n");

for (int i = 0; i < N; i++) {
    for (int j = 0; j < N; j++) {
        printf(" | %d ", arr[i][j]);
    }
    printf("|\n");
}

printf("Транспонированный:\n");

for (int i = 0; i < N; i++) {
    for (int j = 0; j < N; j++) {
        printf(" | %d ", arr[j][i]);
    }
    printf("|\n");
}
return 0;
}
