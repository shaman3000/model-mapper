package com.ssh.model.mapper;

import com.ssh.model.mapper.pojos.RootObject;
import com.ssh.model.mapper.pojos.TargetObject;
import com.ssh.model.mapper.pojos.NestedObject;

public class TestMappingService {

    private Mapper<RootObject, TargetObject> mapper = new Mapper<>();

    public TestMappingService() {
        mapper.map(RootObject::getRootString, TargetObject::setRootString);
        mapper.map(RootObject::getNested, nestedMapping -> {
            nestedMapping.map(NestedObject::getNestedStringValue, TargetObject::setNestedStringValue);
        });
    }

    public TargetObject onMessage(RootObject sourceObject) {
        return mapper.execute(sourceObject, new TargetObject());
    }

    public static void main(String[] args) {
        TestMappingService mappingService = new TestMappingService();

        int val = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            RootObject sourceObject = new RootObject();
            TargetObject target = mappingService.onMessage(sourceObject);
            val = target.hashCode();
        }
        System.out.println(val + "    " + (System.currentTimeMillis() - start) + "ms");
        // 2008ms
    }

}
