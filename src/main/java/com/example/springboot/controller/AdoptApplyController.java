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
import com.example.springboot.entity.AdoptApply;
import com.example.springboot.entity.Adopt;
import com.example.springboot.service.IAdoptApplyService;
import com.example.springboot.service.IAdoptService;
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
@RequestMapping("/adoptApply")
public class AdoptApplyController {

    @Resource
    private IAdoptApplyService adoptApplyService;
    @Resource
    private IAdoptService adoptService;
    @Resource
    IUserService userService;

    private final String now = DateUtil.now();

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody AdoptApply adoptApply) {
        Adopt adopt = adoptService.getById(adoptApply.getAdoptId());
        if (adoptApply.getId() == null) {
            AdoptApply one = adoptApplyService.getOne(new LambdaQueryWrapper<AdoptApply>().eq(AdoptApply::getUser, adoptApply.getUser()).eq(AdoptApply::getAdoptId, adoptApply.getAdoptId()));
            if (one != null){
                return Result.error("201","已申请领养，请勿重复申请");
            }
            adoptApplyService.save(adoptApply);
        } else {
            if (adoptApply.getState().equals("审核通过")){
                adopt.setState("已领养");
                adoptService.updateById(adopt);
            }
            adoptApplyService.updateById(adoptApply);
        }
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        adoptApplyService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        adoptApplyService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(adoptApplyService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(adoptApplyService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "") String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<AdoptApply> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        User currentUser = TokenUtils.getCurrentUser();
        if (currentUser.getRole().equals("ROLE_USER")) {
            queryWrapper.eq("user", currentUser.getUsername());
        }
        return Result.success(adoptApplyService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    private User getUser() {
        return TokenUtils.getCurrentUser();
    }

}

