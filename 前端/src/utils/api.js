import {getToken,removeToken} from '../utils/auth'
import {Message} from "element-ui";
import store from '../store/user'


/**
 * 程序名：api接口
 * 功能：与后端通讯的api接口定义
 */
import axios from "axios";
const service = axios.create({
   //baseURL: process.env.BASE_URL, // api的base_url
  timeout: 15000                  // 请求超时时间
})
// request拦截器
service.interceptors.request.use(config => {
  let token = getToken();
  if (token) {
    config.headers.token = token;
  }
  return config
}, error => {
  // Do something with request error
  console.error(error) // for debug
  Promise.reject(error)
})
// respone拦截器
service.interceptors.response.use(
  response => {
    const res = response.data;
    if (res.code === '666') {
      return res.data;
    } else if (res.code === "333") {
      Message({
        showClose: true,
        message: res.msg,
        type: 'error',
        duration: 500,
        onClose: () => {
          store.state.username = "";
          store.state.id = "";
          store.state.roleId = [];
          store.state.menus = [];
          store.state.permissions = [];
          removeToken()
          return Promise.resolve()
        }
      });
      return Promise.reject("未登录")
    } else {
      Message({
        message: res.message,
        type: 'error',
        duration: 3 * 1000
      })
      return Promise.reject(res)
    }
  },
  error => {
    console.error('err' + error)// for debug
    Message({
      message: error.message,
      type: 'error',
      duration: 3 * 1000
    })
    return Promise.reject(error)
  }
)

export const postFile = function(url,data,file){
  return new Promise((resolve,reject) => {
    let headers = { 'Content-Type': 'multipart/form-data' };
    let formData = new FormData();
    formData.append('body', data);
    formData.append('file', file);
    data = formData;

    axios({
      method: 'post',
      url: url,
      withCredentials: true,
      headers: headers,
      data: data
    }).then(res => resolve(res)).catch(err =>reject(err))
  })
}

export const post = function(url,data,files){
  return new Promise((resolve,reject) => {
    service({
      url:url,
      method:'post',
      data:data
    }).then(res => resolve(res)).catch(err =>reject(err))

  })
}

//问卷设计者操作
export const designOpera = data => {
  return axios.post("/api/design", data).then(res => res.data);
};

export const loginPost = data => {
  return axios.post("/api/admin/userLogin", data).then(res => res.data);
};

export const queryProjectList = data => {
  return axios.post("/api/project/queryProjectList", data).then(res => res.data);
};



//问卷操作
export const addQuestionnaire = data => {
  return axios.post("/api/questionnaire/add", data).then(res => res.data);
};
export const updateQuestionnaire = data => {
  return axios.post("/api/questionnaire/update", data).then(res => res.data);
};
export const queryQuestions = data => {
  return axios.post("/api/questionnaire/queryQuestions", data).then(res => res.data);
};
export const publishQuestionnaire = data => {
  return axios.post("/api/questionnaire/publish", data).then(res => res.data);
};
export const analysis = data => {
  return axios.post("/api/questionnaire/analysis", data).then(res => res.data);
};
export const answerView = data => {
  return axios.post("/api/questionnaire/answerView", data).then(res => res.data);
};


//题目操作
export const sageQuestions = data => {
  return axios.post("/api/question/save", data).then(res => res.data);
};


//问卷回答者操作
export const answerOpera = data => {
  return axios.post("/api/questionnaire/answerView", data).then(res => res.data);
};
export const answerQuestionnaire = data => {
  return post("/api/questionnaire/anwser", data);
};


// 用户操作
export const queryUserList = data => {
  return axios.post("/api/admin/queryUserList", data).then(res => res.data);
};
export const modifyUserStatus = data => {
  return axios.post("/api/admin/modifyUserStatus", data).then(res => res.data);
};
export const deleteUser = data => {
  return axios.post("/api/admin/deleteUserInfoById", data).then(res => res.data);
};
export const resetPwd = data => {
  return post("/api/admin/resetPwd", data);
};
export const modifyUserInfo = data => {
  return post("/api/admin/modifyUserInfo", data);
};
export const addUserInfo = data => {
  return post("/api/admin/addUserInfo", data);
};
