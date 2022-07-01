package com.ah.cloud.permissions.workflow.domain.candidate.dto;

import com.ah.cloud.permissions.enums.workflow.TaskAssignRuleTypeEnum;
import lombok.*;

import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-30 17:13
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TaskCandidateDTO {

    /**
     * 候选人id集合
     */
    private List<Candidate> candidateList;

    /**
     * 选中的候选人
     */
    private Candidate selectedAssignee;

    /**
     * 规则类型
     */
    private TaskAssignRuleTypeEnum taskAssignRuleTypeEnum;

    @Getter
    @Builder
    @ToString
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Candidate {
        /**
         * 候选人id
         */
        private Long userId;

        /**
         * 候选人名称
         */
        private String name;
    }
}
