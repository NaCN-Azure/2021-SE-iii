package com.heap.coinservice.controller;


import com.heap.coinservice.entity.Entity;
import com.heap.coinservice.entity.Relationship;
import com.heap.coinservice.mapper.RelationshipMapper;
import com.heap.coinservice.service.RelationshipService;
import com.heap.commonutils.Result;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 车一晗
 * @since 2021-03-07
 */
@RestController
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

    //TODO 删除方法返回值问题
    @PostMapping("/deleteLink")
    public Result deleteLink(@RequestBody Relationship relationship){
        relationshipService.deleteLink(relationship);
        return Result.ok().message("done!");
    }

    @GetMapping("/getLinkByDomainId/{domainId}")
    public Result getLinkByDomainId(@PathVariable Long domainId){
        return Result.ok();
    }



}

