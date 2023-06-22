package com.prsdhatama.flinkstreaming.taxi;

import org.apache.flink.streaming.api.functions.source.SourceFunction;
import java.util.ArrayList;
import java.util.List;
import org.apache.flink.streaming.api.watermark.Watermark;
import java.util.Random;

// this SourceFunction interface is used to emit data in flink, generate custom data for flink
public class TaxiOrderGenerator implements SourceFunction<TaxiOrder> {
    // gatau buat apaan nanti
    public static final int SLEEP_MILLIS_PER_EVENT = 10;
    // buat batch size kayanya
    private static final int BATCH_SIZE = 5;
    // nanti pelajarin lg volatile
    private volatile boolean running = true;
    // this line of code will provide an emited source of data called 'context'
    @Override
    public void run(SourceContext<TaxiOrder> context) throws Exception {

        long id = 0;
        long maxStartTime = 0;

        while (running && id<100) {

            // generate a batch of TaxiOrders events
                List<TaxiOrder> orders = new ArrayList<TaxiOrder>(BATCH_SIZE);
                for (int i = 1; i <= BATCH_SIZE; i++) {
                    // bisa diassign TaxiOrder() karena
                    TaxiOrder order = new TaxiOrder(id + i);
                    orders.add(order);
                    // the start times may be in order, but let's not assume that
                    // this line of code comparing the maxStartTime with the orderPlaceTime, the biggest value will be assigned to maxStartTime and will be stored for next iteration
                    maxStartTime = Math.max(maxStartTime, order.placeTime.toEpochMilli());
                }
                // then emit the new START events (out-of-order)
                java.util.Collections.shuffle(orders, new Random(id));
                // gangerti ini (lambda expression)
                // line dibawah ini fungsinya buat nandain timestamp streamingnya itu ngambil dari getplacetime di object context
                orders.iterator().forEachRemaining(r -> context.collectWithTimestamp(r, r.getPlaceTime()));
                // gunanya apa ya ini? kenapa buat elemen sisnya mesti didapetin placetime

                // produce a Watermark
                context.emitWatermark(new Watermark(maxStartTime));

                // prepare for the next batch, buat nambahin nilai id tiap iterasi
//                id += BATCH_SIZE;

                // don't go too fast
                Thread.sleep(BATCH_SIZE * SLEEP_MILLIS_PER_EVENT);
        }
    }

    @Override
    public void cancel() {
        running = false;
    }
}
