/*
 * MonteCarlo.cpp
 *
 *  Created on: 9 paź 2016
 *      Author: oramus
 */

#include <math.h>
#include "MonteCarlo.h"
#include <iostream>

using namespace std;

void MonteCarlo::generateNewPositions(const double *xp, const double *yp,
		const double *zp, double *xpp, double *ypp, double *zpp) {

	for (int i = 0; i < PARTICLES; i++) {
		xpp[i] = xp[i]
				+ (0.5 - random() / ( RAND_MAX + 1.0)) * STEP_INIT
						/ pow(steps, STEP_DECREASE_PARAMETER);
		ypp[i] = yp[i]
				+ (0.5 - random() / ( RAND_MAX + 1.0)) * STEP_INIT
						/ pow(steps, STEP_DECREASE_PARAMETER);
		zpp[i] = zp[i]
				+ (0.5 - random() / ( RAND_MAX + 1.0)) * STEP_INIT
						/ pow(steps, STEP_DECREASE_PARAMETER);
	}
}
