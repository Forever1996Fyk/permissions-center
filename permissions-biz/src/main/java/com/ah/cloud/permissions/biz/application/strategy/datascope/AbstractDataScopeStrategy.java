package com.ah.cloud.permissions.biz.application.strategy.datascope;

import com.ah.cloud.permissions.biz.application.service.SysDeptRelationService;
import com.ah.cloud.permissions.biz.application.service.SysRoleService;
import com.ah.cloud.permissions.biz.application.service.ext.SysUserRoleExtService;
import com.ah.cloud.permissions.biz.application.strategy.facroty.DataScopeStrategyFactory;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysDeptRelation;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysRole;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUserRole;
import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
import com.ah.cloud.permissions.enums.DataScopeEnum;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-19 11:58
 **/
@Slf4j
@Component
public class AbstractDataScopeStrategy implements DataScopeStrategy {
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysUserRoleExtService sysUserRoleExtService;
    @Resource
    private SysDeptRelationService sysDeptRelationService;

    @Override
    public String getSql() {
        Long userId = SecurityUtils.getUserIdBySession();
        List<SysUserRole> sysUserRoleList = sysUserRoleExtService.list(
                new QueryWrapper<SysUserRole>().lambda()
                        .eq(SysUserRole::getUserId, userId)
                        .eq(SysUserRole::getDeleted, DeletedEnum.NO.value)
        );
        Set<String> roleCodeSet = sysUserRoleList.stream().map(SysUserRole::getRoleCode).collect(Collectors.toSet());
        List<SysRole> sysRoleList = sysRoleService.list(
                new QueryWrapper<SysRole>().lambda()
                        .in(SysRole::getRoleCode, roleCodeSet)
                        .eq(SysRole::getDeleted, DeletedEnum.NO.value)
        );
        Set<DataScopeEnum> dataScopeEnums = convert(sysRoleList);

        /*
        数据权限规则如下:
        1、如果存在全部, 那么直接返回空，也就是没有限制，不需要拼接sql
        2、如果存在本人, 那么肯定需要拼接当前登录id作为条件, user_id = {} (注: 最好放在最前面, 因为这样不需要再去除or/and 字符)
        3、在上面的情况全部过滤后:
            3.1、如果存在本级🐔以下级, 那么这已经是最大的数据权限类型, 不管还存在什么类型，肯定都没它大，那么查出当前登录人的部门编码, 再查出该部门的所有子部门包括自己, 作为条件即可
            如果不存在本级以下级:
                3.1.1、如果存在本级但不存在下级, 直接把当前部门编码作为条件即可
                3.1.2、如果不存在本级单存在下级, 则查出该部门下的所有子部门, 但不包含自己
                3.1.3、如果🐔存在本级也存在下级, 那么就是本级🐔下级的权限
            3.2、就没有其他权限了
         */

        // 获取去除 数据权限是 DATA_SCOPE_ALL 的权限类型集合
        Set<DataScopeEnum> removeDataScopeEnums = dataScopeEnums.stream()
                .filter(dataScopeEnum -> !Objects.equals(dataScopeEnum, DataScopeEnum.DATA_SCOPE_ALL))
                .collect(Collectors.toSet());
        // 如果两个集合的大小不同，则表示存在全部，直接返回空即可
        if (dataScopeEnums.size() != removeDataScopeEnums.size()) {
            return PermissionsConstants.STR_EMPTY;
        }
        StringBuilder sb = new StringBuilder();
        if (removeDataScopeEnums.contains(DataScopeEnum.DATA_SCOPE_SELF)) {
            sb.append(String.format(" user_id = %s", userId));
        }

        if (isNeedQueryDeptAndChild(removeDataScopeEnums)) {
            if (StringUtils.isNotBlank(sb.toString())) {
                sb.append(" or");
            }
            String deptCodeBySession = SecurityUtils.getDeptCodeBySession();
            List<SysDeptRelation> sysDeptRelationList = sysDeptRelationService.list(
                    new QueryWrapper<SysDeptRelation>().lambda()
                            .eq(SysDeptRelation::getAncestorCode, deptCodeBySession)
            );
            List<String> descendantCodeList = sysDeptRelationList.stream().map(SysDeptRelation::getDescendantCode).collect(Collectors.toList());
            sb.append(String.format(" dept_code in (%s)", StringUtils.join(descendantCodeList, PermissionsConstants.COMMA_SEPARATOR)));
        } else {
            if (removeDataScopeEnums.contains(DataScopeEnum.DATA_SCOPE_DEPT) && !removeDataScopeEnums.contains(DataScopeEnum.DATA_SCOPE_CHILD)) {
                if (StringUtils.isNotBlank(sb.toString())) {
                    sb.append(" or");
                }
                String deptCodeBySession = SecurityUtils.getDeptCodeBySession();
                sb.append(String.format(" dept_code = '%s'", deptCodeBySession));
            } else if (!removeDataScopeEnums.contains(DataScopeEnum.DATA_SCOPE_DEPT) && removeDataScopeEnums.contains(DataScopeEnum.DATA_SCOPE_CHILD)) {
                if (StringUtils.isNotBlank(sb.toString())) {
                    sb.append(" or");
                }
                String deptCodeBySession = SecurityUtils.getDeptCodeBySession();
                List<SysDeptRelation> sysDeptRelationList = sysDeptRelationService.list(
                        new QueryWrapper<SysDeptRelation>().lambda()
                                .eq(SysDeptRelation::getAncestorCode, deptCodeBySession)
                );
                List<String> descendantCodeList = sysDeptRelationList.stream().map(SysDeptRelation::getDescendantCode).collect(Collectors.toList());
                descendantCodeList.remove(deptCodeBySession);
                sb.append(String.format(" dept_code in (%s)", StringUtils.join(descendantCodeList, PermissionsConstants.COMMA_SEPARATOR)));
            }
        }
        return sb.toString();
    }

    /**
     * 是否需要查询本级及下级
     *
     * @param removeDataScopeEnums
     * @return
     */
    private boolean isNeedQueryDeptAndChild(Set<DataScopeEnum> removeDataScopeEnums) {
        return removeDataScopeEnums.contains(DataScopeEnum.DATA_SCOPE_DEPT_AND_CHILD) || (removeDataScopeEnums.contains(DataScopeEnum.DATA_SCOPE_DEPT) && removeDataScopeEnums.contains(DataScopeEnum.DATA_SCOPE_CHILD));
    }

    private Set<DataScopeEnum> convert(List<SysRole> sysRoleList) {
        return sysRoleList.stream()
                .map(sysRole -> DataScopeEnum.getByType(sysRole.getDataScope()))
                .collect(Collectors.toSet());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        DataScopeStrategyFactory.register(this, DataScopeEnum.DEFAULT_SCOPE);
    }
}
