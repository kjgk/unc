package com.unicorn.core.domain;

import java.util.List;

public interface Recursive<T> extends Nomenclator {

    T getParent();

    void setParent(T parent);

    List<T> getChildList();

    void setChildList(List<T> childList);
}