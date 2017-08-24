import Vue from 'vue'
import Router from 'vue-router'
import iView from 'iview'
import Layout from '@/components/layout'
import Bases from '@/components/base/bases'
import Replications from '@/components/replication/replications'
import Caches from '@/components/cache/caches'

Vue.use(Router)
Vue.use(iView)

export default new Router({
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
            },
            {
                path: '/caches',
                name: 'caches',
                component: Caches
            }
        ]
    }]
})