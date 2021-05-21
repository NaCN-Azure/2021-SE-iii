package com.heap.coinservice.utils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.heap.coinservice.entity.Domain;
import com.heap.coinservice.entity.Relationship;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.helpers.AttributesImpl;

import javax.management.relation.Relation;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FileUtil {

    public static String FILEPATH="";

    public static List<List<String>> readCsv(MultipartFile file) throws IOException {
        List<List<String>> rowList = new ArrayList<List<String>>();
        String charset = "GBK";
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(),charset));
        String line = null;
        while ((line = reader.readLine()) != null) {
            String[] rowArr = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
            List<String> row = Arrays.asList(rowArr);
            rowList.add(row);
        }
        return rowList;
    }

    public static String getFileName(String domainName, int type){
        String filename = "";
        if(type == 1){
            filename = domainName + ".xml";
        }
        return FILEPATH + filename;
    }

    public static boolean createXml(List<Relationship> relationships, Domain domain){
        int domainId = domain.getId();
        String domainName = domain.getName();
        try {
            SAXTransformerFactory factory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
            TransformerHandler handler = factory.newTransformerHandler();
            Transformer transformer = handler.getTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            Result res = new StreamResult(new FileOutputStream(FILEPATH + domainName + ".xml"));
            handler.setResult(res);
            handler.startDocument();
            AttributesImpl attributes = new AttributesImpl();

            attributes.addAttribute("", "id", "id", "", domainId+"");
            handler.startElement("", "domain", "domain", attributes);
            handler.characters(domainName.toCharArray(), 0, domainName.length());
            handler.endElement("", "domain", "domain");
            attributes.clear();

            handler.startElement("", "Relationships", "Relationships", attributes);
            for (Relationship rs : relationships) {
                attributes.clear();
                if(rs.getId() != null) {
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
                if(rs.getName() != null) {
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

    public static JSONArray readJsonCompany(MultipartFile file) throws IOException{
        String parserWord = readJsonFile(file).replace("'","\"").replace("None","\"Unknown\"");
        return JSONArray.parseArray(parserWord);
    }

    private static String readJsonFile(MultipartFile file) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        String line=reader.readLine();
        String result="";
        while (line != null) {
            result=result+line;
            line=reader.readLine();
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        String jsonStr="";
        File jsonFile = new File("F:\\大三下\\数据集成\\test.json");
            try {
                FileReader fileReader = new FileReader(jsonFile);
                Reader reader = new InputStreamReader(new FileInputStream(jsonFile));
                int ch = 0;
                StringBuffer sb = new StringBuffer();
                while ((ch = reader.read()) != -1) {
                    sb.append((char) ch);
                }
                fileReader.close();
                reader.close();
                jsonStr = sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        String parserWord = jsonStr.replace("'","\"").replace("None","\"Unknown\"");
        JSONArray companys = JSONArray.parseArray(parserWord);
        System.out.println(companys.size());
        for(int i=0;i<companys.size();i++){
            JSONObject company = companys.getJSONObject(i);
            for(Map.Entry<String,Object> entry: company.entrySet()){
                System.out.println(entry.getKey());
                JSONArray people = (JSONArray) entry.getValue();
                for(int j=0;j<people.size();j++){
                    JSONObject person = people.getJSONObject(j);
                    String name = person.get("name").toString();
                    String sex = person.get("sex").toString();
                    String position = person.get("position").toString();
                    String year = person.get("year").toString();
                    //System.out.println(name+" "+sex+" "+position+" "+year);

                }
            }
        }
        System.out.println("----------------------");
        System.out.println(companys.get(3556).toString());
    }
}
