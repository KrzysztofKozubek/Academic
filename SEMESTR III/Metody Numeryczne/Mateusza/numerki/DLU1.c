#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int Lu(double *A, int pivot[], int n)
{
   int i, j, k, p;
   double *p_k, *p_row, *p_col;
   double max;


   for (k = 0, p_k = A; k < n; p_k += n, k++) {

      pivot[k] = k;
      max = fabs( *(p_k + k) );
      for (j = k + 1, p_row = p_k + n; j < n; j++, p_row += n) {
         if ( max < fabs(*(p_row + k)) ) {
            max = fabs(*(p_row + k));
            pivot[k] = j;
            p_col = p_row;
         }
      }

      if (pivot[k] != k)
         for (j = 0; j < n; j++) {
            max = *(p_k + j);
            *(p_k + j) = *(p_col + j);
            *(p_col + j) = max;
         }

      if ( *(p_k + k) == 0.0 ) return -1;

      for (i = k+1, p_row = p_k + n; i < n; p_row += n, i++) {
         *(p_row + k) /= *(p_k + k);
      }

      for (i = k+1, p_row = p_k + n; i < n; p_row += n, i++)
         for (j = k+1; j < n; j++)
            *(p_row + j) -= *(p_row + k) * *(p_k + j);

   }

   return 0;
}

#define N 4

int main(){

     double A[N][N] = { {0, 25, 23, 0},
                        {0, 1.1, 4, 5},
                        {0, 1.4, 4, 4},
			{0, 0.1, 1.4, 1.5},
			{0, 21, 7, 0}};
     int    pivot[N];
      int err, i, j;


  	err = Lu(&A[0][0], pivot, N);
     	if (err < 0){
           printf("Macierz osobliwa! \n\n"); }

    else{
            Lu(&A[N][N],  pivot,  N);
            printf("Rozkład LU macierzy: \n");
            for(i = 0; i < N; i++)
            {
                for(j = 0; j < N; j++)

                    printf("%3.2f \t", A[i][j]) ;
			printf("\n");
            }
        }
getchar();
}
