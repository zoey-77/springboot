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
import java.util.Objects;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.common.Result;
import org.springframework.web.multipart.MultipartFile;
import com.example.springboot.entity.User;
import com.example.springboot.utils.TokenUtils;
import com.example.springboot.service.IUserService;

import com.example.springboot.service.ICommentService;
import com.example.springboot.entity.Comment;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 评论表 前端控制器
 * </p>
 *
 * @author 
 * @since 
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private ICommentService commentService;
    @Resource
    private IBlogService blogService;
    @Resource
    IUserService userService;

    private final String now = DateUtil.now();

    /**
     * 博客评论树结构接口
     * @param foreignId 博客id
     * @return
     */
    @GetMapping("/tree")
    public Result list(@RequestParam Integer foreignId) {
        List<Comment> list = commentService.list(new LambdaQueryWrapper<Comment>().eq(Comment::getForeignId,foreignId));
        // 给评论赋值用户相关属性
        list.stream().forEach(comment -> {
            if (null != comment.getPid()){
                comment.setParentName(userService.getById(commentService.getById(comment.getPid()).getUserId()).getNickname());
            }
            comment.setUsername(userService.getById(comment.getUserId()).getNickname());
            comment.setImg(userService.getById(comment.getUserId()).getAvatarUrl());
        });

        // 一级评论
        List<Comment> first = list.stream().filter(comment -> comment.getPid() == null).collect(Collectors.toList());
        for (Comment comment : first) {
            Integer pid = comment.getId();
            // 二级评论
            List<Comment> second = list.stream().filter(comment1 -> Objects.equals(pid, comment1.getPid())).collect(Collectors.toList());
            //将子评论放到children集合里
            comment.setChildren(second);

        }
        return Result.success(first);
    }

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Comment comment) {
        if (comment.getId() == null) {
            comment.setTime(DateUtil.now());
        }
        commentService.saveOrUpdate(comment);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        commentService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        commentService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(commentService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(commentService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "") String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }

        Page<Comment> page = commentService.page(new Page<>(pageNum, pageSize), queryWrapper);
        page.getRecords().stream().forEach(comment -> {
            comment.setUsername(userService.getById(comment.getUserId()).getNickname());
            comment.setBlogName(blogService.getById(comment.getForeignId()).getName());
        });
        return Result.success(page);
    }


}

