package com.prsdhatama.flink.schema;


import com.fasterxml.jackson.annotation.JsonProperty;

//"order_id","customer_id","order_status","order_purchase_timestamp","order_approved_at","order_delivered_carrier_date","order_delivered_customer_date","order_estimated_delivery_date"
public class SchemaOrders {
    @JsonProperty
    private String order_id;
    @JsonProperty
    private String customer_id;
    @JsonProperty
    private String order_status;
    @JsonProperty
    private String order_purchase_timestamp;
    @JsonProperty
    private String order_approved_at;
    @JsonProperty
    private String order_delivered_carrier_date;
    @JsonProperty
    private String order_delivered_customer_date;
    @JsonProperty
    private String order_estimated_delivery_date;

    public SchemaOrders() {
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getOrder_purchase_timestamp() {
        return order_purchase_timestamp;
    }

    public void setOrder_purchase_timestamp(String order_purchase_timestamp) {
        this.order_purchase_timestamp = order_purchase_timestamp;
    }

    public String getOrder_approved_at() {
        return order_approved_at;
    }

    public void setOrder_approved_at(String order_approved_at) {
        this.order_approved_at = order_approved_at;
    }

    public String getOrder_delivered_carrier_date() {
        return order_delivered_carrier_date;
    }

    public void setOrder_delivered_carrier_date(String order_delivered_carrier_date) {
        this.order_delivered_carrier_date = order_delivered_carrier_date;
    }

    public String getOrder_delivered_customer_date() {
        return order_delivered_customer_date;
    }

    public void setOrder_delivered_customer_date(String order_delivered_customer_date) {
        this.order_delivered_customer_date = order_delivered_customer_date;
    }

    public String getOrder_estimated_delivery_date() {
        return order_estimated_delivery_date;
    }

    public void setOrder_estimated_delivery_date(String order_estimated_delivery_date) {
        this.order_estimated_delivery_date = order_estimated_delivery_date;
    }
}
