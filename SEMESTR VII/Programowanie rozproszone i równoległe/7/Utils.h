/*
 * Utils.h
 *
 *  Created on: 4 lip 2016
 *      Author: oramus
 */

#ifndef UTILS_H_
#define UTILS_H_

class Utils {
private:
	static double dx, dy, dz;
	static int size;
	static double counts;
public:
	static int load( const char *fileName, double *x, double *y, double *z, int maxSize );
	static double distanceSQ( double x1, double y1, double z1, double x2, double y2, double z2 );
	static void normalizeHistogram( double *histogram, int histogramSize );
	static bool testLastHistogram( long size );
};

#endif /* UTILS_H_ */
