package com.heap.coinservice.service.impl;

import com.heap.coinservice.entity.Entity;
import com.heap.coinservice.mapper.EntityMapper;
import com.heap.coinservice.service.EntityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heap.coinservice.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 车一晗
 * @since 2021-03-07
 */
@Service
public class EntityServiceImpl implements EntityService {

    @Autowired
    private TypeService typeService;

    @Autowired
    private EntityMapper entityMapper;

    @Override
    public Entity createNode(String name, String color, int shape, String type,int domainId,String description){
        Entity entity = entityMapper.findByName(name,domainId);
        if(entity == null) {
            color=typeService.insertType(color,type);
            entity = Entity.builder().name(name).bgColor(color).type(type).shape(shape).domainId(domainId).x(0).y(0).description(description).build();
            return entityMapper.save(entity);
        }
        else {
            return null;
        }
    }

    @Override
    public List<Entity> getNodeByDomainId(int domainId){
        return entityMapper.getNodeByDomainId(domainId);
    }

    @Override
    public Entity updateNode(Entity entity){
        return entityMapper.updateNode(entity.getId(), entity.getName(), entity.getDescription());
    }

    @Override
    public boolean deleteNode(Entity entity){
        Long id = entity.getId();
        Optional<Entity> check = entityMapper.findById(id);
        if(check.isPresent()) {
            entityMapper.deleteNodeWithLink(id);
            if(entityMapper.findById(id).isPresent()){
                if(entityMapper.countEntitiesByTypeNoDomain(entity.getType())==1){
                    typeService.deleteType(entity.getType());
                }
                entityMapper.delete(entity);
            }
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public Entity findByName(String name, int domainId){
        return entityMapper.findByName(name,domainId);
    }

    @Override
    public int countAllEntity(int domainId){
        return entityMapper.countAllEntity(domainId);
    }

    @Override
    public int countEntitiesByType(int domainId,String type){
        return entityMapper.countEntitiesByType(domainId,type);
    }

    @Override
    public Entity updateXY(Long id, double x, double y){
        return entityMapper.updateXY(id,x,y);
    }

    @Override
    public void updateColors(String type,String color){
        typeService.updateColor(type,color);
        entityMapper.updateAllColors(type,color);
    }
}
