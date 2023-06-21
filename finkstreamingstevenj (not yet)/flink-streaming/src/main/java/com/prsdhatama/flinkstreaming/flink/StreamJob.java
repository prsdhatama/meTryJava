package com.prsdhatama.flinkstreaming.flink;


import com.prsdhatama.flinkstreaming.faketaxi.TaxiOrder;
import com.prsdhatama.flinkstreaming.faketaxi.TaxiOrderGenerator;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class StreamJob {
    public static void main(String[] args) throws Exception {
        // set up the streaming execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment
                .getExecutionEnvironment();
//                .createLocalEnvironmentWithWebUI(new Configuration());
        // instance StreamExecutionEnvironment menggunakan method getExecutionEnvironment dari class StreamExecutionEnvironment
        // return the new Configuration
        DataStream<TaxiOrder> orders = env.addSource(new TaxiOrderGenerator());
        orders.print();
        DataStream<TaxiOrder> filteredOrders = orders
                // keep only those rides and both start and end in NYC
                .filter(new TaxiColorFilter());
        filteredOrders.print();
        System.out.println("**************************************************************");
        filteredOrders.print();

        env.execute("Flink Streaming Java API Skeleton");
    }

    public static class TaxiColorFilter implements FilterFunction<TaxiOrder> {
        @Override
        public boolean filter(TaxiOrder order) {
            return order.status.equals("YellowTaxi");
        }
    }
}


