package com.heap.coinservice.service;

import com.heap.coinservice.entity.Entity;
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
    * @param shape
     * @param domainId
     * @param description
     * @return
     */
    Entity createNode(String name, int shape, String type,int domainId,String description,double r,int fontSize);

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
     * 严格模式下只返回含一个节点的节点列表，模糊模式不一定
     * @param name
     * @param domainId
     * @param strict
     * @return
     */
    List<Entity> findByName(String name, int domainId,boolean strict);

    /**
     * 获取域内节点个数
     * @param domainId
     * @return
     */
    int countAllEntity(int domainId);

    /**
     * 改变单个节点坐标
     * @param id
     * @param x
     * @param y
     */
    void updateXY(Long id, double x, double y);

    /**
     * 统计某类型节点个数
     * @param domainId
     * @param type
     * @return
     */
    int countEntitiesByType(int domainId,String type);

    /**
     * 筛选节点类型
     * @param domainId
     * @param type
     * @return
     */
    List<Entity> getNodeByType(int domainId,String type);

    /**
     * 改变类型
     * @param id
     * @param oldType
     * @param newType
     * @param domainId
     */
    void updateType(Long id,String oldType,String newType,int domainId);

}
