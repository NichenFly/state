<template>
    <div>
        <Table :row-class-name="rowClassName" :columns="tableColumns" :data="tableData" v-if="tableColumns.length"></Table>
    </div>
</template>
<script>
    import ExpandRow from './replication-expand-row'
    import { intervalTime, expandWidth, relayWarn } from 'constants/constants'
    import { getReplications } from 'api/replication'
    import { mapMutations } from 'vuex'
    import * as types from 'store/mutation-types'

    export default {
        data() {
            return {
                tableColumns: [],
                tableData: []
            }
        },
        activated() {
            this.setTitle('展示数据库的主从复制信息')
            this._getData()
            this.interval = window.setInterval(() => {
                // 获取数据的逻辑
                this._getData()
            }, intervalTime)
        },
        deactivated() {
            if (this.interval) {
                window.clearInterval(this.interval)
            }
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
            _getData() {
                getReplications().then((res) => {
                    if (res.code === 200) {
                        // 展开的选项设置
                        let expand = {
                            type: 'expand',
                            width: expandWidth,
                            render: (h, params) => {
                                return h(ExpandRow, {
                                    props: {
                                        row: params.row
                                    }
                                })
                            }
                        }
                        // 加入展开的选项设置
                        let data = res.data
                        data.columns.unshift(expand)

                        // 设置警告信息到vuex
                        this.setWarnsReplication(this._getWarns(data.data))

                        this.tableColumns = data.columns
                        this.tableData = data.data
                    }
                })
            },
            _getWarns(data) {
                let num = 0
                data.forEach((item) => {
                    if (item.hasError) {
                        num++
                    } else if (item.relay > relayWarn) {
                        num++
                    }
                })
                return num
            },
            ...mapMutations({
                setTitle: types.SET_TITLE,
                setWarnsReplication: types.SET_WARNS_REPLICATION
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
