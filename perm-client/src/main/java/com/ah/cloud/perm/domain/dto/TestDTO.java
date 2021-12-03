package com.ah.cloud.perm.domain.dto;

/**
 * @program: permissions-center
 * @description: 测试DTO
 * @author: YuKai Fan
 * @create: 2021-12-03 10:31
 **/
public class TestDTO {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String userName;

    public TestDTO(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public TestDTO() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
