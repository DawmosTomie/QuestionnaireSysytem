/**
 * 程序名：Vue全局配置
 * 功能：Vue全局配置
 */
import Vue from "vue";
import App from "./App";
import router from "./router";
import ElementUI from "element-ui";
import { getToken, removeToken } from './utils/auth'
import store from './store/user'
import Vuex from 'vuex';
import "element-ui/lib/theme-chalk/index.css";
import VueClipboard from "vue-clipboard2";
import { post } from './utils/api'
import vuetify from './plugins/vuetify';
// 适配flex
// import '@/utils/flexible.js';
import dataV from '@jiaminghi/data-view';
Vue.use(dataV);


import Icon from 'vue-awesome/components/Icon';
import 'vue-awesome/icons/chart-bar.js';
import 'vue-awesome/icons/chart-area.js';
import 'vue-awesome/icons/chart-pie.js';
import 'vue-awesome/icons/chart-line.js';
import 'vue-awesome/icons/align-left.js';
import './assets/scss/style.scss';
Vue.component('icon', Icon);


import global from './utils/global' // 全局方法



Vue.use(global)

Vue.use(VueClipboard);
Vue.use(ElementUI);
Vue.use(Vuex)
Vue.prototype.$post = post
//引入echart
import * as  Echarts from 'echarts';
Vue.prototype.$echarts = Echarts;
import './assets/china.js'
import './assets/echarts-wordcloud.min.js'
Vue.config.productionTip = false;

const updateSizes = (obj = {}) => {
	obj.width = window.innerWidth
	obj.height = window.innerHeight
	return obj
}

Object.defineProperty(Vue.prototype, '$viewport', {
	value: Vue.observable(updateSizes())
})

window.addEventListener('resize', () => {
	updateSizes(Vue.prototype.$viewport)
})

const Store = new Vuex.Store({
  modules: {
    store
  }
})

router.beforeEach((to, from, next) => {
  if (getToken()) {
    //如果已经登录
    if (!store.state.id) {
      //已经登录但是store内没有userId,说明可能是重新刷新的页面,因此重新获取用户信息
      Store.dispatch('GetInfo').then(() => {
        next({ ...to })
      }).catch(err => {
        removeToken()
        next({name:'Login'})
      })
      next()
    } else {
      next()
    }
  } else if ('/login'.indexOf(to.path) !== -1 || '/retrievePassword'.indexOf(to.path) !== -1
  || '/regist'.indexOf(to.path) !== -1 ||  '/manage'.indexOf(to.path) !== -1) {
    //如果前往的路径是白名单内的,就可以直接前往
    next()
  } else {
    //如果路径不是白名单内的,而且又没有登录,就跳转登录页面
    // store.commit('RESET_USER')
    next({name:'Login'})
  }
})

/* eslint-disable no-new */
new Vue({
  el: "#app",
  vuetify,
  router,
  components: { App },
  template: "<App/>",
  store: Store
});
