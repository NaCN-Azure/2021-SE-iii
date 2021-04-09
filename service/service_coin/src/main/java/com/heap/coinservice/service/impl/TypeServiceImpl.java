package com.heap.coinservice.service.impl;

import com.heap.coinservice.mapper.TypeMapper;
import com.heap.coinservice.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 车一晗
 * @since 2021-03-07
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    TypeMapper typeMapper;

    @Override
    public String insertType(int domainId,String color,String nodeType){
        String check=typeMapper.searchColorByType(domainId,nodeType);
        if(check==null){
            typeMapper.insertType(domainId,color,nodeType);
            return color;
        }
        else return check;
    }

    @Override
    public void updateColor(int domainId,String type, String color){
        typeMapper.updateColor(domainId,type, color);
    }

    @Override
    public void deleteType(int domainId,String type){
        typeMapper.deleteType(domainId,type);
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
