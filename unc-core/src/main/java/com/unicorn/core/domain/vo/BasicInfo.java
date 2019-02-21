package com.unicorn.core.domain.vo;

import com.unicorn.core.domain.Nomenclator;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class BasicInfo implements Serializable {

    protected Long objectId;

    protected String name;

    protected BasicInfo(Long objectId, String name) {
        this.objectId = objectId;
        this.name = name;
    }

    public static BasicInfo valueOf(Long objectId, String name) {
        return new BasicInfo(objectId, name);
    }

    public static BasicInfo valueOf(Nomenclator nomenclator) {
        if (nomenclator == null) {
            return null;
        }
        return BasicInfo.valueOf(nomenclator.getObjectId(), nomenclator.getName());
    }

    public static List<BasicInfo> valueOf(List<? extends Nomenclator> list) {

        return list.stream().map(BasicInfo::valueOf).collect(Collectors.toList());
    }
}
