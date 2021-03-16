package com.heap.coinservice.controller;


import com.heap.coinservice.entity.Domain;
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

    @DeleteMapping("/deleteLink")
    public Result deleteLink(@RequestBody Relationship relationship){
        boolean flag = relationshipService.deleteLink(relationship);
        return flag ? Result.ok() : Result.error().message("删除失败");
    }

    @GetMapping("/getLinkByDomainId/{domainId}")
    public Result getLinkByDomainId(@PathVariable int domainId){
        List<Relationship> relationshipList=relationshipService.getLinkByDomainId(domainId);
        return Result.ok().data("relationships",relationshipList);
    }


}

