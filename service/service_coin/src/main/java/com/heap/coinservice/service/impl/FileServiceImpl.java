package com.heap.coinservice.service.impl;

import com.heap.coinservice.entity.Entity;
import com.heap.coinservice.entity.Relationship;
import com.heap.coinservice.mapper.EntityMapper;
import com.heap.coinservice.mapper.RelationshipMapper;
import com.heap.coinservice.service.DomainService;
import com.heap.coinservice.service.EntityService;
import com.heap.coinservice.service.FileService;
import com.heap.coinservice.service.RelationshipService;
import com.heap.commonutils.DefaultUtil;
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
public class FileServiceImpl implements FileService {
    @Autowired
    private EntityService entityService;

    @Autowired
    private RelationshipService relationshipService;

    @Override
    public List<Relationship> createGraphByCsv(List<List<String>> content,int domainId){
        int size=content.size();
        for(int i=0;i<size;i++){
            List<String> points=content.get(i);
            Entity startNode=entityService.createNode(points.get(0), DefaultUtil.DEFAULT_COLOR,DefaultUtil.DEFAULT_TYPE,domainId);
            Entity endNode=entityService.createNode(points.get(1), DefaultUtil.DEFAULT_COLOR,DefaultUtil.DEFAULT_TYPE,domainId);
            relationshipService.createLink(startNode.getId(),endNode.getId(),points.get(2));
        }
        return relationshipService.getLinkByDomainId(domainId);
    }

}
