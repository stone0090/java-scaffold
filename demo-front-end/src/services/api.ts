// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

export async function requestGet<T>(url: string, params?: any, options?: any) {
  return request<T>(url, {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

export async function requestPost<T>(url: string, values?: any, options?: any) {
  return request<T>(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: values,
    ...(options || {}),
  });
}