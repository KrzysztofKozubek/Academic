package databeseCSV.model;
public class Sensor {

    private double accelermmeterX;
    private double accelermmeterY;
    private double accelermmeterZ;
    private double gravityX;
    private double gravityY;
    private double gravityZ;
    private double linearAccelerationX;
    private double linearAccelerationY;
    private double linearAccelerationZ;
    private double gyroscopeX;
    private double gyroscopeY;
    private double gyroscopeZ;
    private double light;
    private double magneticFieldX;
    private double magneticFieldY;
    private double magneticFieldZ;
    private double orientationX;
    private double orientationY;
    private double orientationZ;
    private double proximity;
    private double armosphericPressure;
    private double temperature;
    private double soundLevel;
    private double locationLatitude;
    private double locationLongitude;
    private double locationAltitude;
    private double locationAltitudeGoogle;
    private double locationAltitudeAtmosphericPressure;
    private double locationSpeed;
    private double locationAccuracy;
    private double locationOrientation;
    private String satellitesInRange;
    private double timeSinceStartInMs;
    private String dateAndTime;

    public Sensor() {}

    public double getAccelermmeterX(){return accelermmeterX;}
    public double getAccelermmeterY(){return accelermmeterY;}
    public double getAccelermmeterZ(){return accelermmeterZ;}
    public double getGravityX(){return gravityX;}
    public double getGravityY(){return gravityY;}
    public double getGravityZ(){return gravityZ;}
    public double getLinearAccelerationX(){return linearAccelerationX;}
    public double getLinearAccelerationY(){return linearAccelerationY;}
    public double getLinearAccelerationZ(){return linearAccelerationZ;}
    public double getGyroscopeX(){return gyroscopeX;}
    public double getGyroscopeY(){return gyroscopeY;}
    public double getGyroscopeZ(){return gyroscopeZ;}
    public double getLight(){return light;}
    public double getMagneticFieldX(){return magneticFieldX;}
    public double getMagneticFieldY(){return magneticFieldY;}
    public double getMagneticFieldZ(){return magneticFieldZ;}
    public double getOrientationX(){return orientationX;}
    public double getOrientationY(){return orientationY;}
    public double getOrientationZ(){return orientationZ;}
    public double getProximity(){return proximity;}
    public double getArmosphericPressure(){return armosphericPressure;}
    public double getTemperature(){return temperature;}
    public double getSoundLevel(){return soundLevel;}
    public double getLocationLatitude(){return locationLatitude;}
    public double getLocationLongitude(){return locationLongitude;}
    public double getLocationAltitude(){return locationAltitude;}
    public double getLocationAltitudeGoogle(){return locationAltitudeGoogle;}
    public double getLocationAltitudeAtmosphericPressure(){return locationAltitudeAtmosphericPressure;}
    public double getLocationSpeed(){return locationSpeed;}
    public double getLocationAccuracy(){return locationAccuracy;}
    public double getLocationOrientation(){return locationOrientation;}
    public String getSatellitesInRange(){return satellitesInRange;}
    public double getTimeSinceStartInMs(){return timeSinceStartInMs;}
    public String getDateAndTime(){return dateAndTime;}

    public void setAccelermmeterX(double value){ accelermmeterX = value; }
    public void setAccelermmeterY(double value){ accelermmeterY = value; }
    public void setAccelermmeterZ(double value){ accelermmeterZ = value; }
    public void setGravityX(double value){ gravityX = value; }
    public void setGravityY(double value){ gravityY = value; }
    public void setGravityZ(double value){ gravityZ = value; }
    public void setLinearAccelerationX(double value){ linearAccelerationX = value; }
    public void setLinearAccelerationY(double value){ linearAccelerationY = value; }
    public void setLinearAccelerationZ(double value){ linearAccelerationZ = value; }
    public void setGyroscopeX(double value){ gyroscopeX = value; }
    public void setGyroscopeY(double value){ gyroscopeY = value; }
    public void setGyroscopeZ(double value){ gyroscopeZ = value; }
    public void setLight(double value){ light = value; }
    public void setMagneticFieldX(double value){ magneticFieldX = value; }
    public void setMagneticFieldY(double value){ magneticFieldY = value; }
    public void setMagneticFieldZ(double value){ magneticFieldZ = value; }
    public void setOrientationX(double value){ orientationX = value; }
    public void setOrientationY(double value){ orientationY = value; }
    public void setOrientationZ(double value){ orientationZ = value; }
    public void setProximity(double value){ proximity = value; }
    public void setArmosphericPressure(double value){ armosphericPressure = value; }
    public void setTemperature(double value){ temperature = value; }
    public void setSoundLevel(double value){ soundLevel = value; }
    public void setLocationLatitude(double value){ locationLatitude = value; }
    public void setLocationLongitude(double value){ locationLongitude = value; }
    public void setLocationAltitude(double value){ locationAltitude = value; }
    public void setLocationAltitudeGoogle(double value){ locationAltitudeGoogle = value; }
    public void setLocationAltitudeAtmosphericPressure(double value){ locationAltitudeAtmosphericPressure = value; }
    public void setLocationSpeed(double value){ locationSpeed = value; }
    public void setLocationAccuracy(double value){ locationAccuracy = value; }
    public void setLocationOrientation(double value){ locationOrientation = value; }
    public void setSatellitesInRange(String value){ satellitesInRange = value; }
    public void setTimeSinceStartInMs(double value){ timeSinceStartInMs = value; }
    public void setDateAndTime(String value){ dateAndTime = value; }

}