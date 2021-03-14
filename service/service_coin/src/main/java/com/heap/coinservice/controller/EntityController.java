package com.heap.coinservice.controller;


import com.heap.coinservice.entity.Entity;
import com.heap.coinservice.service.EntityService;
import com.heap.commonutils.Result;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 车一晗
 * @since 2021-03-07
 */
@RestController
@RequestMapping("/coinservice/entity")
public class EntityController {
    @Autowired
    private EntityService entityService;

    //TODO 这里是不是能够新增一个用户创建的节点实体类？
    @PostMapping("/createNode/{name}/{color}/{type}/{domainId}")
    public Result createNode(@PathVariable String name, @PathVariable String color, @PathVariable int type, @PathVariable int domainId){
        Entity entity = entityService.createNode(name,color,type,domainId);
        return Result.ok().data("entity", entity);
    }

    @GetMapping("/getNodesByDomainId/{domainId}")
    public Result getNodesByDomainId(@PathVariable int domainId){
        List<Entity> list = entityService.getNodeByDomainId(domainId);
        return Result.ok().data("nodesList", list);
    }

    @PostMapping("/updateNode")
    public Result updateNode(@RequestBody Entity entity){
        Entity newEntity = entityService.updateNode(entity);
        return Result.ok().data("newEntity", newEntity);
    }

    //TODO 删除方法返回值问题
    @DeleteMapping("/deleteNode")
    public Result deleteNode(@RequestBody Entity entity){
        entityService.deleteNode(entity);
        return Result.ok().message("done!");
    }

}

