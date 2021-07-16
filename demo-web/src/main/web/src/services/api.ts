// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

export async function requestGet(url: string, params?: any, options?: any) {
  return request(url, {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

export async function requestPost(url: string, values: any, options?: any) {
  return request(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: values,
    ...(options || {}),
  });
}