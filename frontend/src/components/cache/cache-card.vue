<template>
    <div>
        <Row class="rows" v-for="(caches, index) in cacheData" :key="index">
            <Col span="12" class="card" v-for="cache in caches" :key="cache.title">
                <Card>
                    <p slot="title"> {{cache.title}} </p>
                    <div>
                        <!-- <p>mysql  Ver 14.14 Distrib 5.7.16, for Linux (x86_64) using  EditLine wrapper</p> -->
                        <Table size="small" :columns="cache.columns" :data="cache.data" :show-header="false"></Table>
                    </div>
                </Card>
            </Col>
        </Row>
    </div>
</template>
<script>
    import ExpandRow from './cache-expand-row'

    export default {
        props: {
            cacheList: {
                type: Array,
                default: []
            }
        },
        computed: {
            cacheData() {
                return this.cacheList
            }
        },
        components: {
            ExpandRow
        }
    }
</script>
<style lang="scss" scoped>
    .rows {
        display: flex;
        flex-wrap: wrap;
    }
    .ivu-card {
        margin: 10px;
    }
</style>

