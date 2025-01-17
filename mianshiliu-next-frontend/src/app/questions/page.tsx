import Title from "antd/es/typography/Title";
import QuestionTable from "@/components/QuestionTable";
import {listQuestionVoByPageUsingPost, searchQuestionVoByPageUsingPost} from "@/api/questionController";
import "./index.css";
import React from "react";

/**
 * 题目列表页面
 * @constructor
 */
export default async function QuestionsPage({ searchParams }) {
  let questionList = [];
  let total = 0;
  //获取url的查询参数
  const { q: searchText } = searchParams;

  try {
    const questionRes = await searchQuestionVoByPageUsingPost({
      searchText,
      pageSize: 12,
      //es查询条件
      // sortField: "_score",
      sortOrder: "descend",
    });
    // @ts-ignore
    questionList = questionRes.data.records ?? [];
    // @ts-ignore
    total = questionRes.data.total ?? 0;
  } catch (e) {
    // @ts-ignore
    console.error("获取题目列表失败，" + e.message);
  }

  return (
    <div id="questionsPage" className="max-width-content">
      <Title level={3}>题目大全</Title>
      <QuestionTable
        defaultQuestionList={questionList}
        defaultTotal={total}
        defaultSearchParams={{ title: searchText, }}
      />
    </div>
  );
}
