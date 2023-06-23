package com.prsdhatama.flinkstreaming.flink;

import com.prsdhatama.flinkstreaming.taxi.TaxiOrder;
import com.prsdhatama.flinkstreaming.taxi.TaxiOrderGenerator;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class StreamJob {
    public static void main(String[] args) throws Exception {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment
                .createLocalEnvironmentWithWebUI(new Configuration());
        // set up the streaming execution environment
        ///basedontutorial
//        final StreamExecutionEnvironment env = StreamExecutionEnvironment
//                .getExecutionEnvironment();
        // instance StreamExecutionEnvironment menggunakan method getExecutionEnvironment dari class StreamExecutionEnvironment
        // return the new Configuration
        // the filter is work but all the data generated is BlueTaxi
        DataStream<TaxiOrder> orders = env.addSource(new TaxiOrderGenerator());
//                .filter(new TaxiColorFilter())
        DataStream<TaxiOrder> filteredOrders = orders
                .filter(new TaxiColorFilter());
        System.out.println("**************************************************************");
//        orders.print();
        filteredOrders.print();

//        filteredOrders.print();

        env.execute("Flink Streaming Java API Skeleton");
    }

    public static class TaxiColorFilter implements FilterFunction<TaxiOrder> {

        public TaxiColorFilter() {
        }

        @Override
        public boolean filter(TaxiOrder order) {
            return order.status.equals("BlueTaxi");
        }
    }
}


