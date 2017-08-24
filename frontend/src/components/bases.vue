<template>
    <div>
        <base-card></base-card>
    </div>
</template>
<script>
    import BaseCard from '@/components/base-card'
    export default {
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
