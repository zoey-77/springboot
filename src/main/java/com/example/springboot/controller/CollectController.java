package com.example.springboot.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelWriter;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletOutputStream;
import java.net.URLEncoder;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.service.IBlogService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.io.InputStream;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.common.Result;
import org.springframework.web.multipart.MultipartFile;
import com.example.springboot.entity.User;
import com.example.springboot.utils.TokenUtils;
import com.example.springboot.service.IUserService;

import com.example.springboot.service.ICollectService;
import com.example.springboot.entity.Collect;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 点赞表 前端控制器
 * </p>
 *
 * @author 
 * @since 
 */
@RestController
@RequestMapping("/collect")
public class CollectController {

    @Resource
    private ICollectService collectService;
    @Resource
    private IBlogService blogService;


    @Resource
    IUserService userService;

    private final String now = DateUtil.now();

    @GetMapping("/repeat/{foreignId}/{userId}")
    public Result repeat(@PathVariable Integer foreignId,@PathVariable Integer userId) {
        Collect one = collectService.getOne(new LambdaQueryWrapper<Collect>().eq(Collect::getUserId, userId).eq(Collect::getForeignId, foreignId));
        if (null != one) {
            return Result.error("202", "false");
        } else {
            return Result.success();
        }
    }

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Collect collect) {
        Collect one = collectService.getOne(new LambdaQueryWrapper<Collect>().eq(Collect::getUserId, collect.getUserId()).eq(Collect::getForeignId, collect.getForeignId()));
        if (null != one){
            collectService.remove(new LambdaQueryWrapper<Collect>().eq(Collect::getForeignId,collect.getForeignId()).eq(Collect::getUserId,collect.getUserId()));
            return Result.error("202","已取消点赞");
        }

        collect.setTime(DateUtil.now());
        collectService.save(collect);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        collectService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        collectService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(collectService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(collectService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "") String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }

        User currentUser = TokenUtils.getCurrentUser();
        if (currentUser.getRole().equals("ROLE_USER")) {
            queryWrapper.eq("user_id", currentUser.getId());
        }
        Page<Collect> page = collectService.page(new Page<>(pageNum, pageSize), queryWrapper);
        page.getRecords().stream().forEach(collect -> {
            collect.setBlog(blogService.getById(collect.getForeignId()));
            collect.setBlogName(blogService.getById(collect.getForeignId()).getName());
            collect.setUserName(userService.getById(collect.getUserId()).getNickname());
        });
        return Result.success(page);
    }

}

