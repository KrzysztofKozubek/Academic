#include <fstream>
#include <iostream>
#include <string>
#include <sstream>
#include<stdlib.h>
#include<string.h>
#include<math.h>
#include"Utils.h"
#include"Consts.h"

using namespace std;

double Utils::dx;
double Utils::dy;
double Utils::dz;
double Utils::counts;

double Utils::distanceSQ(double x1, double y1, double z1, double x2, double y2,
		double z2) {
	double dx = x1 - x2;
	double dy = y1 - y2;
	double dz = z1 - z2;
	return dx * dx + dy * dy + dz * dz;
}

int Utils::load(const char *fileName, double *x, double *y, double *z,
		int maxSize) {
	ifstream inputFile;

	inputFile.open(fileName, ifstream::in);
	char bufferX[16];
	char bufferY[16];
	char bufferZ[16];
	double xx, yy, zz;
	int counter = 0;

	while (inputFile.good()) {
		inputFile.getline(bufferX, 16, ',');
		inputFile.getline(bufferY, 16, ',');
		inputFile.getline(bufferZ, 16, '\n');

		if (strlen(bufferX) == 0) {
			return counter;
		}

		xx = atof(bufferX);
		yy = atof(bufferY);
		zz = atof(bufferZ);

//		cout << x << " " << y << " " << z << endl;

		x[counter] = xx;
		y[counter] = yy;
		z[counter] = zz;

		counter++;

		if (counter == maxSize)
			return maxSize;

	}

	inputFile.close();

	return counter;
}
