<template>
    <div>
        <base-card :baseList="baseData"></base-card>
        <Spin size="large" fix v-if="baseData.length === 0"></Spin>
    </div>
</template>
<script>
    import BaseCard from '@/components/base/base-card'
    import { mapMutations } from 'vuex'
    import * as types from 'store/mutation-types'
    import { intervalTime } from 'constants/constants'
    import { getBases } from 'api/base'

    export default {
        data() {
            return {
                baseData: []
            }
        },
        activated() {
            this.setTitle('展示数据库的基本信息')
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
                getBases().then((res) => {
                    if (res.code === 200) {
                        this.baseData = res.data
                    }
                })
            },
            ...mapMutations({
                setTitle: types.SET_TITLE
            })
        },
        components: {
            BaseCard
        }
    }
</script>
<style lang="scss" scoped>
    .rows {
        display: flex;
        flex-wrap: wrap;
        justify-content: space-around;
    }
    .ivu-card {
        margin: 10px;
        height: 300px;
    }
</style>
