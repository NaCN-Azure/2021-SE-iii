package com.heap.coinservice.service.impl;

import com.heap.coinservice.entity.Entity;
import com.heap.coinservice.entity.Relationship;
import com.heap.coinservice.mapper.EntityMapper;
import com.heap.coinservice.mapper.RelationshipMapper;
import com.heap.coinservice.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RelationshipServiceImpl implements RelationshipService {
    @Autowired
    RelationshipMapper relationshipMapper;

    @Autowired
    EntityMapper entityMapper;


    @Override
    public Relationship createLink(Long fromId, Long toId, String name){
        Entity startNode = entityMapper.findById(fromId).get();
        Entity endNode = entityMapper.findById(toId).get();

        Relationship relationship = Relationship.builder().startEntity(startNode).endEntity(endNode)
            .domainId(startNode.getDomainId()).name(name).fromId(fromId).toId(toId).type(1).build();

        return relationshipMapper.save(relationship);
    }

    @Override
    public Relationship updateLink(Relationship relationship){
        return relationshipMapper.updateLink(relationship.getId(), relationship.getName());
    }

    @Override
    public boolean deleteLink(Relationship relationship){
        Long id = relationship.getId();
        Optional<Relationship> check = relationshipMapper.findById(id);

        if(check.isPresent()) {
            relationshipMapper.delete(relationship);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public List<Relationship> getLinkByDomainId(int domainId){
        List<Relationship> graph=relationshipMapper.getLinkByDomainId(domainId);
        List<Entity> noneNodes=entityMapper.getUnLinkNodes(domainId);
        for(Entity node:noneNodes){
            Relationship singleRelationship=Relationship.builder().domainId(domainId).startEntity(node).endEntity(node)
                    .fromId(node.getId()).toId(node.getId()).type(-1).build();
            graph.add(singleRelationship);//孤立节点的关系type是-1 初始节点和终止节点都是它自己
        }
        return graph;
    }

}
