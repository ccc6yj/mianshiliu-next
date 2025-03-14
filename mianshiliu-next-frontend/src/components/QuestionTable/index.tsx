"use client";



import {useRef, useState} from "react";
import {ActionType, ProColumns, ProTable} from "@ant-design/pro-components";
import Link from "next/link";
import TagList from "@/components/TagList";
import {TablePaginationConfig} from "antd";
import {listQuestionVoByPageUsingPost, searchQuestionVoByPageUsingPost} from "@/api/questionController";

interface Props {
  //默认值用于展示服务端数据
  defaultQuestionList?: API.QuestionVO[];
  defaultTotal?: number;
  //默认搜索条件
  defaultSearchParams?: API.QuestionQueryRequest;
}

/**
 * 题目表格组件
 * @constructor
 */
export default function QuestionTable(props: Props) {
  const actionRef = useRef<ActionType>();
  const { defaultQuestionList, defaultTotal, defaultSearchParams = {} } = props;
  //题目列表
  const [questionList, setQuestionList] = useState<API.QuestionVO[]>(
    defaultQuestionList || [],
  );
  //题目总数
  const [total, setTotal] = useState<number>(defaultTotal || 0);
  //用于判断是否首次加载
  const [init, setInit] = useState<boolean>(true);
  /**
   * 表格列配置
   */
  const columns: ProColumns<API.QuestionVO>[] = [
    {
      title: "搜索",
      dataIndex: "searchText",
      valueType: "text",
      hideInTable: true,
    },
    {
      title: "标题",
      dataIndex: "title",
      valueType: "text",
      hideInSearch: true,
      render: (_, record) => {
        return <Link href={`/question/${record.id}`}>{record.title}</Link>;
      },
    },

    {
      title: "标签",
      dataIndex: "tagList",
      valueType: "select",
      fieldProps: {
        mode: "tags",
      },
      render: (_, record) => <TagList tagList={record.tagList} />,
    },
  ];

  return (
    <div className="question-table">
      <ProTable
        actionRef={actionRef}
        columns={columns}
        form={{ initialValues: defaultSearchParams }}
        size="large"
        search={{
          labelWidth: "auto",
        }}
        dataSource={questionList}
        pagination={
          {
            pageSize: 12,
            showTotal: (total) => `总共 ${total} 条`,
            showSizeChanger: false,
            total,
          } as TablePaginationConfig
        }
        //@ts-ignore
        request={async (params, sort, filter) => {
          // 首次请求
          if (init) {
            setInit(false);
            // 如果已有外层传来的默认数据，无需再次查询
            if (defaultQuestionList && defaultTotal) {
              return;
            }
          }

          const sortField = Object.keys(sort)?.[0] || "createTime";
          const sortOrder = sort?.[sortField] || "descend";
          // 请求
          // @ts-ignore
          const { data, code } = await searchQuestionVoByPageUsingPost({
            ...params,
            //es 查询条件
            // sortField:'_score',
            sortOrder,
            ...filter,
          } as API.UserQueryRequest);
          // 更新结果
          const newTotal = Number(data.total) || 0;
          const newData = data.records || [];
          //更新状态
          setQuestionList(newData);
          setTotal(newTotal);
          return {
            success: code === 0,
            data: newData,
            total: newTotal,
          };
        }}
      />
    </div>
  );
}
