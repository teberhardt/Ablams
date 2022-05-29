import Vue from 'vue';
import Router from 'vue-router';
import AuthService from '@/auth/auth.service';

Vue.use(Router);

const AblamsRouter = new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: [
        {
            path: '/',
            redirect: '/home',
        },
        {
            path: '/login',
            name: 'login',
            component: () => import('./views/Login.vue'),
            meta: {
                requiresAuth: false,
            },
        },
        {
            path: '/home',
            name: 'home',
            component: () => import('./views/HomeView.vue'),
            meta: {
                requiresAuth: true,
            },
        },
        {
            path: '/audiobooks',
            name: 'audiobooks',
            component: () => import('./views/AudiobooksView.vue'),
            meta: {
                requiresAuth: true,
            },
        },
        {
            path: '/authors',
            name: 'authors',
            component: () => import('./views/AuthorsView.vue'),
            meta: {
                requiresAuth: true,
            },
        },
        {
            path: '/admin-settings',
            name: 'adminsettings',
            component: () => import('./views/AdminSettingsView.vue'),
            meta: {
                requiresAuth: true,
            },
        },
        {
            path: '/about',
            name: 'about',
            // route level code-splitting
            // this generates a separate chunk (about.[hash].js) for this route
            // which is lazy-loaded when the route is visited.
            component: () => import(/* webpackChunkName: "about" */ './views/AboutView.vue'),
            meta: {
                requiresAuth: true,
            },
        },

    ],
});

AblamsRouter.beforeEach((to, from, next) => {
    const requiresAuth = to.matched.some((x) => x.meta.requiresAuth);

    if (!requiresAuth) {
        next();
    } else {
        if (AuthService.isLoggedIn()) {
            next();
        } else {
            next('/login');
        }
    }
});

export default AblamsRouter;
