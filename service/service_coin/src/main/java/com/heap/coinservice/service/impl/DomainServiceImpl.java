package com.heap.coinservice.service.impl;

import com.heap.coinservice.entity.Domain;
import com.heap.coinservice.entity.Entity;
import com.heap.coinservice.mapper.DomainMapper;
import com.heap.coinservice.mapper.EntityMapper;
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

    @Override
    public Domain createDomain(Domain domain){
        return domainMapper.createDomain(domain);
    }

    @Override
    public void deleteDomain(int domainId){
        domainMapper.deleteDomain(domainId);
    }

    @Override
    public Domain updateDomain(Domain domain){
        return domainMapper.updateDomain(domain.getName());
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
