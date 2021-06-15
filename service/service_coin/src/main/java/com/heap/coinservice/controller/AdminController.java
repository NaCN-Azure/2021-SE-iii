package com.heap.coinservice.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.heap.commonutils.Result;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coinservice/admin")
@CrossOrigin
public class AdminController {

    @PostMapping("login")
    public Result login() {
        return Result.ok().data("token", "admin");
    }

    @GetMapping("info")
    public Result info() {
        return Result.ok().data("roles", "[admin]").data("name", "admin").data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
