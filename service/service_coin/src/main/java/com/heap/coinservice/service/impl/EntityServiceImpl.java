package com.heap.coinservice.service.impl;

import com.heap.coinservice.entity.Entity;
import com.heap.coinservice.mapper.EntityMapper;
import com.heap.coinservice.service.EntityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
    private EntityMapper entityMapper;

    @Override
    public Entity createNode(String name, String color, int type, int domainId){
        Entity entity = Entity.builder().name(name).bgColor(color).shape(type).domainId(domainId).build();
        return entityMapper.save(entity);
    }

    @Override
    public List<Entity> getNodeByDomainId(int domainId){
        return entityMapper.getNodeByDomainId(domainId);
    }

    @Override
    public Entity updateNode(Entity entity){
        return entityMapper.updateNode(entity.getId(), entity.getName(), entity.getBgColor());
    }

    @Override
    public boolean deleteNode(Entity entity){
        Long id = entity.getId();
        Optional<Entity> check = entityMapper.findById(id);
        System.out.println("id:"+id);
        if(check.isPresent()) {
            entityMapper.deleteNodeWithLink(id);
            if(entityMapper.findById(id).isPresent()){
                entityMapper.delete(entity);
            }
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public Entity findByName(String name,int domainId){
        return entityMapper.findByName(name,domainId);
    }
}
