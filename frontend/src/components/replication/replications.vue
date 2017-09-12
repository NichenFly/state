<template>
    <div>
        <!-- <Table  @on-expand="expand" 
                :row-class-name="rowClassName" 
                :columns="replicationData.columns" 
                :data="replicationData.data" 
                v-if="replicationData.columns && replicationData.columns.length"></Table> -->
        <graph-line-chart :option="replicationOption"></graph-line-chart>
        <Spin size="large" fix v-if="!replicationData.columns || replicationData.columns.length === 0"></Spin>
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
            replicationOption() {
                return {
                    xAxis: {
                        show: false,
                        type: 'value'
                    },
                    yAxis: {
                        show: false,
                        type: 'value'
                    },
                    dataRange: {
                        show: false,
                        min: 0,
                        max: 100,
                        y: '60%',
                        calculable: true,
                        color: ['#ff3333', 'orange', 'yellow', 'lime', 'aqua']
                    },
                    series: [{
                        type: 'graph',
                        layout: 'none',
                        coordinateSystem: 'cartesian2d',
                        symbolSize: 40,
                        label: {
                            normal: {
                                show: true,
                                color: '#000'
                            }
                        },
                        degeLabel: {
                            normal: {
                                show: true,
                                position: 'middle'
                            }
                        },
                        edgeSymbol: ['circle', 'arrow'],
                        edgeSymbolSize: [4, 10],
                        data: this.replData,
                        links: this.links
                    }, {
                        name: 'A',
                        type: 'lines',
                        coordinateSystem: 'cartesian2d',
                        effect: {
                            show: true,
                            smooth: false,
                            trailLength: 0,
                            symbol: 'arrow',
                            symbolSize: 12
                        },
                        lineStyle: {
                            normal: {
                                // color: function() {
                                //     return getColorPalette(3);
                                // }(),
                                width: 1,
                                opacity: 0.4
                            }
                        },
                        data: this.arrowData
                    }]
                }
            },
            ...mapGetters([
                'replicationData',
                'menuDesc'
            ])
        },
        created() {
            this.replData = [{
                name: 'aa',
                value: [10, 10]
            }, {
                name: 'bb',
                value: [10, 30]
            }, {
                name: 'cc',
                value: [10, 50]
            }, {
                name: 'dd',
                value: [30, 10]
            }, {
                name: 'ee',
                value: [30, 30]
            }, {
                name: 'ff',
                value: [30, 50]
            }, {
                name: 'gg',
                value: [50, 10]
            }, {
                name: 'hh',
                value: [50, 30]
            }, {
                name: 'ii',
                value: [70, 10]
            }, {
                name: 'jj',
                value: [70, 30]
            }, {
                name: 'kk',
                value: [70, 50]
            }]
            this.links = [{
                source: 0,
                target: 1
            }, {
                source: 0,
                target: 4
            }, {
                source: 0,
                target: 5
            }, {
                source: 1,
                target: 4
            }, {
                source: 1,
                target: 6
            }, {
                source: 4,
                target: 7
            }, {
                source: 3,
                target: 8
            }, {
                source: 4,
                target: 9
            }, {
                source: 6,
                target: 10
            }]
            this.arrowData = [
                [{
                    coord: [10, 10]
                }, {
                    coord: [10, 30],
                    lineStyle: {
                        normal: {
                            color: '#dcdcdc'
                        }
                    }
                }],
                [{
                    coord: [10, 30]
                }, {
                    coord: [30, 30],
                    value: 80
                }],
                [{
                    coord: [30, 30]
                }, {
                    coord: [50, 30],
                    value: 50
                }],
                [{
                    coord: [10, 10]
                }, {
                    coord: [30, 30],
                    value: 12
                }],
                [{
                    coord: [10, 10]
                }, {
                    coord: [30, 50],
                    value: 25
                }],
                [{
                    coord: [10, 30]
                }, {
                    coord: [50, 10],
                    value: 8
                }],
                [{
                    coord: [30, 30]
                }, {
                    coord: [70, 30],
                    value: 4
                }],
                [{
                    coord: [30, 10]
                }, {
                    coord: [70, 10],
                    value: 75
                }],
                [{
                    coord: [50, 10]
                }, {
                    coord: [70, 50],
                    value: 38
                }]
            ]
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
