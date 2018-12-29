#include"Function.h"
#include<math.h>

void Function::wait( double count ) {
  do {
    count -= 1.0;
  } while ( count > 0.0 );
}


double Function::value( double x, double y ) {
   double dx11 = x - 11.5; // dla x = 11.5 , sin( pi * x ) = -1.0
   double dx5 = x - 5.5; // dla x = 5.5 , sin( pi * x ) = -1.0
   double dy = y - 0.5;  // dla y = 0.5 , sin( pi * y ) = 1.0
   double dy2 = y - 20.5;  // dla y = 20.5 , sin( pi * y ) = 1.0
   double rsq = dx11 * dx11 + dy * dy;
   double r2sq = dx5 * dx5 + dy2 * dy2;

   wait( Function::sleep );
      
   return sin( 3.14159265 * x ) * sin( 3.14159265 * y ) - 2*exp( -rsq/2.0 ) - 0.5*exp( -r2sq/10.0 );
}
