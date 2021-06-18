package com.heap.coinservice.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.heap.coinservice.entity.Domain;
import com.heap.coinservice.entity.Entity;
import com.heap.coinservice.entity.Relationship;
import com.heap.coinservice.service.DomainService;
import com.heap.coinservice.service.EntityService;
import com.heap.coinservice.service.FileService;
import com.heap.coinservice.service.RelationshipService;
import com.heap.coinservice.utils.FileUtil;
import com.heap.commonutils.DefaultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 纳思彧
 * @since 2021-04-07
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
    public boolean createGraphByCsv(List<List<String>> content, int domainId){
        try {
            int size = content.size();
            for (int i = 0; i < size; i++) {
                List<String> points = content.get(i);
                Entity startNode, endNode;
                Entity check = entityService.findByName(points.get(0), domainId,true);
                if(check == null) {
                    startNode = entityService.createNode(points.get(0),Integer.parseInt(points.get(5)), points.get(3), domainId, DefaultUtil.DEFAULT_DESCRIPTION, DefaultUtil.DEFAULT_R, DefaultUtil.DEFAULT_FONT);
                }
                else {
                    startNode = check;
                }
                check = entityService.findByName(points.get(1), domainId,true);
                if(check == null) {
                    endNode = entityService.createNode(points.get(1), Integer.parseInt(points.get(6)), points.get(4), domainId, DefaultUtil.DEFAULT_DESCRIPTION, DefaultUtil.DEFAULT_R, DefaultUtil.DEFAULT_FONT);
                }
                else {
                    endNode = check;
                }
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
    public boolean createJsonCompany(JSONArray companies, int domainId){
        for(int i = 0; i < companies.size(); i++){
            JSONObject company = companies.getJSONObject(i);
            for(Map.Entry<String,Object> entry: company.entrySet()){
                String companyName = entry.getKey();//这里是公司
                Entity companyNode = entityService.createNode(companyName,DefaultUtil.DEFAULT_SHAPE,"公司", domainId,"", DefaultUtil.DEFAULT_R, DefaultUtil.DEFAULT_FONT);
                JSONArray people = (JSONArray) entry.getValue();
                for(int j = 0; j < people.size(); j++){
                    JSONObject person = people.getJSONObject(j);
                    String name = person.get("name").toString();
                    String sex = person.get("sex").toString();
                    String position = person.get("position").toString();
                    String year = person.get("year").toString();
                    Entity personNode = entityService.findByName(name,domainId,true);//同名检测
                    if(personNode == null) {
                        personNode = entityService.createNode(name, DefaultUtil.DEFAULT_SHAPE, "人", domainId, "性别：" + sex + ",出生日期：" + year, DefaultUtil.DEFAULT_R, DefaultUtil.DEFAULT_FONT);
                    }
                    relationshipService.createLink(companyNode.getId(), personNode.getId(), position);
                }
            }
        }
        return true;
    }

    @Override
    public boolean exportGraphXml(int domainId){
        List<Relationship> relationships = relationshipService.getLinkByDomainId(domainId);
        Domain domain = domainService.getDomainById(domainId);
        return FileUtil.createXml(relationships, domain);
    }

    @Override
    public boolean deleteFile(String domainName, int type){
        String fileName = FileUtil.getFileName(domainName, type);
        File file = new File(fileName);
        if(file.exists()){
            file.delete();
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean exportCSV(int domainId) throws TransformerConfigurationException, IOException, SAXException{
        List<Relationship> relationships = relationshipService.getLinkByDomainId(domainId);
        Domain domain = domainService.getDomainById(domainId);
        return FileUtil.outCsv(relationships,domain);
    }

}
