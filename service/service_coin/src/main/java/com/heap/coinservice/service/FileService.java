package com.heap.coinservice.service;

import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 纳思彧
 * @since 2021-04-07
 */
@Service
public interface FileService {

    /**
     * 根据csv文件解析的content生成图谱
     * @param content
     * @param domainId
     * @return
     */
    boolean createGraphByCsv(List<List<String>> content, int domainId);

    /**
     * 根据json格式创建
     * @param jsonArray
     * @param domainId
     * @return
     */
    boolean createJsonCompany(JSONArray jsonArray, int domainId);

    /**
     * 导出图谱为xml文件
     * @param domainId
     * @return
     * @throws TransformerConfigurationException
     * @throws FileNotFoundException
     * @throws SAXException
     */
    boolean exportGraphXml(int domainId) throws TransformerConfigurationException, FileNotFoundException, SAXException;

    /**
     * 下载结束删除缓存
     * @param domainName
     * @param type
     */
    boolean deleteFile(String domainName, int type);

    /**
     * 导出csv
     * @param domainId
     * @return
     * @throws TransformerConfigurationException
     * @throws FileNotFoundException
     * @throws SAXException
     */
    boolean exportCSV(int domainId) throws TransformerConfigurationException, IOException, SAXException;

}
