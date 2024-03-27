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
import com.example.springboot.entity.ActivityApply;
import com.example.springboot.service.IActivityApplyService;
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

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 申请审批表 前端控制器
 * </p>
 *
 * @author 
 * @since 
 */
@RestController
@RequestMapping("/activityApply")
public class ActivityApplyController {

    @Resource
    private IActivityApplyService activityApplyService;
    @Resource
    IUserService userService;

    private final String now = DateUtil.now();

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody ActivityApply activityApply) {
        if (activityApply.getId() == null) {
            ActivityApply one = activityApplyService.getOne(new LambdaQueryWrapper<ActivityApply>().eq(ActivityApply::getUser, activityApply.getUser()).eq(ActivityApply::getActivityId, activityApply.getActivityId()));
            if (one != null){
                return Result.error("201","已申请报名该活动，请勿重复申请");
            }
        }
        activityApplyService.saveOrUpdate(activityApply);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        activityApplyService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        activityApplyService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(activityApplyService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(activityApplyService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "") String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<ActivityApply> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        User currentUser = TokenUtils.getCurrentUser();
        if (currentUser.getRole().equals("ROLE_USER")) {
            queryWrapper.eq("user", currentUser.getUsername());
        }
        return Result.success(activityApplyService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }


    private User getUser() {
        return TokenUtils.getCurrentUser();
    }

}

