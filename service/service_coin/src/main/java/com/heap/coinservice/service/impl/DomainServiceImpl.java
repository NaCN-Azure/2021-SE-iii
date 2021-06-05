package com.heap.coinservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heap.coinservice.entity.Domain;
import com.heap.coinservice.entity.Entity;
import com.heap.coinservice.entity.Relationship;
import com.heap.coinservice.mapper.DomainMapper;
import com.heap.coinservice.mapper.EntityMapper;
import com.heap.coinservice.mapper.RelationshipMapper;
import com.heap.coinservice.service.DomainService;
import com.heap.coinservice.service.EntityService;
import com.heap.coinservice.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class DomainServiceImpl extends ServiceImpl<DomainMapper, Domain> implements DomainService {

    @Autowired
    private DomainMapper domainMapper;

    @Autowired
    private EntityService entityService;

    @Autowired
    private RelationshipService relationshipService;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private RelationshipMapper relationshipMapper;

    @Override
    public int createDomain(Domain domain){
        Domain checkDomain = domainMapper.selectDomainByName(domain.getName(),domain.getUserId());
        if(checkDomain != null){
            return -1;
        }
        else {
            domainMapper.createDomain(domain);
            return domainMapper.lastInsertId();
        }
    }

    @Override
    public void deleteDomain(int domainId){
        domainMapper.deleteDomain(domainId);
        List<Entity> entities = entityMapper.getNodeByDomainId(domainId);
        List<Relationship> relationships = relationshipMapper.getLinkByDomainId(domainId);
        for(Relationship relationship : relationships){
            relationshipService.deleteLink(relationship);
        }
        for(Entity entity : entities){
            entityService.deleteNode(entity);
        }
    }

    @Override
    public Domain updateDomain(Domain domain){
         domainMapper.updateDomain(domain.getName(), domain.getId());
         return domainMapper.selectDomain(domain.getId());
    }

    @Override
    public Domain getDomainById(int domainId){
        return domainMapper.selectDomain(domainId);
    }

    @Override
    public List<Domain> getAllDomain(String userId){
        return domainMapper.selectAllDomain(userId);
    }
}
