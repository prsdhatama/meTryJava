package com.prsdhatama;

import com.prsdhatama.flink.jackson.Deserialization;
import com.prsdhatama.flink.schema.OrdersAndCustomers;
import com.prsdhatama.flink.schema.SchemaCustomers;
import com.prsdhatama.flink.schema.SchemaOrders;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.KeyedCoProcessFunction;
import org.apache.flink.util.Collector;

//import static com.prsdhatama.flink.utils.Constants;

public class App 
{
    public static void main( String[] args ) throws Exception {

        ParameterTool params = ParameterTool.fromArgs(args);
        //  environment local
        final StreamExecutionEnvironment envLocal = StreamExecutionEnvironment
                .createLocalEnvironmentWithWebUI(new Configuration());

        ////////////////////////////////////////C L U S T E R////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////
        // environment cluster
//        final StreamExecutionEnvironment envCluster = StreamExecutionEnvironment.getExecutionEnvironment();

        // Create KafkaSource using the constants from Constants class
//        KafkaSource<SchemaCustomers> schemaKafkaClusterSource = KafkaSource.<SchemaCustomers>builder()
//                .setBootstrapServers(params.get(Constants.K_BOOTSTRAP_SERVERS))
//                .setProperty("ssl.truststore.location", params.get(Constants.K_TRUSTSTORE_PATH))
//                .setProperty("sasl.mechanism", params.get(Constants.K_SASL_MECHANISM))
//                .setProperty("security.protocol", params.get(Constants.K_SECURITY_PROTOCOL))
//                .setProperty("sasl.kerberos.service.name", params.get(Constants.SASL_KERBEROS_SERVICE_NAME))
//                .setTopics(params.get(Constants.KAFKA_PREFIX + Constants.K_KAFKA_TOPIC))
//                .setValueOnlyDeserializer(new Deserialization<SchemaCustomers>(SchemaCustomers.class))
//                .setStartingOffsets(OffsetsInitializer.earliest())
//                .setGroupId("prsdhatama-flink")
//                .build();


        ////////////////////////////////////////C L U S T E R////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////

        // Local Kafka
        KafkaSource<SchemaCustomers> schemaCustomersKafkaLocalSource = KafkaSource
                .<SchemaCustomers>builder()
                .setBootstrapServers("localhost:9094") //localhost
                .setTopics("prsdhatama_olist_customers")
                .setValueOnlyDeserializer(new Deserialization<SchemaCustomers>(SchemaCustomers.class))
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setGroupId("prsdhatama-flink")
                .build();

//        DataStream<String> source = env.fromSource(consumer, WatermarkStrategy.noWatermarks(), "Kafka Source").uid("kafka-source");

        // Change "Local" to "Cluster" or the opposite
        DataStream<SchemaCustomers> schemaCustomersDataStream = envLocal.<SchemaCustomers>fromSource(
                schemaCustomersKafkaLocalSource,
                WatermarkStrategy.forMonotonousTimestamps(),
                "olist-customers-kafka"
        );


        // Local Kafka
        KafkaSource<SchemaOrders> schemaOrderKafkaLocalSource = KafkaSource
                .<SchemaOrders>builder()
                .setBootstrapServers("localhost:9094") //localhost
                .setTopics("prsdhatama_olist_orders")
                .setValueOnlyDeserializer(new Deserialization<SchemaOrders>(SchemaOrders.class))
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setGroupId("prsdhatama-flink")
                .build();

//        DataStream<String> source = env.fromSource(consumer, WatermarkStrategy.noWatermarks(), "Kafka Source").uid("kafka-source");

        // Change "Local" to "Cluster" or the opposite
        DataStream<SchemaOrders> schemaOrdersDataStream = envLocal.<SchemaOrders>fromSource(
                schemaOrderKafkaLocalSource,
                WatermarkStrategy.forMonotonousTimestamps(),
                "olist-orders-kafka"
        );

        KeyedStream<SchemaOrders, String> keyedOrders = schemaOrdersDataStream
                .keyBy(SchemaOrders::getCustomer_id);

        KeyedStream<SchemaCustomers, String> keyedCustomers = schemaCustomersDataStream
                .keyBy(SchemaCustomers::getCustomer_id);


        DataStream<String> joinedStream = keyedOrders.connect(keyedCustomers).process(
                new KeyedCoProcessFunction<String, SchemaOrders, SchemaCustomers, String>() {
                    private transient ValueState<SchemaCustomers> customersDimState;
                    private transient ValueState<SchemaOrders> ordersDimState;

                    @Override
                    public void open(final Configuration parameters) throws Exception {
                        super.open(parameters);
            /* valuestatedescriptor used for serialization on the state, managed by flink itself
            instead of using default serialization mechanism by java, its more type safety */
                        final ValueStateDescriptor<SchemaCustomers> customersDimStateDescriptor =
                                new ValueStateDescriptor<>("customersState", SchemaCustomers.class);
                        final ValueStateDescriptor<SchemaOrders> ordersDimStateDescriptor =
                                new ValueStateDescriptor<>("ordersState", SchemaOrders.class);
                        customersDimState = getRuntimeContext().getState(customersDimStateDescriptor);
                        ordersDimState = getRuntimeContext().getState(ordersDimStateDescriptor);
                    }

                    @Override
                    public void processElement1(SchemaOrders orders,
                                                KeyedCoProcessFunction<String, SchemaOrders, SchemaCustomers, String>.Context context,
                                                Collector<String> collector) throws Exception {
                        SchemaCustomers customers = customersDimState.value();

                        if (customers != null) {
                            // If there are customers available, clear the state
                            customersDimState.clear();

                            // Emit the joined result
                            collector.collect(new OrdersAndCustomers(orders, customers).toJoinedJsonString());
                        } else {
                            // If there are no customers available, update the orders state
                            ordersDimState.update(orders);
                            System.out.println("ordersDimState updated with ID " + orders.getCustomer_id() + " UPDATED");
                        }
                    }

                    @Override
                    public void processElement2(SchemaCustomers customers,
                                                KeyedCoProcessFunction<String, SchemaOrders, SchemaCustomers, String>.Context context,
                                                Collector<String> collector) throws Exception {
                        SchemaOrders orders = ordersDimState.value();
                        if (orders != null) {
                            // If there are customers available, clear the state
                            ordersDimState.clear();

                            // Emit the joined result
                            collector.collect(new OrdersAndCustomers(orders, customers).toJoinedJsonString());
                            System.out.println("ordersDimState matched with ID " + orders.getCustomer_id() + " MATCH AND REMOVED");
                        } else {
                            // If there are no customers available, update the orders state
                            customersDimState.update(customers);
                            System.out.println("customersDimState updated with ID " + customers.getCustomer_id() + " UPDATED");
                        }
                    }
                }
        );
        joinedStream.print();

        envLocal.execute("join-flink-kafka-streaming");
    }}