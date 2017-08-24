<style scoped>
    .expand-container {
        font-size: 14px;
    }
    .expand-row{
        margin-bottom: 16px;
    }
</style>
<template>
    <div class="expand-container">
        <Row class="expand-row">
            <Col span="12">
                <span class="expand-key">Last_SQL_Running：</span>
                <span class="expand-value">{{ row.Last_SQL_Running }}</span>
            </Col>
            <Col span="12">
                <span class="expand-key">Last_SQL_State：</span>
                <span class="expand-value">{{ row.Last_SQL_State }}</span>
            </Col>
        </Row>
        <Row>
            <Col span="12">
                <span class="expand-key">Last_IO_Running：</span>
                <span class="expand-value">{{ row.Last_IO_Running }}</span>
            </Col>
            <Col span="12">
                <span class="expand-key">Last_IO_State：</span>
                <span class="expand-value">{{ row.Last_IO_State }}</span>
            </Col>
        </Row>
    </div>
</template>
<script>
    export default {
        props: {
            row: Object
        }
    }
</script>