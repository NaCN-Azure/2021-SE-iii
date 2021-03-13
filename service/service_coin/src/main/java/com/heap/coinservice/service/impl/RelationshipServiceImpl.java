package com.heap.coinservice.service.impl;

import com.heap.coinservice.entity.Entity;
import com.heap.coinservice.entity.Relationship;
import com.heap.coinservice.mapper.EntityMapper;
import com.heap.coinservice.mapper.RelationshipMapper;
import com.heap.coinservice.service.RelationshipService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 车一晗
 * @since 2021-03-07
 */
@Service
public class RelationshipServiceImpl implements RelationshipService {
    @Autowired
    RelationshipMapper relationshipMapper;

    @Autowired
    EntityMapper entityMapper;

    @Override
    public Relationship createLink(Long fromId,Long toId,String name){
        Entity startnode=entityMapper.findById(fromId).get();
        Entity endnode=entityMapper.findById(toId).get();
        Relationship relationship= Relationship.builder().startEntity(startnode).endEntity(endnode)
            .domainId(startnode.getDomainId()).name(name).fromId(fromId).toId(toId).type(1).build();
        return relationshipMapper.save(relationship);
    }

    @Override
    public Relationship updateLink(Relationship relationship){
        return relationshipMapper.updateLink(relationship.getId(),relationship.getName());
    }

    @Override
    public void deleteLink(Relationship relationship){
        relationshipMapper.delete(relationship);
    }

}
