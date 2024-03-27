package com.example.springboot.service.impl;

import com.example.springboot.entity.Cat;
import com.example.springboot.mapper.CatMapper;
import com.example.springboot.service.ICatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 
 */
@Service
public class CatServiceImpl extends ServiceImpl<CatMapper, Cat> implements ICatService {

}
