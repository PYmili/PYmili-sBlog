import { createApp } from 'vue'

// public css
import "./assets/style.css"

// element-plus
import ElementPlus from "element-plus"
import 'element-plus/dist/index.css'

// vue router
import { createRouter, createWebHistory, createWebHashHistory } from 'vue-router'

const routes = [
    {
        path: '/',
        name: 'home',
        component: () => import('./Home.vue')
    },
    {
        path: '/login',
        name: 'login',
        component: () => import('./Login.vue')
    },
    {
        path: '/console',
        name: 'Console',
        component: () => import('./Console.vue')
    }
];
const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes,
});

import App from './App.vue'
import { compile } from 'vue'

const app = createApp(App);
app.use(router);
app.use(ElementPlus);
app.mount('#app');