<template>
    <div class="layout">
        <Row type="flex">
            <i-col span="4" class="layout-menu-left">
                <Menu theme="dark" :active-name="activeMenu" width="auto" :open-names="openMenuNames" @on-select="menuSelected" ref="menu">
                    <div class="layout-logo-left">
                        超级监控系统
                    </div>
                    <Submenu :name="menu.menuName" v-for="menu in menus" :key="menu.menuName">
                        <template slot="title">
                            <Icon type="ios-navigate"></Icon>
                            {{ menu.menuName }}
                        </template>
                        <Menu-item :name="subMenu.path" v-for="subMenu in menu.subMenus" :key="subMenu.name">
                            <Badge :count="warns[subMenu.name]">
                                <span class="badge">{{ subMenu.dispaly }}</span>
                            </Badge>
                        </Menu-item>
                    </Submenu>
                </Menu>
            </i-col>
            <i-col span="20">
                <div class="layout-header">
                    <h1>{{ title }}</h1>
                </div>
                <div class="layout-content">
                    <div class="layout-content-main">
                        <keep-alive>
                            <router-view></router-view>
                        </keep-alive>
                    </div>
                </div>
                <div class="layout-copy">
                    xxxx
                </div>
            </i-col>
        </Row>
        <!-- <Spin size="large" fix v-if="menus.length === 0"></Spin> -->
    </div>
</template>
<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    import { getMenus } from 'api/menu'
    import { getBases } from 'api/base'
    import { getCaches } from 'api/cache'
    import { getReplications } from 'api/replication'
    import * as types from 'store/mutation-types'
    import { CODE_OK, intervalTime } from 'constants/constants'
    import { expand } from 'common/js/expand'
    import CacheExpand from '@/components/cache/cache-expand-row'
    import ReplicationExpand from '@/components/replication/replication-expand-row'
    import { resolveListTo2 } from 'common/js/utils'

    export default {
        data() {
            return {
                menus: [],
                openMenuNames: []
            }
        },
        computed: {
            ...mapGetters([
                'title',
                'warns',
                'expandCaches',
                'expandReplications'
            ])
        },
        created() {
            this._getMenus()
            this._setCurrentRoute()
        },
        mounted() {
            // 轮询获取基础数据
            this._getData()
            this.interval = window.setInterval(() => {
                // 获取数据的逻辑
                this._getData()
            }, intervalTime)

            // 原先使用的是$nextTick方法,但是,不知道为啥,那时还没渲染完页面
            window.setTimeout(() => {
                let menu = this.$refs.menu
                menu.updateActiveName()
                menu.updateOpened()
            }, 20)
        },
        deactivated() {
            if (this.interval) {
                window.clearInterval(this.interval)
            }
        },
        methods: {
            menuSelected(menuRoute) {
                this.$router.push({
                    path: menuRoute
                })
            },

            ...mapMutations({
                'setBaseData': types.SET_BASE_DATA,
                'setCacheData': types.SET_CACHE_DATA
            }),

            ...mapActions([
                'setReplicationData'
            ]),

            _getMenus() {
                getMenus().then((res) => {
                    if (res.code === CODE_OK) {
                        let menuData = res.data
                        if (menuData.length > 0) {
                            menuData.sort((a, b) => a.orderNum - b.orderNum)
                            menuData.forEach((menu) => {
                                this.openMenuNames.push(menu.menuName)
                                menu.subMenus.sort((a, b) => a.orderNum - b.orderNum)
                            })
                            this.menus = menuData
                        }
                    } else {
                        this.$Notice.error({
                            title: '获取菜单错误',
                            desc: '请检查服务器连接是否正确'
                        })
                    }
                })
            },

            _setCurrentRoute() {
                let currentRoute = this.$router.currentRoute.path
                if (currentRoute === '/') {
                    this.$router.push({
                        path: '/bases'
                    })
                    this.activeMenu = '/bases'
                } else {
                    this.activeMenu = currentRoute
                }
            },

            _getData() {
                this._getBaseData()
                this._getCacheData()
                this._getReplicationData()
            },

            _getBaseData() {
                getBases().then((res) => {
                    if (res.code === CODE_OK) {
                        this.setBaseData(res.data)
                    }
                })
            },

            _getCacheData() {
                getCaches().then((res) => {
                    if (res.code === CODE_OK) {
                        // 加入展开的选项设置
                        res.data.forEach(cache => {
                            cache.columns.unshift(expand(CacheExpand))

                            // 为保存展开状态做的变量保存,在传递事件时会带着,用于定位具体的某个数据项
                            cache.data.forEach((d) => {
                                d.titleVarible = `${cache.title}_${d.variable}`
                                if (this.expandCaches[d.titleVarible]) {
                                    d._expanded = true
                                }
                            })
                        })
                        this.setCacheData(resolveListTo2(res.data))
                    }
                })
            },

            _getReplicationData() {
                getReplications().then((res) => {
                    if (res.code === CODE_OK) {
                        // 加入展开的选项设置
                        let data = res.data
                        data.columns.unshift(expand(ReplicationExpand))

                        // expand的状态设置
                        data.data.forEach((d) => {
                            if (this.expandReplications[d.host]) {
                                d._expanded = true
                            }
                        })
                        this.setReplicationData(data)
                    }
                })
            }
        }
    }
</script>
<style lang="scss" type="stylesheet/scss">
    .layout {
        border: 1px solid #d7dde4;
        background: #f5f7f9;
        position: relative;
    }
    .layout-header {
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .layout-content {
        min-height: 200px;
        margin: 15px;
        overflow: hidden;
        background: #fff;
        border-radius: 4px;
    }
    .layout-content-main {
        padding: 10px;
    }
    .layout-copy {
        text-align: center;
        padding: 10px 0 20px;
        color: #9ea7b4;
    }
    .layout-menu-left {
        background: #464c5b;
    }
    .layout-header {
        height: 60px;
        background: #fff;
        box-shadow: 0 1px 1px rgba(0,0,0,.1);
    }
    .layout-logo-left {
        width: 90%;
        height: 30px;
        color: #fff;
        background: #5b6270;
        border-radius: 3px;
        margin: 15px auto;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .ivu-table .table-info-row td {
        background-color: #2db7f5;
        color: #fff;
        font-size: 16px;
    }

    .badge{
        border-radius: 6px;
        display: inline-block;
        width: 90px;
    }
</style>