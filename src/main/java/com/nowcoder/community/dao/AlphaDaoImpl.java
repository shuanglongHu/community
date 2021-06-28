package com.nowcoder.community.dao;

import org.springframework.stereotype.Repository;

@Repository
public class AlphaDaoImpl implements MyDao{
    @Override
    public String select() {
        return "Hibernate";
    }
}
