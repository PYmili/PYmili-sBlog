import { createApp } from 'vue'

// public css
import "@/assets/style.css"
import "@/assets/theme.css"

// element-plus
import ElementPlus from "element-plus"
import 'element-plus/dist/index.css'


import App from '@/App.vue'
import { compile } from 'vue'
import router from '@/router/index.js'

const app = createApp(App);
app.use(router);
app.use(ElementPlus);
app.mount('#app');