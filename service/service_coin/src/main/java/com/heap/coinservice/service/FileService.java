package com.heap.coinservice.service;

import com.heap.coinservice.entity.Relationship;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerConfigurationException;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 车一晗
 * @since 2021-03-07
 */
@Service
public interface FileService {

    /**
     * 根据csv文件解析的content生成图谱
     * @param content
     * @param domainId
     * @return
     */
    boolean createGraphByCsv(List<List<String>> content,int domainId);

    /**
     * 导出图谱为csv文件
     * @param domainId
     * @return
     * @throws TransformerConfigurationException
     * @throws FileNotFoundException
     * @throws SAXException
     */
    boolean exportGraphXml(int domainId) throws TransformerConfigurationException, FileNotFoundException, SAXException;

}
