import { createApp } from 'vue'
import App from './App.vue'
import installElementPlus from './plugins/element'
import router from './router'
import store from './store'
import axios from "axios";
import {baseUrl} from './js/base'

const app = createApp(App).use(store).use(router)
installElementPlus(app)
app.mount('#app')

axios.interceptors.request.use(
    config => {
        //动态设置
        config.url = baseUrl + config.url;
        config.headers['Content-Type'] = 'application/json;charset=utf-8'
        config.headers['token'] = '1234567';
        if (config.method === 'post' || config.method === 'put') {
            //post请求时，序列化入参
            config.data = JSON.stringify(config.data);
        }
        return config;
    }, error => {
        console.error(error.response);
        Promise.reject(error.response);
    }
)

axios.defaults.withCredentials = true;
app.config.globalProperties.$axios = axios;