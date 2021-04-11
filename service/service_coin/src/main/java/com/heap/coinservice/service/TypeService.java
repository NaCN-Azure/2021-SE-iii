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

    String insertType(int domainId,String nodeType);

    void deleteType(int domainId,String type);

    String searchColorByType(int domainId,String nodeType);

    List<String> searchAll(int domainId);

}
