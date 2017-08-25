<template>
    <div class="layout">
        <Row type="flex">
            <i-col span="4" class="layout-menu-left">
                <Menu theme="dark" :active-name="activeMenu" width="auto" :open-names="['1']" @on-select="menuSelected">
                    <div class="layout-logo-left">
                        超级监控系统
                    </div>
                    <Submenu name="1" >
                        <template slot="title">
                            <Icon type="ios-navigate"></Icon>
                            MySQL类别
                        </template>
                        
                            <Menu-item :name="menu.path" v-for="menu in menus" :key="menu.name">
                                <Badge :count="menu.badge">
                                    <span class="badge">{{ menu.name }}</span>
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
    </div>
</template>
<script>
    import { mapGetters } from 'vuex'

    export default {
        methods: {
            menuSelected(menuRoute) {
                this.$router.push({
                    path: menuRoute
                })
            }
        },
        computed: {
            menus() {
                return [
                    {
                        name: '基本状态',
                        path: '/bases',
                        desc: '显示数据库的基本状态',
                        badge: this.warns['base']
                    },
                    {
                        name: '缓存状态',
                        path: '/caches',
                        desc: '显示数据库的缓存信息',
                        badge: this.warns['cache']
                    },
                    {
                        name: '复制状态',
                        path: '/replications',
                        desc: '显示数据库的主从复制状态',
                        badge: this.warns['replication']
                    }
                ]
            },
            ...mapGetters([
                'title',
                'warns'
            ])
        },
        created() {
            let currentRoute = this.$router.currentRoute.path
            if (currentRoute === '/') {
                this.$router.push({
                    path: '/bases'
                })
                this.activeMenu = '/bases'
            } else {
                this.activeMenu = currentRoute
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