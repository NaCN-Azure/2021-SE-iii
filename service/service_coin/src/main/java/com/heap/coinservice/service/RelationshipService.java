package com.heap.coinservice.service;

import com.heap.coinservice.entity.Relationship;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 车一晗
 * @since 2021-03-07
 */
public interface RelationshipService {
    Relationship createLink(Long fromId, Long toId, String name);

    Relationship updateLink(Relationship relationship);

    boolean deleteLink(Relationship relationship);
}
