package com.heap.coinservice.service;

import com.heap.coinservice.entity.Domain;
import com.heap.coinservice.entity.Entity;
import org.springframework.stereotype.Service;

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
public interface DomainService {

     Domain createDomain(Domain domain);

     void deleteDomain(int domainId);

     Domain updateDomain(Domain domain);

     Domain getDomainById(int domainId);

     List<Domain> getAllDomain();

}
