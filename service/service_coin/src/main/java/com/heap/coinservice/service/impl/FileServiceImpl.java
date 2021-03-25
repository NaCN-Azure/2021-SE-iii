package com.heap.coinservice.service.impl;

import com.heap.coinservice.entity.Domain;
import com.heap.coinservice.entity.Entity;
import com.heap.coinservice.entity.Relationship;
import com.heap.coinservice.mapper.EntityMapper;
import com.heap.coinservice.service.DomainService;
import com.heap.coinservice.service.EntityService;
import com.heap.coinservice.service.FileService;
import com.heap.coinservice.service.RelationshipService;
import com.heap.coinservice.utils.FileUtil;
import com.heap.commonutils.DefaultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
public class FileServiceImpl implements FileService {
    @Autowired
    private EntityService entityService;

    @Autowired
    private RelationshipService relationshipService;

    @Autowired
    private DomainService domainService;

    @Override
    public boolean createGraphByCsv(List<List<String>> content,int domainId){
        try {
            int size = content.size();
            for (int i = 0; i < size; i++) {
                List<String> points = content.get(i);
                Entity startNode,endNode;
                Entity check=entityService.findByName(points.get(0),domainId);
                if(check==null) {
                    startNode = entityService.createNode(points.get(0), DefaultUtil.DEFAULT_COLOR, DefaultUtil.DEFAULT_TYPE, domainId);
                }
                else startNode=check;
                check=entityService.findByName(points.get(1),domainId);
                if(check==null) {
                    endNode = entityService.createNode(points.get(1), DefaultUtil.DEFAULT_COLOR, DefaultUtil.DEFAULT_TYPE, domainId);
                }
                else endNode=check;
                relationshipService.createLink(startNode.getId(), endNode.getId(), points.get(2));
            }
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean exportGraphXml(int domainId){
        List<Relationship> relationships=relationshipService.getLinkByDomainId(domainId);
        Domain domain=domainService.getDomainById(domainId);
        return FileUtil.createXml(relationships,domain);
    }

}
