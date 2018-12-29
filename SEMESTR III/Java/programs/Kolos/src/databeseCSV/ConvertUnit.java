package databeseCSV;

public class ConvertUnit {

//lenght
    public static double inToCm(double in){return in*2.54;}
    public static double cmToin(double cm){return cm*0.394;}
    public static double inToM(double in){return in*0.0254;}
    public static double mToIn(double m){return m*39.4;}
    public static double ftToCm(double ft){return ft*30.4;}
    public static double ftToM(double ft){return ft*0.304;}
    public static double mToFt(double m){return m*3.2894;}
    public static double cmToFt(double cm){return cm*0.0328;}
    public static double cmToM(double cm){return cm/100;}
    public static double mToCm(double m){return m*100;}

//degree
    public static double radToDeg(double rad){return rad*(180/Math.PI);}
    public static double degToRad(double deg){return deg*0.017453;}

//temperature
    public static double cToF(double c){return c * (9/5) + 32;}
    public static double fToC(double f){return (f - 32) * (5/9);}

//light
    public static double luxToFc(double lux){return lux*10.76391;}
    public static double fcToLux(double fc){return fc*0.0929030436;}

//speed
    public static double kphToMilph(double kph){return kph*1.609344;}
    public static double milphToKph(double milph){return milph*0.621371192;}
    public static double kphToMph(double kph){return kph*1000;}
    public static double mphToKph(double mph){return mph/1000;}
    public static double kphToMps(double kph){return kph*3.6;}
    public static double mpsToKph(double mps){return mps*10/36;}
    public static double kphToKnots(double kph){return kph*1.85200;}
    public static double knotsToKph(double knots){return knots*0.539956803;}
    public static double milphToMps(double milph){return milph*2.23693629;}
    public static double mpsToMilph(double mps){return mps*0.44704;}
    public static double milphToKnots(double milph){return milph*1.15077945;}
    public static double knotsToMilph(double knots){return knots*0.868976242;}
    public static double mpsToKnots(double mps){return mps*0.514444444;}
    public static double knotsToMps(double knots){return knots*1.94384449;}

}
