package com.heap.coinservice.service.impl;

import com.heap.coinservice.COINApplication;
import com.heap.coinservice.controller.DomainController;
import com.heap.coinservice.entity.Domain;
import com.heap.coinservice.entity.Entity;
import com.heap.commonutils.Result;
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
public class DomainServiceImplTest {
    @Autowired
    DomainServiceImpl domainService;


    @Test
    public void createDomain() {
        Domain domain = Domain.builder().build();
        domain.setId(3);
        domain.setName("wb");
//        int actual = domainService.createDomain(domain);
        int expect = 3;
//        assertEquals(actual, expect);
    }

    @Test
    public void deleteDomain() {
        int expect = 1;
//        domainService.deleteDomain(expect);
    }

    @Test
    public void updateDomain() {
        int expectId = 2;
        String expectName="coin";
        Domain given= Domain.builder().build();
        given.setName(expectName);
        given.setId(expectId);
//        Domain actual=domainService.updateDomain(given);
//        assertEquals(actual.getId(),given.getId());
//        assertEquals(actual.getName(),given.getName());
    }

    @Test
    public void getDomainById() {
//        Domain actual=domainService.getDomainById(2);
//        assertEquals(2,actual.getId());
//        assertEquals("coin",actual.getName());
    }

    @Test
    public void getAllDomain() {
//        List<Domain> domains=domainService.getAllDomain();
//        assertEquals(2,domains.size());
    }
}