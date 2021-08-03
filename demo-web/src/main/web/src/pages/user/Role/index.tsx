import { PlusOutlined } from '@ant-design/icons';
import { Drawer, Form, Button, Col, Row, Input, message } from 'antd';
import React, { useState, useRef } from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import type { ProDescriptionsItemProps } from '@ant-design/pro-descriptions';
import ProDescriptions from '@ant-design/pro-descriptions';
import { requestGet, requestPost } from '@/services/api';

const RoleManager: React.FC = () => {

  const actionRef = useRef<ActionType>();
  const [form] = Form.useForm();
  const [currentRow, setCurrentRow] = useState<any>();
  const [addStatus, setAddStatus] = useState<boolean>(true);
  const [formVisible, setFormVisible] = useState<boolean>(false);
  const [detailVisible, setDetailVisible] = useState<boolean>(false);
  type handleGetCallback = (record: any) => void;

  const handleQuery = async (params: any) => {
    const result: Protocol.RestResult = await requestGet<Protocol.RestResult>('/demo/role/list', params);
    return {
      data: result?.data?.list,
      total: result?.data?.total,
      success: result?.success
    }
  };

  const handleGet = async (record: any, callback: handleGetCallback) => {
    const result: Protocol.RestResult = await requestGet<Protocol.RestResult>('/demo/role/get', { "id": record.id });
    if (result?.success) {
      callback(result.data);
    }
  };

  const handleSave = async (record: any) => {
    const result: Protocol.RestResult = await requestPost<Protocol.RestResult>(addStatus ? '/demo/role/add' : '/demo/role/edit', record);
    if (result?.success) {
      setFormVisible(false);
      message.success((addStatus ? '新建' : '修改') + '成功！');
      if (actionRef.current) {
        actionRef.current.reload();
      }
    }
  };

  const handleRemove = async (record: any) => {
    const result: Protocol.RestResult = await requestPost<Protocol.RestResult>('/demo/role/remove', { "id": record.id });
    if (result?.success) {
      setFormVisible(false);
      message.success('删除成功！');
      if (actionRef.current) {
        actionRef.current.reload();
      }
    }
  };

  const columns: ProColumns[] = [
    {
      title: '角色编码',
      dataIndex: 'roleCode',
      valueType: 'text',
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
      title: '角色名称',
      dataIndex: 'roleName',
      valueType: 'text',
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
        headerTitle='角色列表'
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
        columns={columns}
        pagination={{
          pageSize: 8
        }}
      />
      <Drawer
        title="新建角色"
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
                name="roleCode"
                label="角色编码"
                rules={[{ required: true, message: '角色编码不能为空' }]}
              >
                <Input disabled={!addStatus} placeholder="3~20位字符，只能包含英文字母、数字、下划线" />
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item
                name="roleName"
                label="角色名称"
                rules={[{ required: true, message: '角色名称不能为空' }]}
              >
                <Input placeholder="1~20位字符" />
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
        <ProDescriptions<any>
          column={2}
          title="角色详情"
          request={async () => ({
            data: currentRow || {},
          })}
          params={{
            id: currentRow?.id,
          }}
          columns={columns as ProDescriptionsItemProps<any>[]}
        />
      </Drawer>
    </PageContainer>
  );
};

export default RoleManager;
