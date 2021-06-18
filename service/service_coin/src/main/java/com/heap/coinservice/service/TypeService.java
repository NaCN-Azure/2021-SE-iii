package com.heap.coinservice.service;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 纳思彧
 * @since 2021-06-07
 */
@Service
public interface TypeService {

    /**
     * 新增类型
     * @param domainId
     * @param nodeType
     * @return
     */
    String insertType(int domainId,String nodeType);

    /**
     * 删除类型
     * @param domainId
     * @param type
     */
    void deleteType(int domainId,String type);

    /**
     * 类型查找颜色
     * @param domainId
     * @param nodeType
     * @return
     */
    String searchColorByType(int domainId,String nodeType);

    /**
     * 查询所有类型
     * @param domainId
     * @return
     */
    List<String> searchAll(int domainId);

}
