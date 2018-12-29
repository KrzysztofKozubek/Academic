#ifndef MINIMUM_H
#define MINIMUM_H

#include"Function.h"

class Minimum {
	private:
		double bestX, bestY, bestV; // najlepsza znaleziona pozycja i wartosc
		double sumX, sumY, sumV; // suma wyliczanych polozen i wartosci
		const Function *f;  // wskaznik do minimalizowanej funkcji
		const double min, max;
		long counter;
		
		double timeLimit; // czas zakonczenia obliczen
		
		double limit( double x );
		bool haveTimeToContinue();   // sprawdza czy nie minal czas na obliczenia
		void initializeTimeLimit( double msec );

	public:
		void find( double dr, int idleStepsLimit, double msec );
		
		double getBestX() {
		  return bestX;
		}
		double getBestY() {
		  return bestY;
		}
		double getBestValue() {
		  return bestV;
		}

		double getAvgX() {
		  return sumX / counter;
		}
		double getAvgY() {
		  return sumY / counter;
		}
		double getAvgValue() {
		  return sumV / counter;
		}
		long getCounter() {
		  return counter;
		}
		
		// Do konstruktora przekazywana jest funkcja do minimalizacji oraz obszar poszukiwania rozwiazania
		Minimum( Function *f, double min, double max );
};

#endif

