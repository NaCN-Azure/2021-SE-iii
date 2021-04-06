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
    public String insertType(String color,String nodeType){
        String check=typeMapper.searchColorByType(nodeType);
        if(check==null){
            typeMapper.insertType(color,nodeType);
            return color;
        }
        else return check;
    }

    @Override
    public void updateColor(String type, String color){
        typeMapper.updateColor(type, color);
    }

    @Override
    public void deleteType(String type){
        typeMapper.deleteType(type);
    }

    @Override
    public String searchColorByType(String nodeType){
        return typeMapper.searchColorByType(nodeType);
    }

    @Override
    public List<String> searchAll(){
        return typeMapper.searchAll();
    }


}
