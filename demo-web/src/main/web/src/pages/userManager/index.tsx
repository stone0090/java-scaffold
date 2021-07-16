import { PlusOutlined } from '@ant-design/icons';
import { Drawer, Form, Button, Col, Row, Input, message } from 'antd';
import React, { useState, useRef } from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import type { ProDescriptionsItemProps } from '@ant-design/pro-descriptions';
import ProDescriptions from '@ant-design/pro-descriptions';
import { requestGet, requestPost } from '@/services/api';

const UserManager: React.FC = () => {

  const actionRef = useRef<ActionType>();
  const [showDetailVisible, setShowDetailVisible] = useState<boolean>(false);
  const [editFormVisible, setEditFormVisible] = useState<boolean>(false);
  const [currentRow, setCurrentRow] = useState<API.RuleListItem>();
  const [form] = Form.useForm();

  const handleQuery = async (params: any, sort: any, filter: any) => {
    console.log(params)
    const result = await requestGet('/demo/user/list', params);
    if(result.success){
      return {
        data: result.data.list,
        total: result.data.total,
        success: result.success
      }
    }
  };

  const handleEdit = async (values: any) => {
    const hide = message.loading('正在添加...');
    try {
      console.log(values)
      const result = await requestPost('/demo/user/save', values);
      console.log(result)
      if (result.success) {
        setEditFormVisible(false);
        if (actionRef.current) {
          actionRef.current.reload();
        }
      }
      hide();
      message.success('添加成功！');
    } catch (error) {
      hide();
      message.error('添加失败请重试！');
    }
  };

  const handleRemove = async (selectedRows: API.RuleListItem[]) => {
    const hide = message.loading('正在删除');
    if (!selectedRows) return true;
    try {
      // await removeRule({
      //   key: selectedRows.map((row) => row.key),
      // });
      hide();
      message.success('删除成功，即将刷新');
      return true;
    } catch (error) {
      hide();
      message.error('删除失败，请重试');
      return false;
    }
  };

  const columns: ProColumns[] = [
    {
      title: '账号',
      dataIndex: 'username',
      render: (dom, record) => {
        return (
          <a
            onClick={() => {
              setCurrentRow(record);
              setShowDetailVisible(true);
            }}
          >
            {dom}
          </a>
        );
      },
    },
    {
      title: '昵称',
      dataIndex: 'nickname',
      valueType: 'textarea',
    },
    {
      title: '电话',
      dataIndex: 'phone',
      valueType: 'textarea',
    },
    {
      title: '邮箱',
      dataIndex: 'status',
      valueType: 'textarea',
    },
    {
      title: '上次更新时间',
      dataIndex: 'gmtModified',
      valueType: 'dateTime',
    },
    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      render: (dom, record) => [
        <a
          key="config"
          onClick={() => {
            // handleUpdateModalVisible(true);
            setCurrentRow(record);
          }}
        >修改
        </a>,
        <a key="subscribeAlert" >删除</a>,
      ],
    },
  ];

  return (
    <PageContainer>
      <ProTable
        headerTitle='查询表格'
        actionRef={actionRef}
        rowKey="key"
        search={{
          labelWidth: 120,
        }}
        toolBarRender={() => [
          <Button
            type="primary"
            key="primary"
            onClick={() => {
              // handleModalVisible(true);
              setEditFormVisible(true);
            }}
          ><PlusOutlined /> 新建
          </Button>
        ]}
        request={(params, sorter, filter) => handleQuery({ ...params, sorter, filter })}
        columns={columns}
        rowSelection={{
          onChange: (_, selectedRows) => {
            setSelectedRows(selectedRows);
          },
        }}
      />

      <Drawer
        title="新建用户"
        width={720}
        onClose={() => { setEditFormVisible(false); }}
        visible={editFormVisible}
        bodyStyle={{ paddingBottom: 80 }}
        footer={
          <div
            style={{
              textAlign: 'right',
            }}
          >
            <Button onClick={() => { setEditFormVisible(false); }} style={{ marginRight: 8 }}>
              取消
            </Button>
            <Button
              type="primary"
              onClick={() => { form.submit() }}
            >
              提交
            </Button>
          </div>
        }
      >
        <Form
          layout="vertical"
          form={form}
          onFinish={handleEdit}
        >
          <Row gutter={16}>
            <Col span={12}>
              <Form.Item
                name="username"
                label="账号"
                rules={[{ required: true, message: '账号不能空' }]}
              >
                <Input placeholder="6~20位字符，只能包含英文字母、数字、下划线" />
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item
                name="password"
                label="密码"
                rules={[{ required: true, message: '密码不能空' }]}
              >
                <Input
                  placeholder="6~20位字符，只能包含英文字母、数字、符号"
                />
              </Form.Item>
            </Col>
          </Row>
        </Form>
      </Drawer>
      <Drawer
        width={600}
        visible={showDetailVisible}
        onClose={() => {
          setCurrentRow(undefined);
          setShowDetailVisible(false);
        }}
        closable={false}
      >
        {currentRow?.name && (
          <ProDescriptions<API.RuleListItem>
            column={2}
            title={currentRow?.name}
            request={async () => ({
              data: currentRow || {},
            })}
            params={{
              id: currentRow?.name,
            }}
            columns={columns as ProDescriptionsItemProps<API.RuleListItem>[]}
          />
        )}
      </Drawer>

    </PageContainer>
  );
};

export default UserManager;