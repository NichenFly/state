import Vue from 'vue'
import Router from 'vue-router'
import iView from 'iview'

Vue.use(Router)
Vue.use(iView)

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

export default new Router({
    mode: 'history',
    routes: [{
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