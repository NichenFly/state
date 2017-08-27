<template>
    <div>
        <base-card :baseList="baseData"></base-card>
        <Spin size="large" fix v-if="baseData.length === 0"></Spin>
    </div>
</template>
<script>
    import BaseCard from '@/components/base/base-card'
    import { mapMutations, mapGetters } from 'vuex'
    import * as types from 'store/mutation-types'
    import { basePath } from 'constants/constants'

    export default {
        computed: {
            ...mapGetters([
                'baseData',
                'menuDesc'
            ])
        },
        activated() {
            this.$nextTick(() => {
                this.setTitle(this.menuDesc[basePath])
            })
        },
        methods: {
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
