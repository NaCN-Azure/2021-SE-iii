package com.heap.coinservice.controller;


import com.heap.coinservice.entity.Entity;
import com.heap.coinservice.service.EntityService;
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
@RequestMapping("/coinservice/entity")
public class EntityController {
    @Autowired
    EntityService entityService;

    @PostMapping("/createnode")
    public Result createnode(@RequestParam ("name") String name, @RequestParam("color") String color, @RequestParam("type") int type, @RequestParam("domainId") int domainId){
        return Result.ok().data("data",entityService.createNode(name,color,type,domainId));
    }

    @PostMapping("/getnodesbydomainid")
    public Result getNodesByDomainid(@RequestParam("domainId") int domainId){
        return Result.ok().data("data",entityService.getNodeByDomainId(domainId));
    }

    @PostMapping("/updatenode")
    public Result updateNode(@RequestBody Entity entity){
        return Result.ok().data("data",entityService.updateNode(entity));
    }

    @PostMapping("/deletenode")
    public Result deleteNode(@RequestBody Entity entity){
        entityService.deleteNode(entity);
        return Result.ok().message("done!");
    }

}

