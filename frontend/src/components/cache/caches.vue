<template>
    <div>
        <cache-card :cacheList="cacheData"></cache-card>
        <Spin size="large" fix v-if="cacheData.length === 0"></Spin>
    </div>
</template>
<script>
    import CacheCard from '@/components/cache/cache-card'
    import { mapMutations, mapGetters } from 'vuex'
    import * as types from 'store/mutation-types'
    import { cachePath } from 'constants/constants'

    export default {
        computed: {
            ...mapGetters([
                'cacheData',
                'menuDesc'
            ])
        },
        activated() {
            this.setTitle(this.menuDesc[cachePath])
        },
        methods: {
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
