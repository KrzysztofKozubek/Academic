/*
 * Generator.h
 *
 *  Created on: 9 paź 2016
 *      Author: oramus
 */

#ifndef GENERATOR_H_
#define GENERATOR_H_

#include"Consts.h"
#include"Helper.h"

class Generator {
private:
	// prawdziwe polozenia czastek (p) + polozenia, ktorych liczone jest pole
	// i wartosci pola (v)
	double *x, *y, *z, *v, *xp, *yp, *zp;
	// polozenia czastek, od ktorych rozpoczyna sie optymalizacja
	double *xop, *yop, *zop;

	// indeksy juz uzyte w wyznaczaniu par najblizszych czastek
	int *usedIndices;

	bool indexWasUsed( int idx, int size );
	void calcField();

	// sprawdza czy polozenie [x,y,z] nie jest blizej dowolnego
	// z przekazanych w [xt,yt,zt] o rozmiarze size
	bool isCloserThenLimitSQ( double limitSQ, double x, double y, double z,
			const double *xt, const double *yt, const double *zt, int size );

	void generatePositions( double limit, int size, double *x, double *y, double *z );
	void generatePositions( int size, double *x, double *y, double *z );
	double calcFieldError( const double *x, const double *y, const double *z );

	int counter;
public:
	Generator();

	double error( const double *x, const double *y, const double *z );

	// sekwencyjnie wylicza blad w wyznaczeniu pola.
	double calcInitialFieldError();
	double calcFinalFieldError( const double *x, const double *y, const double *z );

	const double *getX() {
		return x;
	}
	const double *getY() {
		return y;
	}
	const double *getZ() {
		return z;
	}
	const double *getV() {
		return v;
	}
	const double *getXop() {
		return xop;
	}
	const double *getYop() {
		return yop;
	}
	const double *getZop() {
		return zop;
	}

	virtual ~Generator();
};

#endif /* GENERATOR_H_ */
