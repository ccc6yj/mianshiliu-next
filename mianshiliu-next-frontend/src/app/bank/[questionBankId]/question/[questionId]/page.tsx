"use server";
import Title from "antd/es/typography/Title";
import { getQuestionBankVoByIdUsingGet } from "@/api/questionBankController";
import "./index.css";
import React from "react";
import { getQuestionVoByIdUsingGet } from "@/api/questionController";
import { Content } from "antd/es/layout/layout";
import Sider from "antd/es/layout/Sider";
import { Flex, Menu } from "antd";
import QuestionCard from "@/components/QuestionCard";
import Link from "next/link";

/**
 * 题库题目详情页面
 * @constructor
 */
export default async function BankQuestionPage({ params }) {
  const { questionBankId, questionId } = params;
  //获取题库详情
  let bank: any = undefined;
  try {
    const res = await getQuestionBankVoByIdUsingGet({
      id: questionBankId,
      needQueryQuestionList: true,
      pageSize: 200,
    });
    bank = res.data;
  } catch (e) {
    // @ts-ignore
    console.error("获取题库详情失败，" + e.message);
  }

  if (!bank) {
    return <div>获取题库详情失败，请刷新重试</div>;
  }

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
  // 题目菜单列表
  const questionMenuItemList = (bank.questionPage?.records || []).map(
    (question) => {
      return {
        label: (
          <Link
            href={`/bank/${questionBankId}/question/${question.id}`}
            prefetch={false}
          >
            {question.title}
          </Link>
        ),
        key: question.id,
      };
    },
  );

  return (
    <div id="bankQuestionPage">
      <Flex gap={24}>
        <Sider width={240} theme="light" style={{ padding: "24px 0" }}>
          <Title level={4} style={{ padding: "0 20px" }}>
            题库标题
          </Title>
          <Menu items={questionMenuItemList} selectedKeys={[questionId]} />
        </Sider>
        <Content>
          <QuestionCard question={question} />
        </Content>
      </Flex>
    </div>
  );
}
