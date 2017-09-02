<template>
    <div class="rows">
        <liquidfill-chart :option="option"></liquidfill-chart>
        <liquidfill-chart :option="option"></liquidfill-chart>
        <liquidfill-chart :option="option"></liquidfill-chart>
        <liquidfill-chart :option="option"></liquidfill-chart>
        <liquidfill-chart :option="option"></liquidfill-chart>
        <liquidfill-chart :option="option"></liquidfill-chart>
    </div>
</template>
<script>
    // import {resolveListTo2} from 'common/js/utils'
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
                        }, 0.5, {
                            value: 0.4,
                            amplitude: 15
                        }, {
                            value: 0.3,
                            amplitude: 20,
                            waveLength: 100
                        }],
                        // waveAnimation: false,
                        radius: '90%',
                        amplitude: 20,
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

