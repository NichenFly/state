<style scoped>
    .expand-container {
        font-size: 14px;
    }
    .expand-row{
        margin-bottom: 16px;
    }
    .expand-row .ivu-col:hover {
        background: #e9eaec;
    }
    .no-data {
        display: flex;
        justify-content: center;
    }
</style>
<template>
    <div class="expand-container">
        <Row class="expand-row">
            <Col span="12" v-for="(value, key) in filteredRow" 
                 :key="key">
                <span class="expand-key">{{ key }}：</span>
                <span class="expand-value">{{ value }}</span>
            </Col>
            <p class="no-data" v-if="filterRowKeys.length === 0">暂无数据显示</p>
        </Row>
    </div>
</template>
<script>
    import _ from 'underscore'
    import { showExcepts } from 'constants/constants'

    export default {
        props: {
            row: {
                type: Object,
                default: {}
            }
        },
        computed: {
            filteredRow() {
                return this._filterProperities()
            },
            filterRowKeys() {
                return _(this.filteredRow).keys()
            }
        },
        methods: {
            _filterProperities() {
                let keys = _(this.row).keys()
                let filterKeys = _(keys).filter((key) => !key.startsWith('_') && !_(showExcepts).contains(key))
                return _(this.row).pick(filterKeys)
            }
        }
    }
</script>