package com.weijia.mhealth.service.Imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weijia.mhealth.entity.Admin;
import com.weijia.mhealth.entity.Doctor;
import com.weijia.mhealth.mapper.AdminMapper;
import com.weijia.mhealth.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Wei Jia
 * @Date 2021/3/29 16:40
 * @Version 1.0
 */
@Service
public class AdminServiceImp implements AdminService {
    private final static Logger logger = LoggerFactory.getLogger(AdminServiceImp.class);

    @Autowired
    AdminMapper adminMapper;

    @Override
    public Admin checkAdmin(Admin admin) {
        return adminMapper.checkAdmin(admin);
    }

    @Override
    public Admin getAdminByName(String name) {
        return adminMapper.getAdminByName(name);
    }

    @Override
    public PageInfo<Admin> getAllAdmin(Integer pageNum, Integer pageSize) {
        //使用分页插件,核心代码就这一行
        PageHelper.startPage(pageNum, pageSize);
        // 获取
        PageInfo<Admin> pageInfo = getPageInfo(pageNum, pageSize);
        return pageInfo;
    }

    private PageInfo<Admin> getPageInfo(Integer pageNum, Integer pageSize) {
        //判断非空
        if (pageNum == null) {
            pageNum = 1; //设置默认当前页
        }
        if (pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 5; //设置默认每页显示的数据数
        }
        //1.引入分页插件,pageNum是第几页，pageSize是每页显示多少条,默认查询总数count
        PageHelper.startPage(pageNum, pageSize);
        //2.紧跟的查询就是一个分页查询-必须紧跟.后面的其他查询不会被分页，除非再次调用PageHelper.startPage
        try {
            List<Admin> admins = adminMapper.getAllAdmin();
            PageInfo<Admin> pageInfo = new PageInfo<>(admins, pageSize);
            pageInfo.setList(admins);
            return pageInfo;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        return null;
    }
}
