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
    path: '/usermgr',
    name: '用户管理',
    icon: 'team',
    routes: [
      {
        path: '/usermgr/list',
        name: '用户列表',
        component: './user/Manager',
      },
      {
        path: '/usermgr/role',
        name: '角色列表',
        component: './user/Role',
      },
      {
        path: '/usermgr/permission',
        name: '权限列表',
        component: './user/Permission',
      },
    ],
  },
  {
    component: './404',
  },
];
