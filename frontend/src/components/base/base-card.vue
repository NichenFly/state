<template>
    <div class="rows">
        <liquidfill-chart :option="option"></liquidfill-chart>
    </div>
</template>
<script>
    // import {resolveListTo2} from 'common/js/utils'
    /*
    每个波浪有自己的运动频率,计算方式是:虚拟时间/增长数的垂直坐标系,本次的数据在增长时的倾角越大,速度越快
    */
    import LiquidfillChart from 'base/echarts/liquidfill-base-chart'

    export default {
        computed: {
            liquidData: function() {
                //
            }
        },
        created() {
            this.chart = {}
            this.option = this._getOption()
            this.media = []
        },
        methods: {
            _getMedia() {
                return [{
                    query: {
                        maxWidth: 300
                    },
                    option: {
                        series: [{
                            label: {
                                normal: {
                                    textStyle: {
                                        fontSize: 16
                                    }
                                }
                            }
                        }]
                    }
                }]
            },
            _getOption() {
                return {
                    series: [{
                        type: 'liquidFill',
                        data: [{
                            name: 'query',
                            value: 0.6
                        }, {
                            name: 'yyy',
                            value: 0.5
                        }, {
                            name: 'xxx',
                            value: 0.4
                        }, {
                            name: 'zzz',
                            value: 0.3,
                            phase: 3
                        }],
                        // waveAnimation: false,
                        radius: '90%',
                        amplitude: 25,
                        period: function (value, index) {
                            return 2000 * index + 1000
                        },
                        outline: {
                            show: false
                        },
                        backgroundStyle: {
                            borderColor: '#156ACF',
                            borderWidth: 1,
                            color: '#E3F7FF',
                            shadowColor: 'rgba(0, 0, 0, 0.4)',
                            shadowBlur: 20
                        },
                        label: {
                            normal: {
                                position: ['50%', '20%'],
                                formatter: function () {
                                    return '10.18.13.20'
                                },
                                textStyle: {
                                    fontSize: 20,
                                    fontFamily: 'Lobster Two',
                                    color: '#D94854',
                                    insideColor: '#E3F7FF'
                                }
                            }
                        }
                    }],
                    tooltip: {
                        show: true,
                        formatter: (params) => {
                            console.log(params)
                            return 'xxx'
                        }
                    }
                }
            }
        },
        components: {
            LiquidfillChart
        }
    }
</script>
<style lang="scss" scoped>
    .rows {
        display: flex;
        flex-wrap: wrap;
        justify-content: flex-start;
    }
    .liquidfill {
        display: inline-block;
        align-self: auto;
        width: 200px;
        height: 200px;
        background-color: #f00;
    }
</style>

