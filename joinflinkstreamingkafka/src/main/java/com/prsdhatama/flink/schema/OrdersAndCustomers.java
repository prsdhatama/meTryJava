package com.prsdhatama.flink.schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.Serializable;
public class OrdersAndCustomers implements Serializable {

    public SchemaOrders orders;
    public SchemaCustomers customers;

    public OrdersAndCustomers(){}
    public OrdersAndCustomers(SchemaOrders orders, SchemaCustomers customers) {
        this.orders = orders;
        this.customers = customers;
    }

    @Override
    public String toString() {
        return "<< Orders with orderId: " + orders.getOrder_id() + " | customerId: " + customers.getCustomer_id() +" are matched and updated >>";
    }
    public String toJoinedJsonString() {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonNode = mapper.createObjectNode();

        jsonNode.put("customer_id", customers.getCustomer_id());
        jsonNode.put("customer_unique_id", customers.getCustomer_unique_id());
        jsonNode.put("customer_zip_code_prefix", customers.getCustomer_zip_code_prefix());
        jsonNode.put("customer_city", customers.getCustomer_city());
        jsonNode.put("customer_state", customers.getCustomer_state());
        jsonNode.put("order_id", orders.getOrder_id());
        jsonNode.put("order_status", orders.getOrder_status());
        jsonNode.put("order_purchase_timestamp", orders.getOrder_purchase_timestamp());
        jsonNode.put("order_approved_at", orders.getOrder_approved_at());
        jsonNode.put("order_delivered_carrier_date", orders.getOrder_delivered_carrier_date());
        jsonNode.put("order_delivered_customer_date", orders.getOrder_delivered_customer_date());
        jsonNode.put("order_estimated_delivery_date", orders.getOrder_estimated_delivery_date());

        return jsonNode.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof OrdersAndCustomers)) {
            return false;
        }

        OrdersAndCustomers OrdersAndCustomer = (OrdersAndCustomers) other;
        return this.orders.equals(OrdersAndCustomer.orders) && this.customers.equals(OrdersAndCustomer.customers);
    }
}
