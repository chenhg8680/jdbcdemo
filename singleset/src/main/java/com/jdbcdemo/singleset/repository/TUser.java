package com.jdbcdemo.singleset.repository;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class TUser{
    private Integer id;
    private String name;
    private Integer status;
    private String createdAt;
    private String updatedAt;
}
