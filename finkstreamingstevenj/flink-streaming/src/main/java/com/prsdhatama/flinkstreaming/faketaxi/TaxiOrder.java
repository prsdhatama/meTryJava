package com.prsdhatama.flinkstreaming.faketaxi;


import com.prsdhatama.flinkstreaming.annotation.Nullable;
import com.prsdhatama.flinkstreaming.datagen.DataGenerator;
//import org.apache.flink.streaming.api.functions.source.datagen.DataGenerator;
//import javax.annotation.Nullable;
import java.io.Serializable;
import java.time.Instant;

public class TaxiOrder implements Comparable<TaxiOrder>, Serializable {

    /**
     * Creates a new TaxiRide with now as start and end time.
     */
    public TaxiOrder() {
    }

    //constructor
    public TaxiOrder(long orderId) {

        // this creating a field with orderId assigned to the field of orderId
        DataGenerator g = new DataGenerator(orderId);
        this.orderId = orderId;
        // checking the DataGenerator Class
        this.placeTime = g.placeTime();
        this.addrLon = g.startLon();
        this.addrLat = g.startLat();
        this.taxiId = g.taxiId();
//        this.taxiType = g.taxiType();
        this.status = g.status();
    }
    // this is field
    public long orderId;
    public Instant placeTime;
    public float addrLon;
    public float addrLat;
    public long taxiId;
    public String status;

    @Override
    public String toString() {

        return "Order ID: "+orderId + "," +
                "Place Time: "+placeTime.toString() + "," +
                "addrLon: "+addrLon + "," +
                "addrLat: " +addrLat + "," +
                "taxiId: " +taxiId + "," +
                status;
    }

    public int compareTo(@Nullable TaxiOrder other) {
        if (other == null) {
            return 1;
        } else if (this.orderId - other.orderId == 0) {
            return 0;
        } else {
            return this.orderId - other.orderId > 0 ? 1 : -1;
        }
    }

    @Override
    public boolean equals(Object other) {
        // checking if other is an instance of TaxiOrder and
        // checking if the field orderId of other(Object) is equal to other(TaxiOrder) cast to TaxiOrder class
        return other instanceof TaxiOrder &&
                this.orderId == ((TaxiOrder) other).orderId;
    }

    @Override
    public int hashCode() {
        //casting the orderId field of current field to int
        return (int) this.orderId;
    }

    public long getPlaceTime() {
        return placeTime.toEpochMilli();
    }
}

//tes
//test2