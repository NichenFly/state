<template>
    <div>
        <base-card :baseList="baseData"></base-card>
    </div>
</template>
<script>
    import BaseCard from '@/components/base/base-card'
    import { mapGetters } from 'vuex'
    import { intervalTime } from 'constants/constants'

    export default {
        computed: {
            ...mapGetters([
                'baseData'
            ])
        },
        activated() {
            this.interval = window.setInterval(() => {
                // 获取数据的逻辑
                console.log('123')
            }, intervalTime)
        },
        deactivated() {
            if (this.interval) {
                console.log('456')
                window.clearInterval(this.interval)
            }
        },
        components: {
            BaseCard
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
