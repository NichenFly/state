<style scoped>
    .expand-container {
        font-size: 14px;
    }
    .desc {
        display: flex;
        justify-content: flex-start;
    }
</style>
<template>
    <div class="expand-container">
        <p class="desc">{{ row.desc }}</p>
    </div>
</template>
<script>
    export default {
        props: {
            row: {
                type: Object,
                default: {}
            }
        }
    }
</script>