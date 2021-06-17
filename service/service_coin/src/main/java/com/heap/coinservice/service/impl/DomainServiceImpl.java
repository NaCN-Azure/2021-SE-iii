package com.heap.coinservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heap.coinservice.entity.Domain;
import com.heap.coinservice.entity.Entity;
import com.heap.coinservice.entity.Relationship;
import com.heap.coinservice.mapper.DomainMapper;
import com.heap.coinservice.mapper.EntityMapper;
import com.heap.coinservice.mapper.RelationshipMapper;
import com.heap.coinservice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
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
    private QuestionService questionService;

    @Autowired
    private FileService fileService;

    @Autowired
    private RelationshipService relationshipService;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private RelationshipMapper relationshipMapper;

    @Override
    public int createDomain(Domain domain){
        Domain checkDomain = domainMapper.selectDomainByName(domain.getName(),domain.getUser_id());
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
//        questionService.clean(entities,relationships);
        for(Relationship relationship : relationships){
            relationshipService.deleteLink(relationship);
        }
        for(Entity entity : entities){
            entityService.deleteNode(entity);
        }
    }

    @Override
    public Domain updateDomain(Domain domain){
        baseMapper.updateById(domain);
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

    @Override
    public void getTemplate(int template, String userId) throws IOException {
        String path = "service/DomainDefault/";
        String filename = "";
        String domainName = "";
        if(template == 1){
            filename = "pokemon.csv";
            domainName = "宝可梦";
        }
        else if(template == 2){
            filename = "chemistry.csv";
            domainName = "化学";
        }
        else if(template == 3){
            filename = "finanial.csv";
            domainName = "金融知识图谱";
        }
        else if(template == 4){
            filename = "movie.csv";
            domainName = "电影知识图谱";
        }
        File file = new File(path + filename);
        List<List<String>> rowList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        while ((line = reader.readLine()) != null) {
            String[] rowArr = line.split(",");
            List<String> row = Arrays.asList(rowArr);
            rowList.add(row);
        }
        Domain csvDomain = Domain.builder().name(domainName).user_id(userId).build();
        int domainId = this.createDomain(csvDomain);
        fileService.createGraphByCsv(rowList, domainId);
    }
}
