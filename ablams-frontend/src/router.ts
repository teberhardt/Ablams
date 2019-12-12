import Vue from 'vue';
import Router from 'vue-router';


Vue.use(Router);

export default new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: [
        {
            path: '/',
            redirect: '/home',
        },
        {
            path: '/home',
            name: 'home',
            component: () => import('./views/HomeView.vue'),
        },
        {
            path: '/audiobooks',
            name: 'audiobooks',
            component: () => import('./views/AudiobooksView.vue'),
        },
        {
            path: '/authors',
            name: 'authors',
            component: () => import('./views/AuthorsView.vue'),
        },
        {
            path: '/admin-settings',
            name: 'adminsettings',
            component: () => import('./views/AdminSettingsView.vue'),
        },
        {
            path: '/about',
            name: 'about',
            // route level code-splitting
            // this generates a separate chunk (about.[hash].js) for this route
            // which is lazy-loaded when the route is visited.
            component: () => import(/* webpackChunkName: "about" */ './views/AboutView.vue'),
        },

    ],
});
