/*
 * Optimize.cpp
 *
 *  Created on: 9 paź 2016
 *      Author: oramus
 */

#include "Optimize.h"
#include "Consts.h"
#include <math.h>
#include <iostream>
#include <mpi.h>

#include <unistd.h>

using namespace std;

Optimize::Optimize() {
	xp 		= new double[PARTICLES];
	yp 		= new double[PARTICLES];
	zp 		= new double[PARTICLES];
	xpp 	= new double[PARTICLES];
	ypp 	= new double[PARTICLES];
	zpp 	= new double[PARTICLES];
	x 		= new double[POSITIONS];
    y 		= new double[POSITIONS];
    z 		= new double[POSITIONS];
    fieldV 	= new double[POSITIONS];
}

void Optimize::setPositionsAndRequiredFieldValues(const double *x0, const double *y0, const double *z0, const double *v0) {
	this->x = x0;
	this->y = y0;
	this->z = z0;
	this->fieldV = v0;
}

void Optimize::setMC(MonteCarlo * mc) { this->mc = mc; }

double *Optimize::getX() { return xp; }
double *Optimize::getY() { return yp; }
double *Optimize::getZ() { return zp; }

void Optimize::setParticlesInitialPositions(const double *x0, const double *y0, const double *z0) {
	for (int i = 0; i < PARTICLES; i++) {
		xp[i] = x0[i];
		yp[i] = y0[i];
		zp[i] = z0[i];
	}
}

void Optimize::calcInitialError() {
	for (int i = 0; i < PARTICLES; i++) {
		xpp[i] = xp[i];
		ypp[i] = yp[i];
		zpp[i] = zp[i];
	}

	err = calcError();
}

void Optimize::updateParticlesPositions() {
	for (int i = 0; i < PARTICLES; i++) {
		xp[i] = xpp[i];
		yp[i] = ypp[i];
		zp[i] = zpp[i];
	}
}

bool firstTime = true;

void Optimize::shareFieldData() {

    int rank = 0;
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);

	MPI_Bcast((void *)x, POSITIONS, MPI_DOUBLE, 0, MPI_COMM_WORLD);
	MPI_Bcast((void *)y, POSITIONS, MPI_DOUBLE, 0, MPI_COMM_WORLD);
	MPI_Bcast((void *)z, POSITIONS, MPI_DOUBLE, 0, MPI_COMM_WORLD);
	MPI_Bcast((void *)fieldV, POSITIONS, MPI_DOUBLE, 0, MPI_COMM_WORLD);
}

double Optimize::calcError() {

	double 	delta 	= 0, result = 0, dv = 0;
    int 	size 	= 0, rank 	= 0;

    int from, to;

    MPI_Comm_size(MPI_COMM_WORLD, &size);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);

    if(firstTime && !rank){

    	firstTime = false;
    	for (int i = 0; i < POSITIONS; i++) {
	        dv 	= fieldV[i] - Helper::value(xpp, ypp, zpp, x[i], y[i], z[i]);
	        delta += dv * dv;
    	}

    	return sqrt(delta) / POSITIONS;
    }

	MPI_Barrier(MPI_COMM_WORLD);
	MPI_Bcast(xpp, PARTICLES, MPI_DOUBLE, 0, MPI_COMM_WORLD);
	MPI_Bcast(ypp, PARTICLES, MPI_DOUBLE, 0, MPI_COMM_WORLD);
	MPI_Bcast(zpp, PARTICLES, MPI_DOUBLE, 0, MPI_COMM_WORLD);

	from 	= (rank * POSITIONS / size);
	to 		= ((rank + 1) * POSITIONS / size);
	if(rank == size) to = POSITIONS;


    for (int i = from; i < to; i++) {
        dv 	= fieldV[i] - Helper::value(xpp, ypp, zpp, x[i], y[i], z[i]);
        delta += dv * dv;
    }

    MPI_Reduce(&delta, &result, 1, MPI_DOUBLE, MPI_SUM, 0, MPI_COMM_WORLD);
	MPI_Barrier(MPI_COMM_WORLD);

	return sqrt(result) / POSITIONS;
}

void Optimize::step() {

	int rank = 0;
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);

	if(!rank)
		mc->generateNewPositions(xp, yp, zp, xpp, ypp, zpp);

	double newErr = 0;
	if(!rank)
		newErr = calcError();
	else
		calcError();

	if ( !rank && mc->accept(err, newErr)) {
//		cout << "Zaakceptowano zmiane z " << err << " na " << newErr << endl;
		err = newErr;
		updateParticlesPositions();
	} else {
//		cout << "Nie zaakceptowano zmiany z " << err << " na " << newErr << endl;
	}
}

Optimize::~Optimize() {}