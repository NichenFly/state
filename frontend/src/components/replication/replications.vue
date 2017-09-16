<template>
    <div>
        <!-- <Table  @on-expand="expand" 
                :row-class-name="rowClassName" 
                :columns="replicationData.columns" 
                :data="replicationData.data" 
                v-if="replicationData.columns && replicationData.columns.length"></Table> -->
        <graph-line-chart :nodeData="replicationData"></graph-line-chart>
        <!-- <Spin size="large" fix v-if="!replicationData.columns || replicationData.columns.length === 0"></Spin> -->
    </div>
</template>
<script>
    import ExpandRow from './replication-expand-row'
    import { mapMutations, mapGetters } from 'vuex'
    import * as types from 'store/mutation-types'
    import { replicationPath } from 'constants/constants'
    import GraphLineChart from 'base/echarts/graph-line'

    export default {
        computed: {
            ...mapGetters([
                'replicationData',
                'menuDesc'
            ])
        },
        activated() {
            this.setTitle(this.menuDesc[replicationPath])
        },
        methods: {
            ...mapMutations({
                setTitle: types.SET_TITLE
            })
        },
        components: {
            ExpandRow,
            GraphLineChart
        }
    }
</script>
<style lang="scss" type="stylesheet/scss">
    .ivu-table .table-warn-row td { 
        background-color: #f90;
        color: #fff;
        font-size: 16px;
    }
    .ivu-table .table-error-row td {
        background-color: #ff6600;
        color: #fff;
        font-size: 16px;
    }
</style>
