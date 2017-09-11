<template>
    <div>
        <!-- <base-card :baseList="baseData"></base-card> -->
        <liquidfill-chart 
            :option="baseOption" 
            @liquidfill-click="liquidClick" 
            v-for="(baseOption, index) in baseOptions" 
            :key="index"></liquidfill-chart>
        <Loading v-if="baseOptions.length === 0"></Loading>
        <Modal
            v-model="modalShow"
            :title="modalTitle"
            :styles="{top: '20px'}"
            width="70">
            <p class="input-area">
                <Input v-model.trim="filterText" placeholder="输入筛选内容"></Input>
            </p>
            <p>
                <Table :columns="hostBaseInfo.columns" 
                       :data="hostBaseData" 
                       :show-header="false"
                       v-if="!loadingState && hostBaseInfo.columns.length !== 0"></Table>
                <Loading v-if="loadingState"></Loading>
            </p>
        </Modal>
    </div>
</template>
<script>
    import { mapMutations, mapGetters } from 'vuex'
    import * as types from 'store/mutation-types'
    import { basePath, CODE_OK } from 'constants/constants'
    import LiquidfillChart from 'base/echarts/liquidfill-base-chart'
    import { getBasesByHost } from 'api/base'
    import { expand } from 'common/js/expand'
    import BaseExpand from '@/components/base/base-item-expand'
    import Loading from 'base/loading/loading'

    export default {
        data() {
            return {
                modalShow: false,
                loadingState: false,
                modalTitle: '',
                filterText: '',
                hostBaseInfo: {
                    columns: [],
                    data: []
                }
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
            hostBaseData() {
                let data = []
                let filterText = this.filterText
                if (filterText) {
                    this.hostBaseInfo.data.forEach((item) => {
                        if (~item.category.toLowerCase().indexOf(filterText.toLowerCase())) {
                            data.push(item)
                        }
                    })
                } else {
                    data = this.hostBaseInfo.data
                }
                return data
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
                this.modalTitle = `${text}的基本信息`
                this.filterText = ''
                this.loadingState = true
                this.modalShow = true
                getBasesByHost(text).then((res) => {
                    if (res.code === CODE_OK) {
                        let data = res.data
                        this.hostBaseInfo.data = data.data
                        data.columns.unshift(expand(BaseExpand))
                        this.hostBaseInfo.columns = data.columns
                        this.loadingState = false
                    }
                })
            },
            ...mapMutations({
                setTitle: types.SET_TITLE
            })
        },
        components: {
            LiquidfillChart,
            Loading
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
    .input-area {
        margin-bottom: 10px;
    }
</style>
