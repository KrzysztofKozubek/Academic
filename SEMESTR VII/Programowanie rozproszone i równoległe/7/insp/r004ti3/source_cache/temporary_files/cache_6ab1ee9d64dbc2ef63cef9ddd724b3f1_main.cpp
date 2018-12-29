#include "Utils.h"
#include "Consts.h"
#include <iostream>
#include <stdio.h>
#include <time.h>
#include <stdlib.h>
#include "Calculations.h"
#include <sys/time.h>

using namespace std;

double **x;
double **y;
double **z;
long *size;
Calculations *c = new Calculations();

void generateFileName(char *buffer, int sh, int num) {
	sprintf(buffer, "%sresult.SH%i.X.%i.txt", PATH_TO_FILES, sh, 5 * num);
}

void alocateMemory() {
	x = new double*[NUMBER_OF_PATTERNS];
	y = new double*[NUMBER_OF_PATTERNS];
	z = new double*[NUMBER_OF_PATTERNS];
	size = new long[NUMBER_OF_PATTERNS];
	for (int i = 0; i < NUMBER_OF_PATTERNS; i++) {
		x[i] = new double[MAX_DATA];
		y[i] = new double[MAX_DATA];
		z[i] = new double[MAX_DATA];
	}
}

int main(int argc, char **argv) {
	int pattern[NUMBER_OF_PATTERNS];
	char filename[128];
	struct timeval tf;
	double t0;

	srandom(time( NULL));
	alocateMemory();

	for (int i = 0; i < NUMBER_OF_PATTERNS; i++) {
		pattern[i] = random() % FILES_PER_PATTERN;
		generateFileName(filename, i, pattern[i]);
		cout << "Loading data from file: " << filename << endl;
		size[i] = Utils::load(filename, x[i], y[i], z[i], MAX_DATA);
		cout << " - data size = " << size[i] << endl;
	}
	cout << "Start calculations" << endl;
	gettimeofday(&tf, NULL);
	t0 = tf.tv_sec + tf.tv_usec * 0.000001;

	c->setNumberOfPatterns(NUMBER_OF_PATTERNS);
	for (int i = 0; i < NUMBER_OF_PATTERNS; i++) {
		c->addPattern(i, x[i], y[i], z[i], size[i]);
	}

	cout << "Tests" << endl;
	cout << "Tests" << endl;

	long sizeM = size[0];
	long *proportions = new long[NUMBER_OF_PATTERNS];
	double *xMix, *yMix, *zMix;

	xMix = new double[sizeM];
	yMix = new double[sizeM];
	zMix = new double[sizeM];

	for (int i = 0; i < 10; i++) {
		for (int j = 0; j < NUMBER_OF_PATTERNS; j++) {
			proportions[j] = 0;
		}
		long remains = sizeM;

		// tworzymy proporcje do mikstury wzorcow
		cout << "Mixture" << endl;
		for (int j = 0; j < NUMBER_OF_PATTERNS - 1; j++) {
			proportions[j] = random() % remains;
			remains -= proportions[j];
			cout << " - pattern # " << j << " points " << proportions[j]
					<< endl;
		}
		proportions[NUMBER_OF_PATTERNS - 1] = remains;

		int idx = 0;
		int max = proportions[0];
		for (int j = 1; j < NUMBER_OF_PATTERNS; j++) {
			if (proportions[j] > max) {
				max = proportions[j];
				idx = j;
			}
		}
		cout << "Largest representation in mixture from pattern # " << idx
				<< endl;

		c->mixer(x, y, z, size, xMix, yMix, zMix, proportions,
				NUMBER_OF_PATTERNS);

		cout << "Looking for best match" << endl;
		int result = c->matchPattern(xMix, yMix, zMix, sizeM);
		cout << "Histogram was closest to pattern ............  # " << result
				<< endl;
	}

	gettimeofday(&tf, NULL);
	double tk = tf.tv_sec + tf.tv_usec * 0.000001;

	cout << "Real time : " << (tk - t0) << " sec." << endl;

	return 0;
}
