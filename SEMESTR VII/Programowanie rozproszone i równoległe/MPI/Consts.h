/*
 * Consts.h
 *
 *  Created on: 4 lip 2016
 *      Author: oramus
 */

#ifndef CONSTS_H_
#define CONSTS_H_

const int POSITIONS = 69707;
const int PARTICLES = 43;
//const int PARTICLES = 17;
const int NUMBER_OF_REPETITIONS = 5001;
const int HEARTBEAT = 250;

const double MIN_DISTANCE_BEETWEEN_PARTICLES = 4.3;
const double MIN_DISTANCE_BEETWEEN_POSITIONS = 0.35;
//const double MIN_DISTANCE_BEETWEEN_POSITIONS = 0.1;

const double FIELD_EXPONENT_SCALE = 0.01;

const double MIN = -10.0;
const double MAX = 10.0;
const double MAX_MIN = MAX - MIN;
const double TEMPERATURE_DROP_PARAMETER = 0.91;

const double STEP_INIT = 0.33;
const double STEP_DECREASE_PARAMETER = 0.24;

#endif /* CONSTS_H_ */
