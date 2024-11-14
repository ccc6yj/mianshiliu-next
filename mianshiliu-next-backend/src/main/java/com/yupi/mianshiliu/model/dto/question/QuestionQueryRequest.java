package com.yupi.mianshiliu.model.dto.question;

import com.yupi.mianshiliu.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 查询题目请求
 *
 *
 * @from <a href="https://www.code-nav.cn">编程导航学习圈
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionQueryRequest extends PageRequest implements Serializable {

    /**
     * id
     */
    private Long id;
    /**
     * 题库 id
     */
    private Long questionBankId;

    /**
     * id
     */
    private Long notId;

    /**
     * 搜索词
     */
    private String searchText;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表
     */
    private List<String> tags;

    /**
     * 创建用户 id
     */
    private Long userId;
    /**
     * 推荐答案
     */
    private String answer;

    private static final long serialVersionUID = 1L;
}