<template>
    <div class="layout">
        <Row type="flex">
            <i-col span="4" class="layout-menu-left">
                <Menu theme="dark" :active-name="activeMenu" width="auto" :open-names="openMenuNames" @on-select="menuSelected" ref="menu">
                    <div class="layout-logo-left">
                        <!--  -->
                        <img src="static/img/logo.gif" class="logo">
                        <span>超级监控系统</span>
                    </div>
                    <Submenu :name="menu.menuName" v-for="menu in menus" :key="menu.menuName">
                        <template slot="title">
                            <Icon type="ios-navigate"></Icon>
                            {{ menu.menuName }}
                        </template>
                        <Menu-item :name="subMenu.path" v-for="subMenu in menu.subMenus" :key="subMenu.name">
                            <Badge :count="warns[subMenu.name]">
                                <span class="badge">{{ subMenu.display }}</span>
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
                    xxx
                </div>
            </i-col>
        </Row>
    </div>
</template>
<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    import { SET_TITLE } from 'store/mutation-types'
    import { intervalTime } from 'constants/constants'

    export default {
        computed: {
            ...mapGetters([
                'title',
                'warns',
                'menus',
                'menuDesc'
            ])
        },
        watch: {
            menus: function(newMenu) {
                if (newMenu.length) {
                    // 展开所有的菜单项
                    newMenu.forEach((menu) => {
                        this.openMenuNames.push(menu.menuName)
                    })

                    // 更新菜单显示状态
                    this.$nextTick(() => {
                        let menu = this.$refs.menu
                        menu.updateActiveName()
                        menu.updateOpened()
                    })
                }
            },
            menuDesc: function(newMenuDesc) {
                if (newMenuDesc) {
                    this._setCurrentTitle()
                }
            }
        },
        created() {
            this.openMenuNames = []
            this.getMenu()
            this._setCurrentRoute()
        },
        mounted() {
            // 轮询获取基础数据
            this.setData()
            this.interval = window.setInterval(() => {
                // 获取数据的逻辑
                this.setData()
            }, intervalTime)
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
                'setTitle': SET_TITLE
            }),

            ...mapActions([
                'setData',
                'getMenu'
            ]),

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

            _setCurrentTitle() {
                let currentRoute = this.$router.currentRoute.path
                if (currentRoute === '/') {
                    currentRoute = '/bases'
                }
                this.setTitle(this.menuDesc[currentRoute])
            }
        }
    }
</script>
<style lang="scss" type="stylesheet/scss">
    .logo {
        height: 30px;
        width: 30px;
        border-radius: 50%;
        position: absolute;
        left: 12px;
    }
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
        font-size: 18px;
        font-weight: 600;
        color: #fff;
        background: #495060;
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