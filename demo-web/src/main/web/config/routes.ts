export default [
  {
    path: '/user',
    layout: false,
    routes: [
      {
        path: '/user',
        routes: [
          {
            name: 'login',
            path: '/user/login',
            component: './user/Login',
          },
        ],
      },
    ],
  },
  {
    path: '/',
    redirect: '/welcome',
  },
  {
    path: '/welcome',
    name: 'welcome',
    icon: 'smile',
    component: './Welcome',
  },
  {
    path: '/user-mgr',
    name: '用户管理',
    icon: 'team',
    routes: [
      {
        path: '/user-mgr/list',
        name: '用户列表',
        component: './user/Manager',
      },
      {
        path: '/user-mgr/role',
        name: '角色列表',
        component: './user/Role',
      },
      {
        path: '/user-mgr/permission',
        name: '权限列表',
        component: './user/Permission',
      },
    ],
  },
  {
    component: './404',
  },
];
