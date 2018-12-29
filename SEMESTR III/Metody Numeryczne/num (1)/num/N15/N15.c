#include<math.h>
#include<stdio.h>
#include<gsl/gsl_linalg.h>

double AlgorytmHor(gsl_vector *a, double x)
{
    double p = gsl_vector_get(a,0) ;
    int k ;
    for( k = 1 ; k <a -> size ; k++ )
    {
    p= p*x + gsl_vector_get(a,k);
    }
    return p ;
}

int main (void)
{
    double x_data[] = {0.062500,0.187500,0.312500,0.437500,0.562500,0.687500,0.812500,0.935700};
    double f_data[] = {0.687959,0.073443,-0.517558,-1.077264,-1.600455,-2.080815,-2.507266,-2.860307};
    int p1, p2, p3;
    double n;
    
    gsl_matrix *x = gsl_matrix_alloc(8,8);
    gsl_permutation *p = gsl_permutation_alloc (8);
    gsl_vector *f = gsl_vector_alloc (8);
    for(p2=0 ; p2 < 8; p2++)
    {
        gsl_vector_set( f, p2, f_data[p2]);
    }
    gsl_vector *a = gsl_vector_alloc (8);
    
    for(p1 =0 ; p1 < 8 ; p1++ )
    {
        n=1;
        for(p2=7 ; p2 >=0 ; p2-- )
        {
            if(p2==7 )
            {
                    gsl_matrix_set(x,p1,p2,1.0);
            }
            else
            {
                gsl_matrix_set(x,p1,p2,pow(x_data[p1],n));
                n++;
            }
        }
    }
    
    gsl_linalg_LU_decomp (x, p, &p3);
    gsl_linalg_LU_solve (x, p, f, a);
    printf("Jawne wspolczynniki wielomianu interpolacyjnego poczynaj¹c od a_(n-1)\n");
    gsl_vector_fprintf (stdout, a, "%g");
    printf("Postaæ wielomianu interpolacyjnego ");
    for(p2=7 ; p2 >=0 ; p2-- )
    {
        if( gsl_vector_get(a,p2)!=0)
        {
             printf("%f x^%d ",gsl_vector_get(a,p2),p2);
        }
    }
    printf("\n");
    /* w oparciu o nasz wielomian interpolacyjny liczymy punkty*/
    for ( n = -1.0 ; n <=1.0 ; n+=0.01 )
    {
    printf("%f %f\n", n, AlgorytmHor(a,n));
    }
    /*for ( p3= 0 ; p3 < 8 ; p3++)
    {
    printf("%f %f\n",x_data[p3] , f_data[p3]) ;
    }*/
    gsl_permutation_free(p);
    gsl_vector_free(a);
    gsl_vector_free(f) ;
    gsl_matrix_free(x);
    return 0;
    /*system("pause");*/
}

