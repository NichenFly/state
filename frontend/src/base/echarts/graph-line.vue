<template>
    <div class="chart" ref="graphLine"></div>
</template>
<script>
    import Echarts from 'echarts'
    export default {
        props: {
            option: {
                type: Object,
                default: {},
                required: true
            }
        },
        watch: {
            option() {
                console('watch...')
                this.chart.resize()
            }
        },
        created() {
            this.chart = {}
        },
        mounted() {
            this.chart = Echarts.init(this.$refs.graphLine)
            this.chart.setOption(this.option)
            this.chart.getZr().on('click', (event) => {
                this.$emit('graph-line-click', event.topTarget.style.text)
            })
        },
        activated() {
            this.chart.resize()
        },
        components: {
            Echarts
        }
    }
</script>
<style lang="scss" scoped>
    .chart {
        display: inline-flex;
        width: 100%;
        height: 600px;
    }
</style>
