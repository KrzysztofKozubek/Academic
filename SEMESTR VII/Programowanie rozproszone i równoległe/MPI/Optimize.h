/*
 * Optimize.h
 *
 *  Created on: 9 paź 2016
 *      Author: oramus
 */

#ifndef OPTIMIZE_H_
#define OPTIMIZE_H_

#include"MonteCarlo.h"
#include"Helper.h"

class Optimize {
private:
	// najlepsze pozycje czastek
	double *xp, *yp, *zp;

	// propozycje pozycji czastek
	double *xpp, *ypp, *zpp;

	// polozenia, w ktorych liczone sa wartosci pola i wartosci pola
	const double *x, *y, *z, *fieldV;

	// kopia polozenia
	double *xc, *yc, *zc, *fvc;

	// aktualna roznica w stosunku do oczekiwanego rozkladu pola
	double err;

	double *shareX, *shareY, *shareZ, *shareFieldV;

	// wskaznik do obiektu klasy Monte Carlo
	MonteCarlo * mc;

	double calcError();
	void updateParticlesPositions();

public:
	Optimize();

	// ustawia poczatkowe polozenia czastek
	void setParticlesInitialPositions(const double *x0, const double *y0,
			const double *z0);

	// ustawia polozenia, w ktorych wyliczane jest pole i jego
	// oczekiwany rozklad
	void setPositionsAndRequiredFieldValues(const double *x0, const double *y0,
			const double *z0, const double *v0);

	// ustawia dostep do obiektu MC odpowiedzialnego za obsluge optymalizacji
	void setMC( MonteCarlo * mc );

	// wyznacza poczatkowy blad
	void calcInitialError();

	// zleca wykonanie kroku obliczen
	void step();

	// zleca rozeslanie danych o polu
	void shareFieldData();

	// zwraca wskaznik do X-owej wspolrzednej czastek
	double *getX();
	// zwraca wskaznik do Y-owej wspolrzednej czastek
	double *getY();
	// zwraca wskaznik do Z-owej wspolrzednej czastek
	double *getZ();

	virtual ~Optimize();
};

#endif /* OPTIMIZE_H_ */
