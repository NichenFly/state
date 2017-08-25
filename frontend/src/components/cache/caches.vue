<template>
    <div>
        <cache-card :cacheList="cacheData"></cache-card>
        <Spin size="large" fix v-if="cacheData.length === 0"></Spin>
    </div>
</template>
<script>
    import CacheCard from '@/components/cache/cache-card'
    import { mapMutations } from 'vuex'
    import * as types from 'store/mutation-types'
    import { getCaches } from 'api/cache'
    import { intervalTime } from 'constants/constants'

    export default {
        data() {
            return {
                cacheData: []
            }
        },
        activated() {
            this.setTitle('展示数据库缓存信息')
            this._getData()
            this.interval = window.setInterval(() => {
                // 获取数据的逻辑
                this._getData()
            }, intervalTime)
        },
        deactivated() {
            if (this.interval) {
                window.clearInterval(this.interval)
            }
        },
        methods: {
            _getData() {
                getCaches().then((res) => {
                    if (res.code === 200) {
                        this.cacheData = res.data
                    }
                })
            },
            ...mapMutations({
                setTitle: types.SET_TITLE
            })
        },
        components: {
            CacheCard
        }
    }
</script>
<style lang="scss" scoped>
    .rows {
        // flex-direction: column;
        display: flex;
        flex-wrap: wrap;
        justify-content: space-around;
        // align-content: flex-start;
    }
    .ivu-card {
        margin: 10px;
        // display: flex;
        height: 300px;
    }
</style>
