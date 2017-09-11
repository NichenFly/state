<style scoped>
    .expand-container {
        font-size: 14px;
    }
    .expand-row{
        margin-bottom: 16px;
    }
    .expand-row .ivu-col:hover {
        background: #e9eaec;
    }
    .no-data {
        display: flex;
        justify-content: center;
    }
</style>
<template>
    <div class="expand-container">
        <Row class="expand-row">
            <p class="no-data">{{ row }}</p>
        </Row>
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