package com.heap.coinservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heap.coinservice.entity.Domain;
import com.heap.coinservice.entity.query.DomainQuery;
import com.heap.coinservice.service.DomainService;
import com.heap.commonutils.DefaultUtil;
import com.heap.commonutils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 车一晗
 * @since 2021-03-09
 */
@RestController
@RequestMapping("/coinservice/domain")
@CrossOrigin
public class DomainController {

    @Autowired
    private DomainService domainService;

    @PostMapping("/createDomain")
    public Result createDomain(@RequestBody Domain domain) {
        int flag = domainService.createDomain(domain);
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
        DefaultUtil.NOW_DOMAIN_ID=domainId;
        return Result.ok().data("domain", domainService.getDomainById(domainId));
    }

    @GetMapping("/selectAllDomain/{userId}")
    public Result selectAllDomain(@PathVariable String userId){
        return Result.ok().data("domain", domainService.getAllDomain(userId));
    }

    //分页条件查询用户
    @PostMapping("getDomainListPage/{current}/{limit}")
    public Result getDomainListPage(@PathVariable long current, @PathVariable long limit, @RequestBody DomainQuery domainQuery) {
        //创建page对象
        Page<Domain> pageDomain = new Page<>(current, limit);

        //构建条件
        QueryWrapper<Domain> wrapper = new QueryWrapper<>();
        //多条件组合查询（某一条件可能有也可能没有）
        //判断条件值是否为空，不过不为空则拼接条件
        String name = domainQuery.getName();
        String userId = domainQuery.getUserId();
        String begin = domainQuery.getBegin();
        String end = domainQuery.getEnd();
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name", name);
        }
        if(!StringUtils.isEmpty(userId)){
            wrapper.eq("user_id", userId);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("create_time", begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("create_time", end);
        }

        //排序
        wrapper.orderByDesc("create_time");

        //调用方法，实现分页
        //调用方法的时候，底层封装，把分页所有数据封装到pageUser对象里面
        domainService.page(pageDomain, wrapper);

        //总记录数
        long total = pageDomain.getTotal();
        //数据list集合
        List<Domain> records = pageDomain.getRecords();

        return Result.ok().data("total", total).data("rows", records);
    }

    //根据模板查阅
    @GetMapping("/getTemplate/{template}/{userId}")
    public Result getTemplate(@PathVariable String template, @PathVariable String userId) throws IOException {
        domainService.getTemplate(Integer.parseInt(template), userId);
        return Result.ok().message("图谱" + template + "号模板成功");
    }

}
