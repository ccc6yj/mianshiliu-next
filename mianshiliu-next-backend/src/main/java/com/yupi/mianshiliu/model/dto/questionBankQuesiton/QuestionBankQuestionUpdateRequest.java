package com.yupi.mianshiliu.model.dto.questionBankQuesiton;

import lombok.Data;

import java.io.Serializable;

/**
 * 更新题库题目关联请求
 *
 *
 * @from <a href="https://www.code-nav.cn">编程导航学习圈
 */
@Data
public class QuestionBankQuestionUpdateRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 题库 id
     */
    private Long questionBankId;

    /**
     * 题目 id
     */
    private Long questionId;

    private static final long serialVersionUID = 1L;
}