<template>
    <div>
        <Row class="rows">
            <Col span="12" class="card">
                <Card>
                    <p slot="title">10.18.13.7</p>
                    <div>
                        <!-- <p>mysql  Ver 14.14 Distrib 5.7.16, for Linux (x86_64) using  EditLine wrapper</p> -->
                        <Table size="small" :columns="columns" :data="data" :show-header="false"></Table>
                    </div>
                </Card>
            </Col>
            <Col span="12" class="card">
                <Card>
                    <p slot="title">10.18.13.7</p>
                    <div>
                        <!-- <p>mysql  Ver 14.14 Distrib 5.7.16, for Linux (x86_64) using  EditLine wrapper</p> -->
                        <Table size="small" :columns="columns" :data="data" :show-header="false"></Table>
                    </div>
                </Card>
            </Col>
        </Row>
    </div>
</template>
<script>
    export default {
        data () {
            return {
                columns: [
                    {
                        title: '类别',
                        key: 'category',
                        width: 170
                    },
                    {
                        title: '状态',
                        key: 'status'
                    }
                ],
                data: [
                    {
                        category: 'SSL',
                        status: 'Not in use'
                    },
                    {
                        category: 'Server version',
                        status: '5.7.16-log MySQL Community Server (GPL)'
                    },
                    {
                        category: 'UNIX socket',
                        status: '/var/lib/mysql/mysql.sock'
                    },
                    {
                        category: 'Uptime',
                        status: '1 day 4 hours 12 min 43 sec'
                    },
                    {
                        category: 'Threads',
                        status: 18
                    },
                    {
                        category: 'Questions',
                        status: 14880
                    },
                    {
                        category: 'Slow queries',
                        status: 1266
                    },
                    {
                        category: 'Opens',
                        status: 426
                    },
                    {
                        category: 'Flush tables',
                        status: 1
                    },
                    {
                        category: 'Open tables',
                        status: 376
                    },
                    {
                        category: 'Queries per second avg',
                        status: 0.146
                    }
                ]
            }
        }
    }
</script>
<style lang="scss" scoped>
    .rows {
        display: flex;
        flex-wrap: wrap;
    }
    .ivu-card {
        margin: 10px;
    }
</style>

