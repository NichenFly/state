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
                       height="400"
                       v-if="hostBaseInfo.columns.length !== 0"></Table>
                <Loading v-if="hostBaseInfo.columns.length === 0"></Loading>
            </p>
        </Modal>
    </div>
</template>
<script>
    import { mapMutations, mapGetters } from 'vuex'
    import * as types from 'store/mutation-types'
    import { basePath, CODE_OK, baseExpandWidth } from 'constants/constants'
    import LiquidfillChart from 'base/echarts/liquidfill-base-chart'
    import { getBasesByHost } from 'api/base'
    import { expand } from 'common/js/expand'
    import BaseExpand from '@/components/base/base-item-expand'
    import Loading from 'base/loading/loading'

    export default {
        data() {
            return {
                modalShow: false,
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
                            data: [
                                {
                                    'name': '1',
                                    'value': 0.5,
                                    'growth': 0.2
                                },
                                {
                                    'name': '2',
                                    'value': 0.35,
                                    'growth': 0.25
                                },
                                {
                                    'name': '3',
                                    'value': 0.2,
                                    'growth': 0.3
                                }
                            ],
                            waveAnimation: !base.hasError,
                            radius: '90%',
                            amplitude: 25,
                            period: function (value, index) {
                                return 2000 * value + 1000
                            },
                            outline: {
                                show: false
                            },
                            backgroundStyle: {
                                borderColor: base.hasError ? '#f00' : '#156ACF',
                                borderWidth: 1,
                                color: base.hasError ? '#f90' : '#E3F7FF',
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
                        }]
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
                this.modalShow = true
                getBasesByHost(text).then((res) => {
                    if (res.code === CODE_OK) {
                        let data = res.data
                        data.columns.unshift(expand(BaseExpand))
                        this.hostBaseInfo.columns = data.columns
                        this.hostBaseInfo.columns[0].width = baseExpandWidth
                        this.hostBaseInfo.data = data.data
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
