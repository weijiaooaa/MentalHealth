package com.weijia.mhealth.mapper;

import com.weijia.mhealth.entity.Login;
import com.weijia.mhealth.entity.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * @Author Wei Jia
 * @Date 2021/2/4 17:34
 * @Version 1.0
 */
public interface LoginMapper {
    //判断登录
    @Select("select account_id from login where account_name=#{account_name} and password=#{password}")
    String justLogin(Login login);
    //根据账号查询用户ID
    @Select("select account_id from login where account_name=#{account_name}")
    String lkUseridByUsername(String username);
    @Insert("insert into login(account_id,account_name,password) values (#{account_id},#{account_name},#{password})")
    void insertLogin(Login login);
    @Insert("insert into userinfo(nickname,usign,userid,uimg) values (#{nickname},#{usign},#{userid},#{uimg})")
    void insertUserInfo(UserInfo userinfo);
}
