package com.heap.coinservice.controller;


import com.heap.coinservice.entity.Entity;
import com.heap.coinservice.service.EntityService;
import com.heap.coinservice.service.TypeService;
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

    @Autowired
    private TypeService typeService;

    @PostMapping("/createNode")
    public Result createNode(@RequestBody Entity entity){
        Entity newEntity = entityService.createNode(entity.getName(), entity.getBgColor(), entity.getShape(),entity.getType(), entity.getDomainId(),entity.getDescription());
        return newEntity != null ? Result.ok().data("entity", newEntity) : Result.error().message("存在同名节点");
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

    @GetMapping("/countNode/{domainId}")
    public Result countNode(@PathVariable int domainId){
        return Result.ok().data("total", entityService.countAllEntity(domainId));
    }

    @GetMapping("/countNodeByType/{domainId}/{type}")
    public Result countNodeByType(@PathVariable int domainId,@PathVariable String type){
        return Result.ok().data("total", entityService.countEntitiesByType(domainId,type));
    }

    @PostMapping("/updateXY")
    public Result updateXY(@RequestBody List<Entity> entities){
        for(Entity entity:entities) {
            entityService.updateXY(entity.getId(), entity.getX(), entity.getY());
        }
        return Result.ok().message("location save!");
    }

    @GetMapping("/getTypes")
    public Result getTypes(){
        return Result.ok().data("Types",typeService.searchAll());
    }

    @PostMapping("/updateAllColors/{type}/{color}")
    public Result updateAllColors(@PathVariable String type,@PathVariable String color){
        entityService.updateColors(type,color);
        return Result.ok().message("Already change");
    }

    @PostMapping("/createType/{type}/{color}")
    public Result createType(@PathVariable String type,@PathVariable String color){
        String checkColor=typeService.searchColorByType(type);
        if(checkColor==null){
            return Result.ok().data("done!",typeService.insertType(color,type));
        }
        else return Result.error().message("type exists");
    }

    @GetMapping("/getNodeByType/{domainId}/{type}")
    public Result getNodeByType(@PathVariable int domainId,@PathVariable String type){
        return Result.ok().data("TypeNodes",entityService.getNodeByType(domainId,type));
    }

}

