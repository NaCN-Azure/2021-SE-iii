package com.heap.coinservice.service.impl;

import com.heap.coinservice.COINApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        classes = COINApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@DirtiesContext
public class TypeServiceImplTest {
    @Autowired TypeServiceImpl typeService;

    @Test
    public void insertType() {
//        String result=typeService.insertType(0,"test");
//        assertEquals("#000000",result);
    }

    @Test
    public void deleteType() {
//        typeService.deleteType(0,"test");
//        assertNull(typeService.searchColorByType(0,"test"));
    }

    @Test
    public void searchColorByType() {
//        assertNull(typeService.searchColorByType(0,"test"));
    }

    @Test
    public void searchAll() {
//        List<String> result=typeService.searchAll(0);
//        assertEquals("test",result.get(0));
    }
}