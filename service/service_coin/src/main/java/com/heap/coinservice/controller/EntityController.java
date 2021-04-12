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
        Entity newEntity = entityService.createNode(entity.getName(), entity.getShape(),entity.getType(), entity.getDomainId(),entity.getDescription(),entity.getR());
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

    @GetMapping("/getTypes/{domainId}")
    public Result getTypes(int domainId){
        return Result.ok().data("Types",typeService.searchAll(domainId));
    }

    @GetMapping("/getNodeByType/{domainId}/{type}")
    public Result getNodeByType(@PathVariable int domainId,@PathVariable String type){
        return Result.ok().data("TypeNodes",entityService.getNodeByType(domainId,type));
    }

    @GetMapping("/searchNodeByName/{domainId}/{searchName}")
    public Result searchNodeByName(@PathVariable int domainId,@PathVariable String searchName){
        return  Result.ok().data("SearchNodes",entityService.findByName(searchName,domainId,false));
    }

    @PostMapping("/updateType/{id}/{oldType}/{newType}/{domainId}")
    public Result updateType(@PathVariable Long id,@PathVariable String oldType,@PathVariable String newType,@PathVariable int domainId){
        entityService.updateType(id,oldType,newType,domainId);
        return Result.ok().message("Type Update");
    }

}

