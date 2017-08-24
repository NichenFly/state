<template>
    <div>
        <cache-card></cache-card>
    </div>
</template>
<script>
    import CacheCard from '@/components/cache/cache-card'
    export default {
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
