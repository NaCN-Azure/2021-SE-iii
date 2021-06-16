package com.heap.coinservice.service;

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
public interface QuestionService {

    /**
     * 脚本调用
     * 返回第一个命令参数
     * 后面为未知的查询变量
     * @param question
     * @return
     */
    List<String> dealByPython(String question);

    /**
     * 得到答案
     * @param domainId
     * @param commands
     * @return
     */
    String getAnswer(int domainId,List<String> commands);

    /**
     * 加入字典
     * @param x
     */
    void AddDict(String x);

    /**
     * 删除节点
     * @param x
     */
    void clean(String x);

}
