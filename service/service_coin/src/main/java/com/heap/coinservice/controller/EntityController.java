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
@CrossOrigin
@RequestMapping("/coinservice/entity")
public class EntityController {

    @Autowired
    private EntityService entityService;

    @PostMapping("/createNode")
    public Result createNode(@RequestBody Entity entity){
        Entity newEntity = entityService.createNode(entity.getName(), entity.getBgColor(), entity.getShape(), entity.getDomainId());
        return newEntity!=null?Result.ok().data("entity", newEntity):Result.error().message("存在同名节点");
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

    @DeleteMapping("/deleteNode")
    public Result deleteNode(@RequestBody Entity entity){
        boolean flag = entityService.deleteNode(entity);
        return flag ? Result.ok() : Result.error().message("删除失败");
    }

}

