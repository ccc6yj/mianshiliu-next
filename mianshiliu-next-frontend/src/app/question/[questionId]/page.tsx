"use server";
import "./index.css";
import React from "react";
import { getQuestionVoByIdUsingGet } from "@/api/questionController";
import QuestionCard from "@/components/QuestionCard";

/**
 * 题库题目详情页面
 * @constructor
 */
export default async function QuestionPage({ params }) {
  const {questionId } = params;

  let question: any = undefined;

  try {
    const res = await getQuestionVoByIdUsingGet({
      id: questionId,
    });
    question = res.data;
  } catch (e) {
    // @ts-ignore
    console.error("获取题目详情失败，" + e.message);
  }

  if (!question) {
    return <div>获取题目详情失败，请刷新重试</div>;
  }

  return (
    <div id="questionPage">
      <QuestionCard question={question} />

    </div>
  );
}
