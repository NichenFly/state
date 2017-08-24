<template>
    <div>
        <Row class="rows">
            <Col span="7" class="card">
                <Card>
                    <p slot="title">标准卡片</p>
                    <p>卡片内容</p>
                    <p>卡片内容</p>
                    <p>卡片内容</p>
                </Card>
            </Col>
            <Col span="7"  class="card">
                <Card>
                    <p slot="title">标准卡片</p>
                    <p>卡片内容</p>
                    <p>卡片内容</p>
                    <p>卡片内容</p>
                </Card>
            </Col>
            <Col span="7"  class="card">
                <Card>
                    <p slot="title">标准卡片</p>
                    <p>卡片内容</p>
                    <p>卡片内容</p>
                    <p>卡片内容</p>
                </Card>
            </Col>
        </Row>
        <Row  class="rows">
            <Col span="7"  class="card">
                <Card>
                    <p slot="title">标准卡片</p>
                    <p>卡片内容</p>
                    <p>卡片内容</p>
                    <p>卡片内容</p>
                </Card>
            </Col>
            <Col span="7"  class="card">
                <Card>
                    <p slot="title">标准卡片</p>
                    <p>卡片内容</p>
                    <p>卡片内容</p>
                    <p>卡片内容</p>
                </Card>
            </Col>
        </Row>
    </div>
</template>
<script>
    export default {
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
