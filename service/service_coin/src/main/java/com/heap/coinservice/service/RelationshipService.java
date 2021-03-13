package com.heap.coinservice.service;

import com.heap.coinservice.entity.Entity;
import com.heap.coinservice.entity.Relationship;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 车一晗
 * @since 2021-03-07
 */
public interface RelationshipService {
    Relationship createLink(Long fromId,Long toId,String name);

    Relationship updateLink(Relationship relationship);

    void deleteLink(Relationship relationship);
}
