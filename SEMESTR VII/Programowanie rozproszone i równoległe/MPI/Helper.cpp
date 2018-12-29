/*
 * Helper.cpp
 *
 *  Created on: 9 paź 2016
 *      Author: oramus
 */

#include "Helper.h"
#include "Consts.h"
#include <math.h>
#include <iostream>

using namespace std;

double Helper::distance( double x1, double y1, double z1, double x2, double y2, double z2 ) {
	double dx = x1 - x2;
	double dy = y1 - y2;
	double dz = z1 - z2;

	return sqrt( dx * dx + dy * dy + dz * dz );
}

double Helper::distanceSQ( double x1, double y1, double z1, double x2, double y2, double z2 ) {
	double dx = x1 - x2;
	double dy = y1 - y2;
	double dz = z1 - z2;

	return dx * dx + dy * dy + dz * dz;
}

double Helper::value(  const double *xp, const double *yp, const double *zp, double x, double y, double z ) {
	double result = 0;
	for ( int i = 0; i < PARTICLES; i++ ) {
		result += exp( - Helper::distanceSQ( xp[ i ], yp[ i ], zp[ i ], x, y, z ) * FIELD_EXPONENT_SCALE );
	}
	Helper::valueExecutions++;
	return result;
}

double Helper::scale( double x ) {
	return MIN + x * MAX_MIN;
}
