package com.heap.coinservice.service.impl;

import com.heap.coinservice.entity.Domain;
import com.heap.coinservice.entity.Entity;
import com.heap.coinservice.entity.Relationship;
import com.heap.coinservice.mapper.DomainMapper;
import com.heap.coinservice.mapper.EntityMapper;
import com.heap.coinservice.mapper.RelationshipMapper;
import com.heap.coinservice.service.DomainService;
import com.heap.coinservice.service.EntityService;
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
public class DomainServiceImpl implements DomainService {

    @Autowired
    private DomainMapper domainMapper;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private RelationshipMapper relationshipMapper;

    @Override
    public int createDomain(Domain domain){
        Domain checkDomain=domainMapper.selectDomainByName(domain.getName());
        if(checkDomain!=null){
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
        List<Entity> entities=entityMapper.getNodeByDomainId(domainId);
        List<Relationship> relationships=relationshipMapper.getLinkByDomainId(domainId);
        for(Entity entity:entities){
            entityMapper.delete(entity);
        }
        for(Relationship relationship:relationships){
            relationshipMapper.delete(relationship);
        }
    }

    @Override
    public Domain updateDomain(Domain domain){
         domainMapper.updateDomain(domain.getName(),domain.getId());
         return domainMapper.selectDomain(domain.getId());
    }

    @Override
    public Domain getDomainById(int domainId){
        return domainMapper.selectDomain(domainId);
    }

    @Override
    public List<Domain> getAllDomain(){
        return domainMapper.selectAllDomain();
    }
}
