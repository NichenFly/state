<template>
    <div>
        <div class="chart" ref="graphLine"></div>
        <!-- <loading v-if=""></loading> -->
    </div>
    
</template>
<script>
    import Echarts from 'echarts'
    import Loading from 'base/loading/loading'
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
                this.chart.setOption(this.option)
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
                if (event.topTarget) {
                    this.$emit('graph-line-click', event.topTarget.style.text)
                }
            })
        },
        activated() {
            this.chart.resize()
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
        height: 600px;
    }
</style>
