package com.ah.cloud.permissions.biz.domain.notice.content.feishu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: permissions-center
 * @description: 富文本
 * @author: YuKai Fan
 * @create: 2022-06-02 16:30
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostContent implements FeishuContent {

    /**
     * 实际内容
     */
    private ActualContent zh_cn;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ActualContent {
        /**
         * 标题
         */
        private String title;

        /**
         * 富文本内容
         */
        private List<List<InnerContent>> content;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InnerTextContent implements InnerContent {
        /**
         * 标签
         */
        private String tag;

        /**
         * 超链接
         */
        private String href;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InnerImageContent implements InnerContent {
        /**
         * 标签
         */
        private String tag;
        /**
         * 图片链接
         */
        private String image_key;

        /**
         * 宽度
         */
        private String width;

        /**
         * 高度
         */
        private String height;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InnerHrefContent implements InnerContent {
        /**
         * 标签
         */
        private String tag;

        /**
         * 超链接
         */
        private String href;

        /**
         * 文本内容
         */
        private String text;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InnerAtContent implements InnerContent {
        /**
         * 标签
         */
        private String tag;

        /**
         * 用户id
         */
        private String user_id;

        /**
         * 用户名称
         */
        private String user_name;
    }

    public interface InnerContent {
    }
}
