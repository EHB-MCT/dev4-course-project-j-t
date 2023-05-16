import { createRouter, createWebHistory } from 'vue-router'
import Login from '../components/Login.vue'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'Login',
            component: Login
        },
        {
            path: '/item',
            name: 'item',
            component: () => import('../components/Item.vue')
        },
        {
            path: '/register',
            name: 'register',
            component: () => import('../components/Register.vue')
        },
        {
            path: '/loans',
            name: 'loans',
            component: () => import('../components/GetAllLoans.vue')
        },
        {
            path: '/createItem',
            name: 'createItem',
            component: () => import('../components/CreateItem.vue')
        },
        {
            path: '/createLoan',
            name: 'createLoan',
            component: () => import('../components/CreateLoan.vue')
        },
        {
            path: '/getAllUsers',
            name: 'getAllUsers',
            component: () => import('../components/GetAllUsers.vue')
        },
        {
            path: '/itemDetails/:id',
            name: 'itemDetails',
            component: () => import('../components/ItemDetails.vue')
        },
        {
            path: '/history',
            name: 'history',
            component: () => import('../components/History.vue')
        },
        {
            path: '/myloans',
            name: 'myloans',
            component: () => import('../components/MyLoans.vue')
        },
    ]
})

export default router
