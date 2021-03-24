package com.weijia.mhealth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Wei Jia
 * @Date 2021/2/4 17:09
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {

    private Integer id;
    private String doctorNumber;
    private String name;
    private String password;
    private Integer gender;
    private Integer age;
    private Integer teachYears;
    private String graduatedSchool;
    private String tel;
    private String email;
    private Boolean state;
    private Long gmtCreate;
    private Long gmtModified;

}
