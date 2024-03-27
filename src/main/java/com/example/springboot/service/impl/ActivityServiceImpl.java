package com.example.springboot.service.impl;

import com.example.springboot.entity.Activity;
import com.example.springboot.mapper.ActivityMapper;
import com.example.springboot.service.IActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 社团活动表 服务实现类
 * </p>
 *
 * @author 
 * @since 
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IActivityService {

}
