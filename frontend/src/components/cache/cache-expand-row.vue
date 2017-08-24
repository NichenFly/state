<style scoped>
    .expand-container {
        font-size: 14px;
    }
</style>
<template>
    <div class="expand-container">
        <span class="expand-value"> {{ row.desc }} </span>
    </div>
</template>
<script>
    export default {
        props: {
            row: Object
        }
    }
</script>