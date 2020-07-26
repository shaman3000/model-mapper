package com.ssh.model.mapper.pojos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RootObject {

    private String rootString = "rootString";

    private NestedObject nested = new NestedObject();

}
