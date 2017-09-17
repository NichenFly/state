<template>
    <div>
        <div class="chart" ref="graphLine"></div>
        <!-- <loading v-if=""></loading> -->
    </div>
    
</template>
<script>
    import Echarts from 'echarts'
    import Loading from 'base/loading/loading'
    import { MAX_NUM, STEP_LENGTH, ERROR_COLOR, SYMBOL_SIZE, COLORS, LABEL_COLOR } from 'constants/graph-const'
    import { STATE_YES_STRING } from 'constants/constants'
    export default {
        props: {
            nodeData: {
                type: Object,
                default: {},
                required: true
            }
        },
        watch: {
            nodes() {
                this.chart.setOption(this.option)
                this.chart.resize()
            },
            links() {
                this.chart.setOption(this.option)
                this.chart.resize()
            },
            arrows() {
                this.chart.setOption(this.option)
                this.chart.resize()
            },
            option() {
                this.chart.setOption(this.option)
                this.chart.resize()
            }
        },
        computed: {
            nodes() {
                return this._getNodes(this.nodeData)
            },
            links() {
                return this._getLinks(this.nodeData)
            },
            arrows() {
                return this._getArrows(this.links)
            },
            option() {
                return {
                    xAxis: {
                        show: false,
                        type: 'value'
                    },
                    yAxis: {
                        show: false,
                        type: 'value'
                    },
                    itemStyle: {
                        normal: {
                            color: '#1593e7'
                        }
                    },
                    series: [{
                        type: 'graph',
                        layout: 'none',
                        coordinateSystem: 'cartesian2d',
                        symbolSize: SYMBOL_SIZE,
                        label: {
                            normal: {
                                show: true,
                                color: LABEL_COLOR
                            }
                        },
                        degeLabel: {
                            normal: {
                                show: true,
                                position: 'middle'
                            }
                        },
                        edgeSymbol: ['circle', 'arrow'],
                        edgeSymbolSize: [0, 10],
                        data: this.nodes,
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
                            symbolSize: 6
                        },
                        lineStyle: {
                            normal: {
                                color: '#aaa',
                                width: 1,
                                opacity: 0.4
                            }
                        },
                        data: this.arrows
                    }]
                }
            }
        },
        created() {
            this.nodeObjs = []
            this.colorIndex = 0
            this.chart = {}
        },
        mounted() {
            // console.log(this.$refs.graphLine.clientHeight)
            // console.log(this.$refs.graphLine.clientWidth)
            this.chart = Echarts.init(this.$refs.graphLine)
            this.chart.setOption(this.option)
            this.chart.getZr().on('click', (event) => {
                if (event.topTarget) {
                    this.$emit('graph-line-click', event.topTarget.style.text)
                }
            })
        },
        activated() {
            this.chart.resize()
        },
        methods: {
            _getNodes(data) {
                if (!data) {
                    return []
                }
                let nodes = []
                let line = 0
                let currentNum = 0
                data.groups.forEach((group) => {
                    let masters = group.masters
                    let slaves = group.slaves
                    let maxHostNums = masters.length > slaves.length ? masters.length : slaves.length
                    if (currentNum + maxHostNums > MAX_NUM) {
                        line++
                        currentNum = 1
                    }

                    masters.forEach((master, index) => {
                        nodes.push({
                            name: master,
                            value: [this._getXaxis(currentNum, masters.length, index, maxHostNums), line * MAX_NUM],
                            itemStyle: data.nodes[master] === STATE_YES_STRING ? { normal: { color: ERROR_COLOR } } : undefined
                        })
                    })

                    slaves.forEach((slave, index) => {
                        nodes.push({
                            name: slave,
                            value: [this._getXaxis(currentNum, slaves.length, index, maxHostNums), (line + 1) * MAX_NUM],
                            itemStyle: data.nodes[slave] === STATE_YES_STRING ? { normal: { color: ERROR_COLOR } } : undefined
                        })
                    })
                    currentNum += maxHostNums
                })
                this.nodeObjs = nodes
                return nodes
            },

            /**
             * 获取x坐标
             */
            _getXaxis(currentNum, hostNums, index, maxHostNums) {
                let x = 0
                if (hostNums === maxHostNums) {
                    x = currentNum * STEP_LENGTH + hostNums / maxHostNums * STEP_LENGTH * index
                } else {
                    let totalLength = STEP_LENGTH * (maxHostNums - 1)
                    let stepLength = totalLength / (hostNums + 1)
                    x = currentNum * stepLength + stepLength * (index + 1)
                }
                return x
            },
            _getLinks(data) {
                if (!data) {
                    return []
                }
                let links = []
                let masters = data.masters
                masters.forEach((master) => {
                    let slaves = master.slaves
                    slaves.forEach((slave, index) => {
                        links.push({
                            source: master.name,
                            target: slave.name,
                            lineStyle: {
                                normal: {
                                    color: COLORS[index % COLORS.length]
                                }
                            }
                        })
                    })
                })
                return links
            },
            _getArrows(links) {
                if (!links || !links.length) {
                    return []
                }
                let nodeObjTmps = {}
                let nodeObjs = this.nodeObjs
                nodeObjs.forEach((nodeObj) => {
                    nodeObjTmps[nodeObj.name] = nodeObj.value
                })
                let arrows = []
                links.forEach((link, index) => {
                    arrows.push([
                        {
                            coord: nodeObjTmps[link.source]
                        },
                        {
                            coord: nodeObjTmps[link.target]
                        }
                    ])
                })
                return arrows
            }
        },
        components: {
            Echarts,
            Loading
        }
    }
</script>
<style lang="scss" scoped>
    .chart {
        display: inline-flex;
        width: 100%;
        height: 400px;
    }
</style>
