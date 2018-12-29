/*
 * MonteCarlo.h
 *
 *  Created on: 9 paź 2016
 *      Author: oramus
 */

#ifndef MONTECARLO_H_
#define MONTECARLO_H_

#include<math.h>
#include<stdlib.h>
#include<iostream>
#include"Consts.h"

class MonteCarlo {
private:
	static long steps;
	static double scaleFactor;
public:
	static void generateNewPositions(const double *xp, const double *yp,
			const double *zp, double *xpp, double *ypp, double *zpp);

	static void setScaleFactor(double v) {
		if (!steps) {
			scaleFactor = v;
		}
	}

	static bool accept(double oldVal, double newVal) {
		steps++;
		double tmp = (newVal - oldVal) / scaleFactor;
		tmp = exp(-tmp * pow(steps, TEMPERATURE_DROP_PARAMETER));
//		std::cout << "old " << oldVal << " new " << newVal << " prob: "
//				<< (tmp / (tmp + 1)) << " skala " << pow(steps, POWER)
//				<< std::endl;

		return random() < ( RAND_MAX * tmp / (1.0 + tmp));
	}
};

#endif /* MONTECARLO_H_ */
