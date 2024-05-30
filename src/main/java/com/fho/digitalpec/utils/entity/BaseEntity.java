package com.fho.digitalpec.utils.entity;

import java.io.Serializable;

public interface BaseEntity<I extends Serializable> {

    I getId();
}
