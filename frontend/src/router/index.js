import Vue from 'vue'
import Router from 'vue-router'
import iView from 'iview'

Vue.use(Router)
Vue.use(iView)

const Login = (resolve) => {
    import('@/components/login').then((module) => {
        resolve(module)
    })
}

const Layout = (resolve) => {
    import('@/components/layout').then((module) => {
        resolve(module)
    })
}

const Bases = (resolve) => {
    import('@/components/base/bases').then((module) => {
        resolve(module)
    })
}

const Replications = (resolve) => {
    import('@/components/replication/replications').then((module) => {
        resolve(module)
    })
}

const router = new Router({
    mode: 'history',
    routes: [{
        path: '/login',
        name: 'login',
        component: Login
    }, {
        path: '/',
        name: 'layout',
        component: Layout,
        children: [
            {
                path: '/bases',
                name: 'bases',
                component: Bases
            },
            {
                path: '/replications',
                name: 'replications',
                component: Replications
            }
        ]
    }]
})
router.beforeEach((to, from, next) => {
    if (to.meta.requireAuth) {
        next('/login')
    }
    next()
})

export default router

