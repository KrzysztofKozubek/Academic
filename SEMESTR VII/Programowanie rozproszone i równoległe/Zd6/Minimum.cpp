#include "Minimum.h"
#include <stdlib.h>   // potrzebne dla random()
#include <math.h>     // bo sin i cos
#include <iostream>
#include <sys/time.h>

/* Krzysztof Kozubek */

Minimum::Minimum( Function *f, double min, double max ) : min( min ), max( max ), f(f) {
    bestX = bestY = ( min + max ) * 0.5;
    bestV = f->value( bestX, bestY );
    counter = 0;
    sumX = 0.0;
    sumY = 0.0;
    sumV = 0.0;
}

// metoda zwraca pozycje z wnetrza obszaru poszukiwania
double Minimum::limit( double x ) {
    if ( x < min ) return min; // za male
    if ( x > max ) return max; // za duze
    return x; // x jest pomiedzy min a max, wiec zwracamy x
}

bool Minimum::haveTimeToContinue() {
    struct timeval tf;
    gettimeofday( &tf, NULL );
    double now = tf.tv_sec * 1000 + tf.tv_usec * 0.001;

    if ( now < timeLimit ) return true;  // limit czasu nie osiagniety
    return false; // juz po czasie, pora konczyc obliczenia
} 

void Minimum::initializeTimeLimit( double msec ) {
    struct timeval tf;
    gettimeofday( &tf, NULL );
    timeLimit = tf.tv_sec * 1000 + tf.tv_usec * 0.001 + msec; // ustawiamy czas zakonczenia obliczen    
}

void Minimum::find( double dr, int idleStepsLimit, double msec ) {

    initializeTimeLimit( msec );

/* Inicjalizacja tablic dla każdego wątku */
    
    long*   counterThread;
    double* sumXThread;
    double* sumYThread;
    double* sumVThread;

    double x, y, v, r, xnew, ynew, vnew, randX, randY, randV;

#pragma omp parallel private(x, y, v, r, xnew, ynew, vnew)
    {  
        const int numberThreads = omp_get_num_threads();

#pragma omp single
    {
        /* Wyzerowanie tablic */ 
        counterThread   = new long[numberThreads];
        sumXThread      = new double[numberThreads];
        sumYThread      = new double[numberThreads];
        sumVThread      = new double[numberThreads];

        for(int i = 0; i < numberThreads; i++) {

            counterThread[i]    = 0;
            sumXThread[i]       = 0.0;
            sumYThread[i]       = 0.0;
            sumVThread[i]       = 0.0;
        }
    }

        struct drand48_data buffer;
        
        int numberThread = omp_get_thread_num();
        srand48_r((unsigned)time(NULL) * numberThread, &buffer);


        while ( haveTimeToContinue() ) {

            /* Losowanie X Y */
            drand48_r(&buffer, &x);
            drand48_r(&buffer, &y);

            x = x * ( max - min ) + min;
            y = y * ( max - min ) + min;

            v = f->value( x, y );
            int idleSteps = 0;

            while( idleSteps < idleStepsLimit ) { 

                /* Losowanie r */
                drand48_r(&buffer, &r);
                r *= 6.28318;  
    
                xnew = x + dr * sin( r );
                ynew = y + dr * cos( r );

                xnew = limit( xnew );
                ynew = limit( ynew );

                vnew = f->value( xnew, ynew );

                counterThread[numberThread]++;
                sumXThread[numberThread] += xnew;
                sumYThread[numberThread] += ynew;
                sumVThread[numberThread] += vnew;

                if ( vnew < v ) {

                    x = xnew;  
                    y = ynew;
                    v = vnew;
                    idleSteps = 0; 
                } else {
                    idleSteps++;
                }
            } 

#pragma omp critical 
    {
            if ( v < bestV ) {  
                bestV = v;
                bestX = x;
                bestY = y;

                std::cout << "Thread #" << omp_get_thread_num() << " new better position: " << x << ", " << y << " value = " << v << std::endl;
            } 
    }
        } 
#pragma omp barrier
#pragma omp single
    {
        for(int i = 0; i < numberThreads; i++) {

            counter += counterThread[i];
            sumX += sumXThread[i];
            sumY += sumYThread[i];
            sumV += sumVThread[i];
        }
        delete [] counterThread;
        delete [] sumXThread;
        delete [] sumYThread;
        delete [] sumVThread;
    }
    } 
}
