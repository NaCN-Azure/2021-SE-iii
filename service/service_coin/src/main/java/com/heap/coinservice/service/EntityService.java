package com.heap.coinservice.service;

import com.heap.coinservice.entity.Entity;
import com.baomidou.mybatisplus.extension.service.IService;
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
public interface EntityService  {

    /**
     *
     * 创建节点
     * @param name
     * @param color
     * @param type
     * @param domainId
     * @return
     */
    Entity createNode(String name, String color, int type, int domainId);

    /**
     *
     * 返回域内所有节点信息
     * @param domainId
     * @return
     */
    List<Entity> getNodeByDomainId(int domainId);

    /**
     *
     * 更新节点信息
     * @param entity
     * @return
     */
    Entity updateNode(Entity entity);

    /**
     *
     * 删除节点
     * @param entity
     * @return
     */
    boolean deleteNode(Entity entity);


    /**
     * 查询名称结点
     * @param name
     * @param domainId
     * @return
     */
    Entity findByName(String name,int domainId);

    /**
     * 获取域内节点个数
     * @param domainId
     * @return
     */
    int countAllEntity(int domainId);

    /**
     * 改变节点坐标
     * @param id
     * @param x
     * @param y
     * @return
     */
    Entity updateXY(Long id,double x,double y);

}
