package com.heap.coinservice.service.impl;

import com.heap.coinservice.entity.Entity;
import com.heap.coinservice.entity.Relationship;
import com.heap.coinservice.service.DomainService;
import com.heap.coinservice.service.EntityService;
import com.heap.coinservice.service.FileService;
import com.heap.coinservice.service.RelationshipService;
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
                Entity startNode = entityService.createNode(points.get(0), DefaultUtil.DEFAULT_COLOR, DefaultUtil.DEFAULT_TYPE, domainId);
                Entity endNode = entityService.createNode(points.get(1), DefaultUtil.DEFAULT_COLOR, DefaultUtil.DEFAULT_TYPE, domainId);
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
    public boolean exportGraphXml(int domainId) throws TransformerConfigurationException, FileNotFoundException, SAXException {
        List<Relationship> relationships=relationshipService.getLinkByDomainId(domainId);
        String domainName=domainService.getDomainById(domainId).getName();
        try {
            SAXTransformerFactory factory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
            TransformerHandler handler = factory.newTransformerHandler();
            Transformer transformer = handler.getTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            Result res = new StreamResult(new FileOutputStream("example.xml"));
            handler.setResult(res);
            handler.startDocument();
            AttributesImpl attributes = new AttributesImpl();

            attributes.addAttribute("","id","id","",domainId+"");
            handler.startElement("","domain","domain",attributes);
            handler.characters(domainName.toCharArray(),0,domainName.length());
            handler.endElement("","domain","domain");
            attributes.clear();

            handler.startElement("", "Relationships", "Relationships", attributes);
            for (Relationship rs : relationships) {
                attributes.clear();
                if(rs.getId()!=null) {
                    attributes.addAttribute("", "id", "id", "", rs.getId() + "");
                }
                handler.startElement("", "Relationship", "Relationship", attributes);

                attributes.clear();
                attributes.addAttribute("", "id", "id", "", rs.getStartEntity().getId() + "");
                handler.startElement("", "startEntity", "startEntity", attributes);
                handler.characters(rs.getStartEntity().getName().toCharArray(), 0, rs.getStartEntity().getName().length());
                handler.endElement("", "startEntity", "startEntity");

                attributes.clear();
                attributes.addAttribute("", "id", "id", "", rs.getEndEntity().getId() + "");
                handler.startElement("", "EndEntity", "EndEntity", attributes);
                handler.characters(rs.getEndEntity().getName().toCharArray(), 0, rs.getEndEntity().getName().length());
                handler.endElement("", "EndEntity", "EndEntity");

                attributes.clear();
                handler.startElement("", "name", "name", attributes);
                if(rs.getName()!=null) {
                    handler.characters(rs.getName().toCharArray(), 0, rs.getName().length());
                }
                handler.endElement("", "name", "name");

                handler.startElement("", "type", "type", attributes);
                String linkType = rs.getType() == 1 ? "normal-link" : "single-node";
                handler.characters(linkType.toCharArray(), 0, linkType.length());
                handler.endElement("", "type", "type");

                handler.endElement("", "Relationship", "Relationship");
            }
            handler.endElement("", "Relationships", "Relationships");
            handler.endDocument();
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
