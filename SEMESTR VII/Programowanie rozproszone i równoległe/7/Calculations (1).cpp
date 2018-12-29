/*
 * Calculations.cpp
 *
 *  Created on: 5 lip 2016
 *      Author: oramus
 */

#include <math.h>
#include "Calculations.h"
#include "Consts.h"
#include "Utils.h"
#include <iostream>
#include <stdlib.h>

using namespace std;

Calculations::Calculations() {
    histogramSize = (int) (MAX_DISTANCE * BINS_PER_LENTH_UNIT + 1);
}

Calculations::~Calculations() {
}

void Calculations::setNumberOfPatterns(int p) {
    numberOfPatters = p;
    histograms = new double*[numberOfPatters];
}

void Calculations::clearHistogram(double *histogram) {
    for (int i = 0; i < histogramSize; i++)
        histogram[i] = 0;
}

void Calculations::addPattern(int idx, double *x, double *y, double *z,
        long size) {
    histograms[idx] = new double[histogramSize];
    clearHistogram(histograms[idx]);
    calcHistogram( histograms[idx], x, y, z, size );
    normalizeHistogram( histograms[idx] );
}

// ta metoda MUSI zostac zrownoleglona
void Calculations::calcHistogram( double *result, double *x, double *y, double *z, long size ) {
#pragma omp parallel for schedule(guided)
    for (long i = 0; i < size; i++)
        for (long j = 0; j < i; j++) {
            int res = (int)sqrt(
                    Utils::distanceSQ(x[i], y[i], z[i], x[j], y[j], z[j]))
                * BINS_PER_LENTH_UNIT;
#pragma omp atomic
            results[res]++;
        }
}

// ta metoda MUSI zostac zrownoleglona
void Calculations::normalizeHistogram(double *histogram ) {
    double sum = 0;
    double removed = 0;
#pragma omp parallel for reduction(+:sum)
    for (int i = 0; i < histogramSize; i++)
        sum += histogram[i];

    // usuwamy dane od 0 do okolo 2, bo zawieraja
    // raczej informacje o sposobie tworzenia siatki,
    // a nie ksztalcie
    int BPL = 2 * BINS_PER_LENTH_UNIT;
#pragma omp parallel for reduction(+:removed)
    for ( int i = 0; i <= BPL; i ++ ) {
        removed += histogram[ i ];
        histogram[ i ] = 0;
    }

    cout << "Histogram contains " << sum << " counts" << endl;
    cout << "Data removed from histogram #" << removed << endl;

    sum -= removed;
#pragma omp parallel for
    for (int i = 0; i < histogramSize; i++) {
        histogram[i] /= sum;
    }
}

int Calculations::matchPattern(double *x, double *y, double *z,
        long size) {
    double *histogram = new double[ histogramSize ];

    clearHistogram(histogram);
    calcHistogram( histogram, x, y, z, size );
    normalizeHistogram( histogram );

    int result = findBestMatch( histogram );
    delete[] histogram;

    return result;
}

double Calculations::histogramDiff(double *h1, double *h2) {
    double sum = 0;
    double dh;
    for (int i = 0; i < histogramSize; i++) {
        dh = h1[i] - h2[i];
        sum += dh * dh;
    }
    return sqrt(sum);
}

int Calculations::findBestMatch( double *histogramToCompare ) {
    // sprawdzamy, do ktorego ze znanych histogramow nowy jest
    // najbardziej podobny
    double bestD = histogramDiff(histogramToCompare, histograms[0]);
    cout << "Best : " << bestD << endl;
    int bestIdx = 0;
    double newD;
    for (int i = 1; i < numberOfPatters; i++) {
        newD = histogramDiff(histogramToCompare, histograms[i]);
        cout << "* Proposal (" << i << ") : " << newD << endl;
        if (newD < bestD) {
            bestD = newD;
            cout << "New Best : " << bestD << endl;
            bestIdx = i;
        }
    }
    return bestIdx;
}

// ta metoda MUSI zostac zrownoleglona
// trzeba w nie wymienic generator liczb losowych
// na drand48_r
void Calculations::mixer( double **x, double **y, double **z, long *size,
        double *xres, double *yres, double *zres,
        long *order, int patterns ) {
    struct drand48_data buffer;
    long orders = order[0];
#pragma omp parallel for reduction(+:orders)
    for ( int i = 1; i < patterns; i++ )
        orders += order[ i ];

    cout << "Order size " << orders << endl;
#pragma omp parallel
    {
        srand48_r((unsigned)time(NULL), &buffer);
        int p;
        double rand;
        long idx;
#pragma omp for schedule(guided)
        for(int o = 0; o < orders; o++) {
            do {
                drand48_r(&buffer, &rand);
                p = (int)(rand * RAND_MAX) % patterns;
            } while ( order[ p ] == 0 );
            //orders --;
#pragma omp atomic
            order[ p ]--;
            drand48_r(&buffer, &rand);
            idx = (int)(rand * RAND_MAX) % size[ p ];
            xres[ o ] = x[ p ][ idx ];
            yres[ o ] = y[ p ][ idx ];
            zres[ o ] = z[ p ][ idx ];
        }
    }
}