package com.heap.coinservice.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class Domain {
    private int id;
    private String name;
    private String user_id;
}
