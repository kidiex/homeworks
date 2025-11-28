#include <stdio.h>
#include <stdlib.h>
#include <time.h>
int main() {
srand(time(NULL));
int N = 3;
int arr[N][N];
int flag;

do {
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            arr[i][j] = rand() % 10;
        }
    }
int magic_sum = 0;
for (int j = 0; j < N; j++) {magic_sum += arr[0][j];}
flag = 1;
 
for (int i = 0; i < N; i++) {
    int sum_str = 0;
   
    for (int j = 0; j < N; j++) {
        sum_str += arr[i][j];
    }
    if (sum_str != magic_sum) {
        flag = 0;
        break;
    }
}
if (flag) {
    for (int i = 0; i < N; i++) {
    int sum_stl = 0;
   
    for (int j = 0; j < N; j++) {
        sum_stl += arr[j][i];
    }
    if (sum_stl != magic_sum) {
        flag = 0;
        break;
    }
    }
}
    }
while (!flag);
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            printf(" | %2d ", arr[i][j]);
        }
        printf("|\n");
    }
return 0;
}
