package com.heap.coinservice.service;

import com.heap.coinservice.entity.Entity;
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
public interface EntityService  {

    Entity createNode(String name, String color, int type, int domainId);

    List<Entity> getNodeByDomainId(int domainId);

    Entity updateNode(Entity entity);

    void deleteNode(Entity entity);

}
