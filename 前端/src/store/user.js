
import {removeToken, setToken} from '../utils/auth'
import {post} from '../utils/api'

const user = {
  state: {
    username: "",
    id: "",
    roleId: [],
  },
  mutations: {
    SET_USER: (state, userInfo) => {
      state.username = userInfo.username;
      state.id = userInfo.id;
      state.roleId = userInfo.roleId;
    },
    RESET_USER: (state) => {
      state.username = "";
      state.id = "";
      state.roleId = [];
      state.menus = [];
      state.permissions = [];
    }
  },
  actions:{
    
    // 登录
    Login({commit, state}, loginForm) {
      return new Promise((resolve, reject) => {
        post(
          "/api/admin/userLogin",
          loginForm
        ).then(data => {
          setToken(data.sessionId);
          commit('SET_USER', data);
          resolve(data);
        }).catch(err => {
          reject(err)
        })
      })
    },
    LoginValidCode({commit, state}, loginForm) {
      return new Promise((resolve, reject) => {
        post(
          "/api/validCode/check",
          loginForm
        ).then(data => {
          //localstorage中保存token
          setToken(data.sessionId);
          commit('SET_USER', data);
          resolve(data);
        }).catch(err => {
          reject(err)
        })
      })
    },
    // 获取用户信息
    GetInfo({commit, state}) {
      return new Promise((resolve, reject) => {
        post(
          '/api/admin/getUserInfo'
        ).then(data => {
          commit('SET_USER', data);
          resolve(data)
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 登出
    LogOut({commit}) {
      return new Promise((resolve) => {
          commit('RESET_USER')
          removeToken()
          resolve();
      })
    },
    // 前端登出,不用调后端清除token的接口
    FedLogOut({commit}) {
      return new Promise(resolve => {
        commit('RESET_USER')
        removeToken()
        resolve()
      })
    }
  
  }
}
export default user;