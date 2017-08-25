<template>
    <div>
        <Row class="rows" v-for="(bases,index) in baseData" :key="index">
            <Col span="12" class="card" v-for="{base, baseIndex} in bases" :key="baseIndex">
                <Card>
                    <p slot="title">{{ base }}</p>
                    <div>
                        <!-- <p>mysql  Ver 14.14 Distrib 5.7.16, for Linux (x86_64) using  EditLine wrapper</p> -->
                        <Table size="small" :columns="base.columns" :data="base.data" :show-header="false"></Table>
                    </div>
                </Card>
            </Col>
        </Row>
    </div>
</template>
<script>
    import {resolveListTo2} from 'common/js/utils'

    export default {
        props: {
            baseList: {
                type: Array,
                default: []
            }
        },
        computed: {
            baseData() {
                return resolveListTo2(this.baseList)
            }
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

