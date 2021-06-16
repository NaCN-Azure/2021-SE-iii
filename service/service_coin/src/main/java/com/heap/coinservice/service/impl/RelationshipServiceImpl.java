package com.heap.coinservice.service.impl;

import com.heap.coinservice.entity.Entity;
import com.heap.coinservice.entity.Relationship;
import com.heap.coinservice.mapper.EntityMapper;
import com.heap.coinservice.mapper.RelationshipMapper;
import com.heap.coinservice.service.QuestionService;
import com.heap.coinservice.service.RelationshipService;
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
public class RelationshipServiceImpl implements RelationshipService {
    @Autowired
    QuestionService questionService;

    @Autowired
    RelationshipMapper relationshipMapper;

    @Autowired
    EntityMapper entityMapper;


    @Override
    public Relationship createLink(Long fromId, Long toId, String name){
        Entity startNode = entityMapper.findById(fromId).get();
        Entity endNode = entityMapper.findById(toId).get();
        Relationship check = relationshipMapper.findByToNodes(fromId,toId);
        if(check==null) {
            Relationship relationship = Relationship.builder().startEntity(startNode).endEntity(endNode)
                    .domainId(startNode.getDomainId()).name(name).fromId(fromId).toId(toId).type(1).build();
            questionService.AddDict(name,"nl");
            return relationshipMapper.save(relationship);
        }
        else {
            relationshipMapper.updateLink(check.getId(),check.getName()+","+name);
            return check;
        }
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
//            questionService.clean(relationship.getName());
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public List<Relationship> getLinkByDomainId(int domainId){
        List<Relationship> graph = relationshipMapper.getLinkByDomainId(domainId);
        List<Entity> noneNodes = entityMapper.getUnLinkNodes(domainId);
        for(Entity node : noneNodes){
            Relationship singleRelationship = Relationship.builder().domainId(domainId).startEntity(node).endEntity(node)
                    .fromId(node.getId()).toId(node.getId()).type(-1).build();
            graph.add(singleRelationship);//孤立节点的关系type是-1 初始节点和终止节点都是它自己
        }
        return graph;
    }

    @Override
    public int countAllLink(int domainId){
        return relationshipMapper.countAllLink(domainId);
    }

    @Override
    public List<Relationship> getLinkByName(int domainId,String name){
        List<Relationship> graph = relationshipMapper.getLinkByDomainId(domainId);
        List<Relationship> result=new ArrayList<>();
        for(Relationship re:graph){
            if(re.getName().contains(name)){
                result.add(re);
            }
        }
        return result;
    }

    @Override
    public List<Relationship> getGraphScreen(int domainId,List<Entity> entities){
        List<Relationship> graph=getLinkByDomainId(domainId);
        List<Relationship> result=new ArrayList<>();
        for(Relationship check:graph){
            if(entities.contains(check.getStartEntity())||entities.contains(check.getEndEntity())){
                result.add(check);
            }
        }
        return result;
    }

}
