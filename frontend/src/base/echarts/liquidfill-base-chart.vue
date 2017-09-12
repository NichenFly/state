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
        watch: {
            option() {
                this.chart.setOption(this.option)
                this.chart.resize()
            }
        },
        created() {
            this.chart = {}
        },
        activated() {
            this.chart.resize()
        },
        mounted() {
            this.chart = Echarts.init(this.$refs.liquidfill)
            this.chart.setOption({
                baseOption: this.option,
                media: this.media
            })
            this.chart.getZr().on('click', (event) => {
                if (event.topTarget) {
                    this.chart.resize()
                    this.$emit('liquidfill-click', event.topTarget.style.text)
                }
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
        display: inline-flex;
        width: 200px;
        height: 200px;
    }
</style>


