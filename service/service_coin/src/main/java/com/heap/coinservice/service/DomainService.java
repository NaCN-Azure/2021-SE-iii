package com.heap.coinservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heap.coinservice.entity.Domain;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
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
public interface DomainService extends IService<Domain> {

     /**
      *
      * 新增域
      * @param domain
      * @return
      */
     int createDomain(Domain domain);

     /**
      * 删除域（包括内部的关系节点）
      * @param domainId
      */
     void deleteDomain(int domainId);

     /**
      * 更新域信息
      * @param domain
      * @return
      */
     Domain updateDomain(Domain domain);

     /**
      * 返回当前域
      * @param domainId
      * @return
      */
     Domain getDomainById(int domainId);

     /**
      * 返回所有域的列表
      * @return
      */
     List<Domain> getAllDomain(String userId);

     /**
      * 模板创建
      * @param template
      */
     void getTemplate(int template, String userId) throws IOException;

}
