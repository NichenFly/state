<template>
    <div>
        <!-- <base-card :baseList="baseData"></base-card> -->
        <liquidfill-chart :option="baseOption" @liquidfill-click="liquidClick" v-for="(baseOption, index) in baseOptions" :key="index"></liquidfill-chart>
        <Spin size="large" fix v-if="baseOptions.length === 0"></Spin>
        <Modal
            v-model="modalShow"
            title="somethings show"
            :mask-closable="false"
            width="80">
            <p>自定义宽度，单位 px，默认 520px。</p>
            <p>对话框的宽度是响应式的，当屏幕尺寸小于 768px 时，宽度会变为自动<code>auto</code>。</p>
        </Modal>
    </div>
</template>
<script>
    import { mapMutations, mapGetters } from 'vuex'
    import * as types from 'store/mutation-types'
    import { basePath } from 'constants/constants'
    import LiquidfillChart from 'base/echarts/liquidfill-base-chart'

    /*
    每个波浪有自己的运动频率,计算方式是:虚拟时间/增长数的垂直坐标系,本次的数据在增长时的倾角越大,速度越快
    */
    export default {
        data() {
            return {
                modalShow: false
            }
        },
        computed: {
            baseOptions() {
                let baseData = this.baseData
                return baseData.map((base) => {
                    return {
                        series: [{
                            type: 'liquidFill',
                            data: base.data,
                            // waveAnimation: false,
                            radius: '90%',
                            amplitude: 25,
                            period: function (value, index) {
                                return 2000 * base.data[index].growth + 1000
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
                                        return base.title
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
                                // console.log(params.data)
                                return `${params.data.name}`
                            }
                        }
                    }
                })
            },
            ...mapGetters([
                'baseData',
                'menuDesc'
            ])
        },
        activated() {
            this.$nextTick(() => {
                this.setTitle(this.menuDesc[basePath])
            })
        },
        methods: {
            liquidClick(text) {
                console.log(text)
                this.modalShow = true
            },
            ...mapMutations({
                setTitle: types.SET_TITLE
            })
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
        justify-content: space-around;
    }
    .ivu-card {
        margin: 10px;
        height: 300px;
    }
</style>
