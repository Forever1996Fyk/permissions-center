package com.ah.cloud.permissions.biz.domain.chatroom.form;

import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-23 17:40
 **/
@Data
public class ChatRoomUpdateForm {

    /**
     * 聊天室主键
     */
    private Long id;

    /**
     * 聊天室名称
     */
    @NotEmpty(message = "聊天室名称不能为空")
    private String roomName;

    /**
     * 最大聊天室数量
     */
    @Min(message = "不能小于0", value = PermissionsConstants.ZERO)
    private Long maxRoomSize;

    /**
     * 聊天室头像
     */
    private String roomAvatar;

    /**
     * 所属id
     */
    @NotNull(message = "所属id不能为空")
    private Long ownerId;
}
