package com.heap.coinservice.controller;


import com.heap.coinservice.entity.Domain;
import com.heap.coinservice.entity.Entity;
import com.heap.coinservice.entity.Relationship;
import com.heap.coinservice.service.RelationshipService;
import com.heap.commonutils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
@RequestMapping("/coinservice/relationship")
public class RelationshipController {

    @Autowired
    private RelationshipService relationshipService;

    @PostMapping("/createLink/{fromId}/{toId}/{name}")
    public Result createLink(@PathVariable Long fromId, @PathVariable Long toId, @PathVariable String name){
        Relationship relationship = relationshipService.createLink(fromId,toId,name);
        return Result.ok().data("relationship", relationship);
    }

    @PostMapping("/updateLink")
    public Result updateLink(@RequestBody Relationship relationship){
        Relationship newRelationship = relationshipService.updateLink(relationship);
        return Result.ok().data("newRelationship", newRelationship);
    }

    @PostMapping("/deleteLink")
    public Result deleteLink(@RequestBody Relationship relationship){
        boolean flag = relationshipService.deleteLink(relationship);
        return flag ? Result.ok() : Result.error().message("删除失败");
    }

    @GetMapping("/getLinkByDomainId/{domainId}")
    public Result getGraph(@PathVariable int domainId){
        List<Relationship> relationshipList = relationshipService.getLinkByDomainId(domainId);
        return Result.ok().data("relationships", relationshipList);
    }

    @GetMapping("/countLink/{domainId}")
    public Result countLink(@PathVariable int domainId){
        return Result.ok().data("total", relationshipService.countAllLink(domainId));
    }

    @GetMapping("/getLinkScreen/{domainId}")
    public Result getGraphScreen(@PathVariable int domainId, @RequestBody List<String> types, @RequestBody List<String> entities){
        //TODO
        //types:表示能显示的节点类型一栏，entities表示显示该节点及其附属所有关系的图谱，二者可以进行叠加
        //如果传入的是全type或者entities则不做相应筛选，注意这两者不能全为空
        return Result.ok();
    }

    @GetMapping("/searchLink/{domainId}/{searchName}")
    public Result searchLink(@PathVariable int domainId,@PathVariable String searchName){
        //TODO
        //模糊查询关系
        return Result.ok();
    }

}

