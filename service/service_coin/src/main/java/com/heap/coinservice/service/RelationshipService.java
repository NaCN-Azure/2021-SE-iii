package com.heap.coinservice.service;

import com.heap.coinservice.entity.Domain;
import com.heap.coinservice.entity.Relationship;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
public interface RelationshipService {

    /**
     *
     * 创建关系
     * @param fromId
     * @param toId
     * @param name
     * @return
     */
    Relationship createLink(Long fromId, Long toId, String name);

    /**
     *
     * 更新关系
     * @param relationship
     * @return
     */
    Relationship updateLink(Relationship relationship);

    /**
     *
     * 删除关系
     * @param relationship
     * @return
     */
    boolean deleteLink(Relationship relationship);

    /**
     *
     * 返回域内所有关系（包含孤立节点）
     * @param domainId
     * @return
     */
    List<Relationship> getLinkByDomainId(int domainId);

    /**
     * 返回关系个数
     * @param domainId
     * @return
     */
    int countAllLink(int domainId);

}
