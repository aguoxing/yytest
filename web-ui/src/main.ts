import { createApp } from 'vue'
import App from './App.vue'
import router from './router/index'
import {createPinia} from "pinia"; // 路由守卫
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

const app = createApp(App)
// 创建pinia 实例
const pinia = createPinia()

app.use(pinia)
app.use(router)
app.use(ElementPlus)

app.mount('#app')
