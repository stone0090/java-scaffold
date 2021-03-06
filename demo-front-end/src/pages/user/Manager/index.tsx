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
  const [form] = Form.useForm();
  const [currentRow, setCurrentRow] = useState<any>();
  const [addStatus, setAddStatus] = useState<boolean>(true);
  const [formVisible, setFormVisible] = useState<boolean>(false);
  const [detailVisible, setDetailVisible] = useState<boolean>(false);
  type handleGetCallback = (record: any) => void;

  const handleQuery = async (params: any) => {
    const result: Protocol.RestResult = await requestGet<Protocol.RestResult>('/demo/user/list', params);
    // 将用户的角色列表打平到roleName字段，给用户列表展示
    if (result && result.data && result.data.list) {
      const users = result.data.list;
      for (var i = 0; i < users.length; i++) {
        const user = users[i];
        if (user.roles) {
          var roleName = "";
          for (var j = 0; j < user.roles.length; j++) {
            const role = user.roles[j];
            roleName += (j == 0) ? role.roleName : ("、" + role.roleName);
          }
          user["roleName"] = roleName;
        }
      }
    }
    return {
      data: result?.data?.list,
      total: result?.data?.total,
      success: result?.success
    }
  };

  const handleGet = async (record: any, callback: handleGetCallback) => {
    const result: Protocol.RestResult = await requestGet<Protocol.RestResult>('/demo/user/get', { "id": record.id });
    if (result?.success) {
      callback(result.data);
    }
  };

  const handleSave = async (record: any) => {
    const result: Protocol.RestResult = await requestPost<Protocol.RestResult>(addStatus ? '/demo/user/add' : '/demo/user/edit', record);
    if (result?.success) {
      setFormVisible(false);
      message.success((addStatus ? '新建' : '修改') + '成功！');
      if (actionRef.current) {
        actionRef.current.reload();
      }
    }
  };

  const handleRemove = async (record: any) => {
    const result: Protocol.RestResult = await requestPost<Protocol.RestResult>('/demo/user/remove', { "id": record.id });
    if (result?.success) {
      setFormVisible(false);
      message.success('删除成功！');
      if (actionRef.current) {
        actionRef.current.reload();
      }
    }
  };

  const briefColumns: ProColumns[] = [
    {
      title: '账号',
      dataIndex: 'username',
      valueType: 'textarea',
      render: (dom, record) => {
        return (
          <a
            onClick={() => {
              setCurrentRow(undefined);
              handleGet(record, setCurrentRow);
              setDetailVisible(true);
            }}
          >
            {dom}
          </a>
        );
      },
    },
    {
      title: '密码',
      dataIndex: 'password',
      valueType: 'textarea',
      search: false,
    },
    {
      title: '昵称',
      dataIndex: 'nickname',
      valueType: 'textarea',
    },
    {
      title: '角色',
      dataIndex: 'roleName',
      valueType: 'textarea',
    },
    {
      title: '更新时间',
      dataIndex: 'gmtModified',
      valueType: 'dateTime',
      search: false,
    },
    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      render: (dom, record) => [
        <a
          key="edit"
          onClick={() => {
            form.resetFields();
            setAddStatus(false);
            setDetailVisible(false);
            handleGet(record, form.setFieldsValue);
            setFormVisible(true);
          }}
        >
          修改
        </a>,
        <a
          key="remove"
          onClick={() => {
            handleRemove(record)
          }}
        >
          删除
        </a>,
      ],
    },
  ];

  const detailColumns: ProColumns[] = [
    {
      title: '账号',
      dataIndex: 'username',
      valueType: 'textarea',
    },
    {
      title: '密码',
      dataIndex: 'password',
      valueType: 'textarea',
    },
    {
      title: '昵称',
      dataIndex: 'nickname',
      valueType: 'textarea',
    },
    {
      title: '简介',
      dataIndex: 'resume',
      valueType: 'textarea',
    },
    {
      title: '电话',
      dataIndex: 'phone',
      valueType: 'textarea',
    },
    {
      title: '邮箱',
      dataIndex: 'email',
      valueType: 'textarea',
    },
    {
      title: '更新时间',
      dataIndex: 'gmtModified',
      valueType: 'dateTime',
    },
    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      render: (dom, record) => [
        <a
          key="edit"
          onClick={() => {
            form.resetFields();
            setAddStatus(false);
            setDetailVisible(false);
            handleGet(record, form.setFieldsValue);
            setFormVisible(true);
          }}
        >
          修改
        </a>,
        <a
          key="remove"
          onClick={() => {
            handleRemove(record)
          }}
        >
          删除
        </a>,
      ],
    },
  ];

  return (
    <PageContainer>
      <ProTable
        headerTitle='用户列表'
        actionRef={actionRef}
        bordered={true}
        rowKey="id"
        search={{
          labelWidth: 60,
        }}
        toolBarRender={() => [
          <Button
            type="primary"
            key="primary"
            onClick={() => {
              form.resetFields();
              setFormVisible(true);
              setAddStatus(true);
            }}
          ><PlusOutlined />
            新建
          </Button>
        ]}
        request={handleQuery}
        columns={briefColumns}
        pagination={{
          pageSize: 8
        }}
      />
      <Drawer
        title="新建用户"
        width={720}
        onClose={() => { setFormVisible(false); }}
        visible={formVisible}
        bodyStyle={{ paddingBottom: 80 }}
        footer={
          <div
            style={{
              textAlign: 'right',
            }}
          >
            <Button
              onClick={() => { setFormVisible(false); }}
              style={{ marginRight: 8 }}
            >
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
          onFinish={handleSave}
        >
          <Row gutter={16}>
            <Col span={12}>
              <Form.Item
                name="username"
                label="账号"
                rules={[{ required: true, message: '账号不能为空' }]}
              >
                <Input disabled={!addStatus} placeholder="3~20位字符，只能包含英文字母、数字、下划线" />
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item
                name="password"
                label="密码"
                rules={[{ required: true, message: '密码不能为空' }]}
              >
                <Input placeholder="3~20位字符，只能包含英文字母、数字、下划线" />
              </Form.Item>
            </Col>
          </Row>
          <Row gutter={16}>
            <Col span={12}>
              <Form.Item
                name="nickname"
                label="昵称"
              >
                <Input placeholder="小于20位字符" />
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item
                name="resume"
                label="简介"
              >
                <Input placeholder="小于20位字符" />
              </Form.Item>
            </Col>
          </Row>
          <Row gutter={16}>
            <Col span={12}>
              <Form.Item
                name="phone"
                label="电话"
              >
                <Input placeholder="小于20位字符" />
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item
                name="email"
                label="邮箱"
              >
                <Input placeholder="小于20位字符" />
              </Form.Item>
            </Col>
          </Row>
          <Form.Item name="id"></Form.Item>
        </Form>
      </Drawer>
      <Drawer
        width={720}
        visible={detailVisible}
        onClose={() => {
          setCurrentRow(undefined);
          setDetailVisible(false);
        }}
        closable={false}
      >
        {currentRow?.username && (
          <ProDescriptions<any>
            column={2}
            title="用户详情"
            request={async () => ({
              data: currentRow || {},
            })}
            params={{
              id: currentRow?.id,
            }}
            columns={detailColumns as ProDescriptionsItemProps<any>[]}
          />
        )}
      </Drawer>
    </PageContainer>
  );
};

export default UserManager;
