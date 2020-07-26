package com.ssh.model.mapper;

import junit.framework.TestCase;
import com.ssh.model.mapper.pojos.NestedObject;
import com.ssh.model.mapper.pojos.RootObject;
import com.ssh.model.mapper.pojos.TargetObject;
import org.junit.Assert;
import org.junit.Test;

public class MapperTest extends TestCase {

    private Mapper<RootObject, TargetObject> mapper = new Mapper<>();

    public MapperTest() {
        mapper.map(RootObject::getRootString, TargetObject::setRootString);
        mapper.map(RootObject::getNested, nestedMapping -> {
            nestedMapping.map(NestedObject::getNestedStringValue, TargetObject::setNestedStringValue);
        });
    }

    @Test
    public void testExecute() {
        RootObject source = new RootObject();
        TargetObject target = mapper.execute(source, new TargetObject());
        Assert.assertEquals(source.getRootString(), target.getRootString());
        Assert.assertEquals(source.getNested().getNestedStringValue(), target.getNestedStringValue());
    }

    @Test
    public void testExecutePerf() {
        String value = "";
        long start = System.currentTimeMillis();
        long count = 10000000;
        RootObject sourceObject = new RootObject();
        TargetObject target = new TargetObject();
        for (int i = 0; i < count; i++) {
            TargetObject mapped = mapper.execute(sourceObject, target);
            value = mapped.getRootString();
        }
        if (value != null) {
            long mSecs = System.currentTimeMillis() - start;
            System.out.println(
                count + " records have been processed in " + mSecs + "ms. \r\n" +
                Long.valueOf(count / mSecs).toString() + " records/msec."
            );
        }
    }

}