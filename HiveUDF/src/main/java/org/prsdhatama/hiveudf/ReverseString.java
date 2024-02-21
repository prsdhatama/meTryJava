package org.prsdhatama.hiveudf;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.StringObjectInspector;

public class ReverseString extends GenericUDF {
    private StringObjectInspector inputUDF;
    private ObjectInspector[] inputArguments;

    @Override
    public ObjectInspector initialize(ObjectInspector[] args) throws UDFArgumentException {

        inputArguments = args;
        // initialize input
        ObjectInspector input = args[0];
        //check number of input arguments for UDF assigned
        checkArgsSize(args, 1, 1);
        // check to make sure the input is a string
        if (!(input instanceof StringObjectInspector)) {
            //?? ini gmn dah cara manggil getStringValue
            /*
            ObjectInspectorConverters.MapConverter converter;
            getStringValue(args,0, ObjectInspectorConverters.MapConverter converter = new ObjectInspectorConverters.MapConverter())
             */
            throw new UDFArgumentException("input must be a string");
        } else {
            // cast the class as StringObjectInspector
            this.inputUDF = (StringObjectInspector) input;
            System.out.println("Success. Input formatted correctly");

            return PrimitiveObjectInspectorFactory.javaStringObjectInspector;
        }
    };

    @Override
    public Object evaluate(DeferredObject[] args) throws HiveException {
        Object input;

        if ((input = args[0].get()) == null){
            return null;
        } else {
            //ini getPrimitiveJavaObject klo dicek bentuknya abstract method, gmn mau tau ini fungsinya apa deh
            String stringForwards = this.inputUDF.getPrimitiveJavaObject(input);
            StringBuilder sbForwards = new StringBuilder(stringForwards);

            return sbForwards.reverse().toString();
        }
    }

    @Override
    public String getDisplayString(String[] strings) {
        return "value " + strings[0] + " (type: " + inputArguments[0].getTypeName() + ").";
    }

    ;

};
