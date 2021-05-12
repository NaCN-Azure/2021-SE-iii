package com.heap.coinservice.service.impl;

import com.heap.coinservice.entity.Entity;
import com.heap.coinservice.mapper.EntityMapper;
import com.heap.coinservice.service.EntityService;
import com.heap.coinservice.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Entity createNode(String name, int shape, String type,int domainId,String description,double r,int fontSize){
        Entity entity = entityMapper.findByName(name,domainId);
        if(entity == null) {
            String color=typeService.insertType(domainId,type);
            entity = Entity.builder().name(name).bgColor(color).type(type).shape(shape).domainId(domainId).r(r).description(description).
                    fontSize(fontSize).build();
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
        String oldType = entityMapper.findById(entity.getId()).get().getType();
        if(!oldType.equals(entity.getType())) {
            updateType(entity.getId(), oldType, entity.getType(), entity.getDomainId());
        }
        entityMapper.updateForceXY(entity.getId(),entity.getX(),entity.getY());
        //entityMapper.updateComposeXY(entity.getId(),entity.getComposeX(),entity.getComposeY());
        entityMapper.updateNode(entity.getId(),entity.getName(), entity.getDescription(),entity.getR(),entity.getShape(),entity.getFontSize());
        return entityMapper.findById(entity.getId()).get();
    }

    @Override
    public boolean deleteNode(Entity entity){
        Long id = entity.getId();
        Optional<Entity> check = entityMapper.findById(id);
        if(check.isPresent()) {
            if(entityMapper.countEntitiesByType(entity.getDomainId(),entity.getType())<=1){
                typeService.deleteType(entity.getDomainId(),entity.getType());
            }
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
    public Entity findByName(String name, int domainId,boolean strict){
        //strict=true 严格模式下，name严格按照完全一致匹配
        //strict=false 模糊模式下，name可根据部分字段进行匹配
        return entityMapper.findByName(name, domainId);
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
    public void updateXY(Long id, double X, double Y){
        entityMapper.updateForceXY(id, X, Y);
    }

    @Override
    public void updateComposeXY(Long id,double composeX,double composeY){
        entityMapper.updateComposeXY(id,composeX,composeY);
    }

    @Override
    public List<Entity> getNodeByType(int domainId,String type){
        return entityMapper.getNodeByType(domainId,type);
    }

    @Override
    public void updateType(Long id,String oldType,String newType,int domainId){
        String color = typeService.searchColorByType(domainId,newType);
        if(color==null){
            color=typeService.insertType(domainId,newType);//没有,说明是新类型
        }
        if(entityMapper.countEntitiesByType(domainId,oldType)<=1){
            typeService.deleteType(domainId,oldType);//说明改动的节点是该类型的最后一个，类型删掉
        }
        entityMapper.updateType(id,newType,color);
    }
}
