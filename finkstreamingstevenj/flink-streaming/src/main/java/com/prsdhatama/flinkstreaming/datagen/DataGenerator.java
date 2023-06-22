package com.prsdhatama.flinkstreaming.datagen;

import java.time.Instant;
import java.util.Random;

public class DataGenerator {
    private static final int SECONDS_BETWEEN_RIDES = 20;
    private static final int NUMBER_OF_DRIVERS = 200;
    // creating a start time that started from 1 jan 2020 at 00.00 P.M
    private static final Instant beginTime = Instant.parse("2020-01-01T12:00:00.00Z");
    // this rideId field will not be serialized or included in any serialization and will be initiated upon deserialized with default value of 0
    final private transient long rideId;
    // constructor
    public DataGenerator(long rideId) {

        this.rideId = rideId;
    }

    public Instant placeTime() {
        return beginTime.plusSeconds(SECONDS_BETWEEN_RIDES * rideId);
    }
    public long driverId() {
        Random rnd = new Random(rideId);
        return 2013000000 + rnd.nextInt(NUMBER_OF_DRIVERS);
    }
    public long taxiId() {
        
        return driverId();
    }
    public float startLat() {
        return new Random(rideId).nextInt(10);
    }
    public float startLon() {
        return new Random(rideId).nextInt(10);
    }
    public String status(){
        String statuses[] = new String[]{ "YellowTaxi" , "GreenTaxi", "BlueTaxi" };
        Random rnd = new Random(rideId);
        return statuses[rnd.nextInt(3)];
    }
//    public short taxiType() {
//
//        return (short) aLong(1L, 4L);
//    }
    public String paymentType() {

        return (rideId % 2 == 0) ? "CARD" : "CASH";
    }
//    public float tolls() {
//        return (rideId % 10 == 0) ? aLong(0L, 5L) : 0L;
//    }
//    private long rideDurationMinutes() {
//        return aLong(0L, 600, 20, 40);
//    }
    //The LongRides exercise needs to have some rides with a duration > 2 hours, but not too many.
//    private long aLong(long min, long max) {
//        float mean = (min + max) / 2.0F;
//        float stddev = (max - min) / 8F;
//
//        return aLong(min, max, mean, stddev);
//    }
    //the rideId is used as the seed to guarantee deterministic results

//    private long aLong(long min, long max, float mean, float stddev) {
//        Random rnd = new Random(rideId);
//        long value;
//        do {
//            value = (long) Math.round((stddev * rnd.nextGaussian()) + mean);
//        }
//        while ((value < min) || (value > max));
//        return value;
//    }
}


