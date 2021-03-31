package com.weijia.mhealth.service;

import com.github.pagehelper.PageInfo;
import com.weijia.mhealth.entity.Admin;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Wei Jia
 * @Date 2021/3/29 16:40
 * @Version 1.0
 */
@Service
public interface AdminService {

    Admin checkAdmin(Admin admin);

    Admin getAdminByName(String name);

    PageInfo<Admin> getAllAdmin(Integer pageNum, Integer pageSize);
}
