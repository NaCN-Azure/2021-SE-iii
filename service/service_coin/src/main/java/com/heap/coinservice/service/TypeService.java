package com.heap.coinservice.service;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 车一晗
 * @since 2021-03-07
 */
@Service
public interface TypeService {

    String insertType(String color,String nodeType);

    void updateColor(String type, String color);

    void deleteType(String type);

    String searchColorByType(String nodeType);

    List<String> searchAll();

}
