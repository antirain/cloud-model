import {createApp} from 'vue'
import './style.css'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css' // 🔥 必须引入！
import { createPinia } from 'pinia'

import VForm3 from 'vform3-builds'  //引入VForm 3库
import 'vform3-builds/dist/designer.style.css'  //引入VForm3样式

const app = createApp(App);
const pinia = createPinia()
app.use(pinia)
app.use(router)
app.use(ElementPlus)
app.use(VForm3)  //全局注册VForm 3(同时注册了v-form-designer和v-form-render组件)

app.mount('#app')
