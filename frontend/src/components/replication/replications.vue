<template>
    <div>
        <Table :row-class-name="rowClassName" :columns="columns" :data="data" :no-data-text="noDataText"></Table>
    </div>
</template>
<script>
    import ExpandRow from './replication-expand-row.vue'

    export default {
        data () {
            return {
                columns: [
                    {
                        type: 'expand',
                        width: 50,
                        render: (h, params) => {
                            return h(ExpandRow, {
                                props: {
                                    row: params.row
                                }
                            })
                        }
                    },
                    {
                        title: '主机',
                        key: 'host'
                    },
                    {
                        title: '主/从',
                        key: 'masterSlave'
                    },
                    {
                        title: '状态',
                        key: 'state'
                    },
                    {
                        title: '时延(ms)',
                        key: 'relay'
                    }
                ],
                data: [
                    {
                        host: '10.18.13.7',
                        masterSlave: 'slave',
                        state: '正常',
                        relay: 3,
                        hasError: false,
                        Last_SQL_Running: 'Yes',
                        Last_IO_Running: 'Yes',
                        Last_IO_State: '...',
                        Last_SQL_State: 'Waiting for more events ...'
                    },
                    {
                        host: '10.18.13.8',
                        masterSlave: 'master',
                        state: '正常'
                    },
                    {
                        host: '10.18.13.34',
                        masterSlave: 'master/slave',
                        state: '正常',
                        relay: 100,
                        hasError: false,
                        Last_SQL_Running: 'Yes',
                        Last_IO_Running: 'Yes',
                        Last_IO_State: '...',
                        Last_SQL_State: 'Waiting for more events ...'
                    },
                    {
                        host: '10.18.13.35',
                        masterSlave: 'master/slave',
                        state: '出错',
                        relay: 0,
                        hasError: true,
                        Last_SQL_Running: 'No',
                        Last_IO_Running: 'Yes',
                        Last_IO_State: '...',
                        Last_SQL_State: 'Waiting for more events ...'
                    },
                    {
                        host: '10.18.13.70',
                        masterSlave: 'slave',
                        state: '正常',
                        relay: 10000,
                        hasError: false,
                        Last_SQL_Running: 'Yes',
                        Last_IO_Running: 'Yes',
                        Last_IO_State: '...',
                        Last_SQL_State: 'Waiting for more events ...'
                    },
                    {
                        host: '10.18.13.71',
                        masterSlave: 'slave',
                        state: '出错',
                        relay: 0,
                        hasError: true,
                        Last_SQL_Running: 'No',
                        Last_IO_Running: 'Yes',
                        Last_IO_State: '...',
                        Last_SQL_State: 'Error'
                    }
                ],
                noDataText: '暂无配置数据'
            }
        },
        methods: {
            rowClassName (row, index) {
                if (row.hasError) {
                    return 'table-error-row'
                }

                if (row.relay > 1000) {
                    return 'table-warn-row'
                }
                return ''
            }
        },
        computed: {
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
