package com.heap.coinservice.service.impl;

import com.heap.coinservice.COINApplication;
import com.heap.coinservice.entity.Entity;
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
public class EntityServiceImplTest {
    @Autowired
    EntityServiceImpl entityService;

    @Test
    public void createNode() {
        Entity expect=Entity.builder().name("wb").bgColor("#000000").shape(1).domainId(2).build();
//        Entity actual=entityService.createNode("wb","#000000",1,2);
//        actual.setId(null);
//        assertEquals(expect,actual);
    }

    @Test
    public void getNodeByDomainId() {
//        List<Entity> entities=entityService.getNodeByDomainId(2);
//        assertEquals(1,entities.size());
    }

    @Test
    public void updateNode() {
        Entity expect=Entity.builder().name("ok").bgColor("#000000").build();
//        Entity actual=entityService.updateNode(expect);
//        assertEquals(expect,actual);
    }

    @Test
    public void deleteNode() {
        Entity expect=Entity.builder().name("ok").bgColor("#000000").build();
//        boolean judge=entityService.deleteNode(expect);
//        assertTrue(judge);
    }
}