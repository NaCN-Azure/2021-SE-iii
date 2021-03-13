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
    RelationshipService relationshipService;

    @PostMapping("/createlink")
    public Result createLink(@RequestParam("fromId") Long fromId,@RequestParam("toId") Long toId,@RequestParam("name") String name){
        return Result.ok().data("data",relationshipService.createLink(fromId,toId,name));
    }

    @PostMapping("/updatelink")
    public Result updateLink(@RequestBody Relationship relationship){
        return Result.ok().data("data",relationshipService.updateLink(relationship));
    }

    @PostMapping("/deletelink")
    public Result deleteLink(@RequestBody Relationship relationship){
        relationshipService.deleteLink(relationship);
        return Result.ok().message("done!");
    }

    @PostMapping("/getlinkbydomainid")
    public Result getlinkbydomainid(@RequestParam Long domainId){
        return Result.ok();
    }



}

