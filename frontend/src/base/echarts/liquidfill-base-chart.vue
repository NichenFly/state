<template>
    <div class="chart" ref="liquidfill"></div>
</template>
<script>
    import Echarts from 'echarts'
    import EchartsLiquidfill from 'echarts-liquidfill'
    export default {
        props: {
            option: {
                type: Object,
                default: {},
                required: true
            },
            media: {
                type: Array,
                default: () => {
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
                }
            }
        },
        created() {
            this.chart = {}
        },
        mounted() {
            this.chart = Echarts.init(this.$refs.liquidfill)
            this.chart.setOption({
                baseOption: this.option,
                media: this.media
            })
            this.chart.getZr().on('click', function() {
                console.log(arguments[0].topTarget.style.text)
            })
        },
        components: {
            Echarts,
            EchartsLiquidfill
        }
    }
</script>
<style lang="scss" scoped>
    .chart {
        width: 200px;
        height: 200px;
    }
</style>


