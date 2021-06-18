package com.heap.coinservice.service.impl;

import com.heap.coinservice.mapper.TypeMapper;
import com.heap.coinservice.service.QuestionService;
import com.heap.coinservice.service.TypeService;
import com.heap.commonutils.DefaultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 纳思彧
 * @since 2021-04-07
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    TypeMapper typeMapper;

    @Autowired
    QuestionService questionService;

    @Override
    public String insertType(int domainId,String nodeType){
        String check=typeMapper.searchColorByType(domainId,nodeType);
        if(check==null){
            //DefaultUtil.checkColor(typeMapper.searchColor(domainId));
            String color= typeMapper.getNewColor(domainId);
            if(color == null || color.equals("")){
                color= DefaultUtil.DEFAULT_COLOR;
            }
            typeMapper.insertType(domainId,color,nodeType);
            questionService.AddDict(nodeType,"nr");
            return color;
        }
        else return check;
    }

    @Override
    public void deleteType(int domainId,String type){
        //String color = typeMapper.searchColorByType(domainId,type);
        //DefaultUtil.releaseColor(color);
        typeMapper.deleteType(domainId,type);
//        questionService.clean(type);
    }

    @Override
    public String searchColorByType(int domainId,String nodeType){
        return typeMapper.searchColorByType(domainId,nodeType);
    }

    @Override
    public List<String> searchAll(int domainId){
        return typeMapper.searchAll(domainId);
    }


}
