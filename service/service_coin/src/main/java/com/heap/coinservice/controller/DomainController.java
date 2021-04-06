package com.heap.coinservice.controller;

import com.heap.coinservice.entity.Domain;
import com.heap.coinservice.service.DomainService;
import com.heap.commonutils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coinservice/domain")
@CrossOrigin
public class DomainController {

    @Autowired
    private DomainService domainService;

    @PostMapping("/createDomain")
    public Result createDomain(@RequestBody Domain domain) {
        int flag=domainService.createDomain(domain);
        return flag != -1 ? Result.ok().message("创建成功") : Result.error().message("名称重复");
    }

    @PostMapping("/updateDomain")
    public Result updateDomain(@RequestBody Domain domain){
        return Result.ok().data("domain", domainService.updateDomain(domain));
    }

    @DeleteMapping("/deleteDomain/{domainId}")
    public Result deleteDomain(@PathVariable int domainId){
        domainService.deleteDomain(domainId);
        return Result.ok().message("删除成功");
    }

    @GetMapping("/selectDomainById/{domainId}")
    public Result selectDomainById(@PathVariable int domainId){
        return Result.ok().data("domain", domainService.getDomainById(domainId));
    }

    @GetMapping("/selectAllDomain")
    public Result selectAllDomain(){
        return Result.ok().data("domain", domainService.getAllDomain());
    }

}
