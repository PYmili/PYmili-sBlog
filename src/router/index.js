// vue router
import BlogView from '@/views/BlogView.vue';
import ConsoleView from '@/views/ConsoleView.vue';
import FwView from '@/views/FwView.vue';
import GalleryView from '@/views/GalleryView.vue';
import HomeView from '@/views/HomeView.vue';
import LoginView from '@/views/LoginView.vue';
import MyFlowingFireflyWifeView from '@/views/MyFlowingFireflyWifeView.vue';
import { createRouter, createWebHistory, createWebHashHistory } from 'vue-router'

const routes = [
    {
        path: '/',
        name: 'home',
        component: HomeView
    },
    {
        path: '/login',
        name: 'login',
        component: LoginView
    },
    {
        path: '/console',
        name: 'Console',
        component: ConsoleView
    },
    {
        path: '/blog',
        name: 'Blog',
        component: BlogView
    },
    {
        path: '/MyFlowingFireflyWife',
        name: 'MyFlowingFireflyWife',
        component: MyFlowingFireflyWifeView
    },
    {
        path: "/gallery",
        name: "Gallery",
        component: GalleryView
    },
    {
        path: "/fw",
        name: "FW",
        component: FwView
    }
];
const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes,
});
 
export default router;