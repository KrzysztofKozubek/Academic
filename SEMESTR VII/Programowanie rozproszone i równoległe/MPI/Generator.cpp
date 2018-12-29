/*
 * Generator.cpp
 *
 *  Created on: 9 paź 2016
 *      Author: oramus
 */

#include "Generator.h"
#include "Helper.h"
#include <stdlib.h>
#include <iostream>
#include <math.h>

Generator::Generator() {

	xp = new double[PARTICLES];
	yp = new double[PARTICLES];
	zp = new double[PARTICLES];

	xop = new double[PARTICLES];
	yop = new double[PARTICLES];
	zop = new double[PARTICLES];

	usedIndices = new int[ PARTICLES ];

	x = new double[POSITIONS];
	y = new double[POSITIONS];
	z = new double[POSITIONS];
	v = new double[POSITIONS];

	generatePositions(MIN_DISTANCE_BEETWEEN_PARTICLES, PARTICLES, xp, yp, zp);
	generatePositions(MIN_DISTANCE_BEETWEEN_PARTICLES, PARTICLES, xop, yop,
			zop);
	generatePositions( POSITIONS, x, y, z);

	counter = 0;
	calcField();
}

void Generator::generatePositions( int size, double *x, double *y,
		double *z) {
	int size3 = 1 + (int)pow( size, 0.3333 );
	int counter = 0;
	std::cout << " size = " << size << " size3^3 = " << size3*size3*size3 << std::endl;

	double scale = 1.0 / (size3-1.0);
	for ( int i = 0; i < size3; i++ ) {
		double xhelp = Helper::scale( i * scale );
		for ( int j = 0; j < size3; j++ ) {
			double yhelp = Helper::scale( j * scale );
			for ( int k = 0; k < size3; k++ ) {
				if ( counter < size ) {
					x[ counter ] = xhelp;
					y[ counter ] = yhelp;
					z[ counter ] = Helper::scale( k * scale );
					counter++;
				} else {
					return; // koniec generowaniania pozycji
				}
			}
		}
	}
}


void Generator::generatePositions(double limit, int size, double *x, double *y,
		double *z) {

	double xprop, yprop, zprop;
	double limitSQ = limit * limit;

	x[0] = Helper::scale(random() / (double) RAND_MAX);
	y[0] = Helper::scale(random() / (double) RAND_MAX);
	z[0] = Helper::scale(random() / (double) RAND_MAX);

	for (int i = 1; i < size;) {
		xprop = Helper::scale(random() / (double) RAND_MAX);
		yprop = Helper::scale(random() / (double) RAND_MAX);
		zprop = Helper::scale(random() / (double) RAND_MAX);

		if (!isCloserThenLimitSQ(limitSQ, xprop, yprop, zprop, x, y, z, i)) {
			x[i] = xprop;
			y[i] = yprop;
			z[i] = zprop;
			i++;
//			std::cout << i << " " << xprop << ", " << yprop << ", " << zprop
//					<< std::endl;
		}
	}
}

bool Generator::isCloserThenLimitSQ(double limitSQ, double x, double y,
		double z, const double *xt, const double *yt, const double *zt,
		int size) {
	for (int i = 0; i < size; i++) {
		if (Helper::distanceSQ(x, y, z, xt[i], yt[i], zt[i]) < limitSQ)
			return true;
	}
	return false;
}

bool Generator::indexWasUsed( int idx, int size ) {
	for ( int i = 0; i < size; i++ )
		if ( idx == usedIndices[ i ] ) return true;
	return false;
}

double Generator::error(const double *x, const double *y, const double *z) {
	double sum = 0;
	double min;
	double tmp;
	int best;

	// metoda error moze zostac uzyta tylko 2x (poczatek i koniec pracy programu)
	if (counter > 1) {
		std::cout << "BLAD !!!!! Za duzo wywolan metody error()" << std::endl;
		return 0;
	}
	counter++;

	for (int i = 0; i < PARTICLES; i++) {
//		std::cout << "Particle " << i << " [" << xp[i] << ", " << yp[i] << ", " << zp[i] << " ] " << std::endl;
		min = 3 * MAX_MIN * MAX_MIN ;

		for (int j = 0; j < PARTICLES; j++) {

			if ( indexWasUsed( j, i ) ) continue;

			tmp = Helper::distanceSQ(xp[i], yp[i], zp[i], x[j], y[j], z[j]);
			if (tmp < min) {
				min = tmp;
				best = j;
//				std::cout << "  closest Particle " << j << " [" << x[j] << ", " << y[j] << ", " << z[j] << " ] - " << sqrt(min) << std::endl;
			}
		}

		usedIndices[ i ] = best;
//		std::cout << "  Min distance " << sqrt( min ) << " to particle " << best << std::endl;

		sum += sqrt( min );
	}

	return sum / PARTICLES;
}

void Generator::calcField() {
	for (int i = 0; i < POSITIONS; i++) {
		v[i] = Helper::value(xp, yp, zp, x[i], y[i], z[i]);

//		std::cout << "Field: [" << x[i] << ", " << y[i] << ", " << z[i]
//				<< "] = " << v[i] << std::endl;
	}
}

double Generator::calcFieldError(const double *xx, const double *yy, const double *zz  ) {
	double dv, delta = 0;
	for (int i = 0; i < POSITIONS; i++) {
		dv = v[i] - Helper::value(xx, yy, zz, x[i], y[i], z[i]);
		delta += dv * dv;
	}
	return sqrt(delta) / POSITIONS;
}

double Generator::calcFinalFieldError(const double *x, const double *y, const double *z  ) {
	return calcFieldError(x,y,z);
}

double Generator::calcInitialFieldError() {
	return calcFieldError( xop, yop, zop );
}

Generator::~Generator() {
}

