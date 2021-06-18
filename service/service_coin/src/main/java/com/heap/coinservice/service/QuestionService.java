package com.heap.coinservice.service;

import com.heap.coinservice.entity.Entity;
import com.heap.coinservice.entity.Relationship;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 纳思彧
 * @since 2021-06-07
 */
@Service
public interface QuestionService {

    /**
     * 脚本调用（废弃）
     * 返回第一个命令参数
     * 后面为未知的查询变量
     * @param question
     * @return
     */
    List<String> dealByPython(String question);

    /**
     * 得到答案
     * @param domainId
     * @param members
     * @return
     */
    String getAnswer(int domainId,int commands,List<String> members);

    /**
     * 加入字典
     * @param x
     */
    void AddDict(String x,String type);

    /**
     * 删除字典（暂时废弃）
     */
    void clean(List<Entity> entity,List<Relationship> relation);

}
