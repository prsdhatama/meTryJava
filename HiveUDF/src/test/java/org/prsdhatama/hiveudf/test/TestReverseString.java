package org.prsdhatama.hiveudf.test;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.JavaStringObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prsdhatama.hiveudf.ReverseString;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;

import static org.junit.jupiter.api.Assertions.*;

public class TestReverseString {

    String text = "hello world";
//    int text = 6;
    @Test
    @DisplayName("Test jika input adalah String")
    public void testStringInput() throws HiveException {

        ReverseString r = new ReverseString();
        ObjectInspector input = PrimitiveObjectInspectorFactory.javaStringObjectInspector;

        JavaStringObjectInspector resultInspector = (JavaStringObjectInspector) r.initialize(new ObjectInspector[] {input});


        Object result = r.evaluate(new GenericUDF.DeferredObject[] { new GenericUDF.DeferredJavaObject(text) });

        assertEquals("dlrow olleh",
                resultInspector.getPrimitiveJavaObject(result));

        assertInstanceOf(String.class, result);



    }

}
