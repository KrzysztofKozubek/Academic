/*
 * Calculations.h
 *
 *  Created on: 5 lip 2016
 *      Author: oramus
 */

#ifndef CALCULATIONS_H_
#define CALCULATIONS_H_

class Calculations {
private:
	int numberOfPatters;
	double **histograms;
	int histogramSize;

	double histogramDiff(double *h1, double *h2);
	void calcHistogram(double *result, double *x, double *y, double *z,
			long size);
	void normalizeHistogram(double *histogram);
	void clearHistogram(double *histogram);
	int findBestMatch(double *histogramToCompare);
	// procesuje wzorzec tworzac histogram
	void processPattern();
public:
	// ustala liczbe wzorcow
	void setNumberOfPatterns(int p);
	// dodaje wzorzec
	void addPattern(int idx, double *x, double *y, double *z, long size);

	// zwraca id najlepszego wzorca, ktory pasuje do przekazanego
	int matchPattern(double *x, double *y, double *z, long size);

	// mikser pobiera losowe dane z przeslanych dwu-wymiarowych tablic (x,y,z) i
	// umieszcza je w tablicach wynikowych (x,y,z-res)
	// liczba danych do pobrania z zapisana jest w wektorze order
	void mixer(double **x, double **y, double **z, long *size, double *xres,
			double *yres, double *zres, long *order, int patterns);

public:
	Calculations();
	virtual ~Calculations();
};

#endif /* CALCULATIONS_H_ */
