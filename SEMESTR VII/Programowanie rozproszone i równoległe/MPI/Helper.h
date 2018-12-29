/*
 * Helper.h
 *
 *  Created on: 9 paź 2016
 *      Author: oramus
 */

#ifndef HELPER_H_
#define HELPER_H_

class Helper {
	static long valueExecutions;
public:
	static double distanceSQ( double x1, double y1, double z1, double x2, double y2, double z2 );
	static double distance( double x1, double y1, double z1, double x2, double y2, double z2 );
	static double value(  const double *xp, const double *yp, const double *zp, double x, double y, double z );
	static double scale( double x );
	static long getValueExecutions() {
		return valueExecutions;
	}
	static void clearValueExecutionsCounter() {
		valueExecutions = 0;
	}
};

#endif /* HELPER_H_ */
