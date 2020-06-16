import Vue from 'vue';
import VueRouter from 'vue-router';

Vue.use(VueRouter);

const routes = [
    {
        path: '/',
        redirect: {
            name: 'HouseSelection'
        }
    },
    {
        path: '/house-selection',
        name: 'HouseSelection',
        component: () => import('../views/HouseSelection.vue')
    },
    {
        path: '/module-selection',
        name: 'ModuleSelection',
        component: () => import('../views/ModuleSelection.vue')
    },
    // {
    //     path: '/calculation-of-use',
    //     name: 'CalculationOfUse',
    //     component: () => import('../views/CalculationOfUse.vue')
    // },
    {
        path: '/monitoring',
        name: 'Monitoring',
        component: () => import('../views/Monitoring.vue')
    }
];

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
});

export default router;