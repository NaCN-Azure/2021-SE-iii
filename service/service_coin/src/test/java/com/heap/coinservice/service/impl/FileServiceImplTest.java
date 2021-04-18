package com.heap.coinservice.service.impl;

import com.heap.coinservice.COINApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerConfigurationException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        classes = COINApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@DirtiesContext
public class FileServiceImplTest {
    @Autowired
    FileServiceImpl fileService;
    @Test
    public void createGraphByCsv() {
        try {
            List<List<String>> lists = new ArrayList<>();
            List<String> listA = new ArrayList<>();
            List<String> listB = new ArrayList<>();
            listA.add("A");
            listA.add("B");
            listA.add("1");
            listB.add("C");
            listB.add("D");
            listB.add("2");
            lists.add(listA);
            lists.add(listB);
//            boolean judge = fileService.createGraphByCsv(lists, 2);
//            assertTrue(judge);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void exportGraphXml() {
        try{
//        boolean judge = fileService.exportGraphXml(2);
//        assertTrue(judge);
    }catch (Exception e){
            e.printStackTrace();
        }

}

    @Test
    public void deleteFile() {
//        boolean judge= fileService.deleteFile("wb",0);
//        assertTrue(judge);
    }
}