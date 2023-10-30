/**
 * 程序名：前端路由配置
 * 功能：配置前端路由
 */
import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/views/user-sign/Login'
import Regist from '@/views/user-sign/Regist'
import RetrievePassword from '@/components/RetrievePassword'
import Base from '@/components/Base'
import Index from '@/components/map/index'
import Home from '@/components/Home'
import MenuBar from '@/components/MenuBar'
import UserInfos from '@/views/tenant/UserInfos'
import Display from '@/components/Display'
import TempDisplay from '@/components/TempDisplay'
import ThankYou from '@/components/ThankYou'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Index',
      meta: { hidden: true },
      component: Login
    },
    {
      path: '/login',
      name: 'Login',
      meta: { hidden: true },
      component: Login
    },
    {
      path: '/adv',
      name: 'Adv',
      meta: { hidden: true },
      component: () => import('../views/adtv/reward.vue')
    },
    {
      path: '/regist',
      name: 'Regist',
      meta: { hidden: true },
      component: Regist
    },
    {
      path: '/retrievePassword',
      name: 'RetrievePassword',
      meta: { hidden: true },
      component: RetrievePassword
    },
    {
      path: '/display/:questionnaireId',
      name: 'Display',
      meta: { hidden: true },
      component: Display
    },
    {
      path: '/tempdisplay/:questionnaireId',
      name: 'TempDisplay',
      meta: { hidden: true },
      component: TempDisplay
    },
    {
      path: '/thankyou',
      name: 'ThankYou',
      meta: { hidden: true },
      component: ThankYou
    },
    {
      path: '/manage',
      name: 'manage',
      meta: { hidden: true },
      component: () => import("../views/admin/login.vue")
    },
	{
      path: '/',
      name: 'Base',
      component: Base,
      meta: { hidden: false },
      children: [
        {
          path: '/user',
          name: 'User',
          component: { render: (e) => e("router-view") },
          meta: {
            requireAuth: true,
          },
          children: [
            {
              path: '/user/index',
              name: 'QnCenter',
              component: () => import('../views/qn-manage/QnCenter.vue'),
              meta: {
                requireAuth: true,
              }
            },
            {
              path: '/user/home',
              name: 'QnHome',
              component: Home,
              meta: {
                requireAuth: true,
              }
            },
            {
              path: '/user/create_ques',
              name: 'CreateQues',
              component: () => import('../views/qn-create/CreateQn.vue'),
            },
            {
              path: '/user/edit_ques_type',
              name: 'CreateQuesType',
              component: () => import('../views/qn-create/CreateType.vue'),
            },
            {
              path: '/user/anwserers',
              name: 'Anwserers',
              component: () => import('../views/qn-manage/Questioners.vue'),
            },
            {
              path: '/user/edit_ques/:questionnaireId?',
              name: 'Edit',
              component: () => import('../views/qn-create/qn-form/form-create.vue'),
            },
            {
              path: '/user/group',
              name: 'Group',
              component: () => import('../views/qn-manage/Groups.vue'),
            },
            {
              path: '/user/datashow/:questionnaireId',
              name: 'DataShow',
              meta: { hidden: true },
              component: () => import('../views/qn-stat/QnStat.vue')
            },
            {
              path: '/user/qn-fill',
              name: 'qnfill',
              meta: { hidden: true },
              component: () => import('../views/qn-fill/FillQn.vue')
            },
          ]
        },
        {
          path: '/tenant',
          name: 'tenant',
          meta: { title: '租户管理界面', roleId: 'tenet' },
          component: MenuBar,
          children: [
            {
              path: '/tenant/userList',
              name: 'QuestionerList',
              iconCls: 'el-icon-user',
              component: UserInfos,
              meta: { title: '用户列表' },
            },
            {
              path: '/tenant/fee',
              name: 'Fee',
              iconCls: 'el-icon-user',
              component: () => import("../views/tenant/feeManage.vue"),
              meta: { title: '使用情况' },
            }
          ]
        },
        {
          path: '/admin',
          name: 'admin',
          meta: { title: '管理员管理界面', roleId: 'admin' },
          component: MenuBar,
          children: [
            {
              path: '/admin/tenants',
              name: 'tenants',
              iconCls: 'el-icon-user',
              component: () => import("../views/admin/tenants.vue"),
              meta: { title: '租户列表' },
            }, {
              path: '/admin/tenantsUsage',
              name: 'tenantsUsage',
              iconCls: 'el-icon-user',
              component: () => import("../views/admin/tenantUsage.vue"),
              meta: { title: '租户统计' },
            },
          ]
        },
      ],
    },
    {
      path: '/bigData',
      name: 'BigData',
      meta: { hidden: true },
      component: Index
    },
  ],
})
