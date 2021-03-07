package com.heap.coinservice.service.impl;

import com.heap.coinservice.entity.Entity;
import com.heap.coinservice.mapper.EntityMapper;
import com.heap.coinservice.service.EntityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 车一晗
 * @since 2021-03-07
 */
@Service
public class EntityServiceImpl extends ServiceImpl<EntityMapper, Entity> implements EntityService {

}
