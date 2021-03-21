package com.heap.coinservice.service.impl;

import com.heap.coinservice.COINApplication;
import com.heap.coinservice.entity.Relationship;
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
public class RelationshipServiceImplTest {
    @Autowired
    RelationshipServiceImpl relationshipService;

    @Test
    public void createLink() {
        long fromId = 0L;
        long toId=1L;
        String name = "link";
//        Relationship actual=relationshipService.createLink(fromId,toId,name);
//        assertEquals((Long) 0L,(Long) actual.getFromId());
//        assertEquals((Long) 0L,(Long) actual.getToId());
//        assertEquals("link",actual.getName());
    }

    @Test
    public void updateLink() {
        long fromId = 0L;
        long toId=1L;
        String name = "link";
        Relationship test=Relationship.builder().build();
        test.setFromId(fromId);
        test.setToId(toId);
        test.setName(name);
//        Relationship actual=relationshipService.updateLink(test);
//        assertEquals(test.getFromId(),actual.getFromId());
//        assertEquals(test.getToId(),actual.getToId());
//        assertEquals(test.getName(),actual.getName());
    }

    @Test
    public void deleteLink() {
        long fromId = 0L;
        long toId=1L;
        String name = "link";
        long  id=0L;
        Relationship test=Relationship.builder().build();
        test.setFromId(fromId);
        test.setToId(toId);
        test.setName(name);
        test.setId(id);
//        boolean judge = relationshipService.deleteLink(test);
//        assertTrue(judge);
    }

    @Test
    public void getLinkByDomainId() {
//        List<Relationship> relationships = relationshipService.getLinkByDomainId(2);
//        assertEquals(1,relationships.size());
    }
}