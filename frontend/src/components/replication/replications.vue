<template>
    <div>
        <Table @on-expand="expand" :row-class-name="rowClassName" :columns="replicationData.columns" :data="replicationData.data" v-if="replicationData.columns && replicationData.columns.length"></Table>
        <Spin size="large" fix v-if="!replicationData.columns || replicationData.columns.length === 0"></Spin>
    </div>
</template>
<script>
    import ExpandRow from './replication-expand-row'
    import { mapMutations, mapGetters } from 'vuex'
    import * as types from 'store/mutation-types'
    import { relayWarn, replicationPath } from 'constants/constants'

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
            rowClassName (row, index) {
                if (row.hasError) {
                    return 'table-error-row'
                }

                if (row.relay > relayWarn) {
                    return 'table-warn-row'
                }
                return ''
            },
            expand(data, status) {
                let changedData = Object.assign({status: status}, data)
                this.setExpand(changedData)
            },
            ...mapMutations({
                setTitle: types.SET_TITLE,
                setExpand: types.SET_EXPAND_REPLICATION
            })
        },
        components: {
            ExpandRow
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
