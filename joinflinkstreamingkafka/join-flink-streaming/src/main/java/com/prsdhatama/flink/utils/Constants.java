package com.prsdhatama.flink.utils;

public class Constants {

    public static final String SENSITIVE_KEYS_KEY = "sensitive.keys";
    public static final String MASK = "********";
    public static final String KAFKA_PREFIX = "kafka.";
    public static final String K_BOOTSTRAP_SERVERS = "mtr01.ds-inovasi.com:9093,mtr02.ds-inovasi.com:9093,mtr03.ds-inovasi.com:9093";
    public static final String K_KAFKA_TOPIC = "prsdhatama_olist_customers";
    public static final String K_HDFS_OUTPUT = "/user/prsdhatama/flink/KafkaToHDFS/";
    public static final String K_SCHEMA_REG_URL = "schema.registry.url";
    public static final String K_SCHEMA_REG_SSL_CLIENT_KEY = "schema.registry.client.ssl";
    public static final String K_TRUSTSTORE_PATH = "trustStorePath";
    public static final String K_TRUSTSTORE_PASSWORD = "trustStorePassword";
    public static final String K_KEYSTORE_PASSWORD = "keyStorePassword";

    public static final String K_SASL_MECHANISM = "GSSAPI";
    public static final String K_SECURITY_PROTOCOL = "SASL_SSL";
    public static final String SASL_KERBEROS_SERVICE_NAME = "kafka";


}
