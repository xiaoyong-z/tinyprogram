<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 账单查询
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button type="primary" icon="el-icon-search" @click="handleSearch" style="float:right">搜索</el-button>
                <el-input v-model="query.traveller_name" placeholder="旅客名" class="handle-input mr10"></el-input>
            </div>
            <el-table
                    :data="currentList"
                    border
                    class="table"
                    ref="multipleTable"
                    header-cell-class-name="table-header"
                    @selection-change="handleSelectionChange"
            >
                <el-table-column prop="traveller_name" label="旅客名" align="center"></el-table-column>
                <el-table-column prop="activity_name" label="活动名" align="center"></el-table-column>
                <el-table-column prop="salesman_name" label="业务员" align="center"></el-table-column>
                <el-table-column prop="pay_date" label="支付日期" align="center" sortable></el-table-column>
                <el-table-column prop="pay_money" label="支付金额" align="center"></el-table-column>

<!--                <el-table-column label="操作" width="180" align="center">-->
<!--                    <template slot-scope="scope">-->
<!--                        <el-button-->
<!--                                type="text"-->
<!--                                icon="el-icon-detail"-->
<!--                                @click="handleEdit(scope.$index, scope.row)"-->
<!--                        >详情</el-button>-->
<!--                    </template>-->
<!--                </el-table-column>-->
            </el-table>
            <div class="pagination">
                <el-pagination
                        background
                        layout="total, prev, pager, next"
                        :current-page="query.pageIndex"
                        :page-size="query.pageSize"
                        :total="query.total"
                        @current-change="handlePageChange"
                ></el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="30%">
            <el-form ref="form" :model="form" label-width="70px">
                <el-form-item label="美食名">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="美食描述">
                    <el-input v-model="form.description"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import { fetchData } from '../../api/index';
    import clone from '../common/clone';
    import {getFormatDate, getTimestamp} from "../common/global_function";
    export default {
        name: 'basetable',
        data() {
            return {
                query: {
                    address: '',
                    name: '',
                    pageIndex: 1,
                    pageSize: 8,
                    total: 0,
                },
                tableData: [],
                tableDataCopy: [],
                multipleSelection: [],
                delList: [],
                editVisible: false,
                addVisible: false,
                pageTotal: 0,
                form: {},
                idx: -1,
                id: -1,
                travellerData: [],
                travellerDist: {},
                travellerOptions: [],
                activityData: [],
                activityDist: {},
                activityOptions: [],
                salesmanData: [],
                salesmanDist: {},
                salesmanOptions: [],
                currentList: [],
            };
        },
        created() {
            this.getData();
        },
        methods: {
            myFormatDate(time) {
                var date = new Date(time);
                return getFormatDate(date, 'yyyy-MM-dd hh:mm');
            },
            async getDataHelp(){
                await this.axios.get(this.GLOBAL.traveller_get).then((res)=>{
                    this.travellerData = res.data;
                    for(let index in this.travellerData){
                        this.travellerDist[this.travellerData[index].traveller_id] = this.travellerData[index].name;
                        this.travellerOptions.push({value:this.travellerData[index].traveller_id,label:this.travellerData[index].name})
                    }
                });

                await this.axios.get(this.GLOBAL.activity_get).then((res)=>{
                    this.activityData = res.data;
                    for(let index in this.activityData){
                        this.activityDist[this.activityData[index].activity_id] = this.activityData[index].name;
                        this.activityOptions.push({value:this.activityData[index].activity_id,label:this.activityData[index].name})
                    }
                });

                await this.axios.get(this.GLOBAL.salesman_get).then((res)=>{
                    this.salesmanData = res.data;
                    for(let index in this.salesmanData){
                        this.salesmanDist[this.salesmanData[index].salesman_id] = this.salesmanData[index].name;
                        this.salesmanOptions.push({value:this.salesmanData[index].salesman_id,label:this.salesmanData[index].name})
                    }
                });
            },
            // 获取 easy-mock 的模拟数据
            async getData() {
                await this.getDataHelp();
                await this.axios.get(this.GLOBAL.bill_get).then((res)=>{
                    this.tableData = res.data;
                    for(let index in this.tableData){
                        this.tableData[index].pay_date = this.myFormatDate(this.tableData[index].pay_date);
                        this.tableData[index].traveller_name = this.travellerDist[this.tableData[index].traveller_id];
                        this.tableData[index].activity_name = this.activityDist[this.tableData[index].activity_id];
                        this.tableData[index].salesman_name = this.salesmanDist[this.tableData[index].salesman_id];
                    }
                    console.log(this.tableData);
                    this.query.total = this.tableData.length;
                    this.sliceList();
                    this.tableDataCopy = clone(this.tableData);
                });
                // fetchData(this.query).then(res => {
                //     console.log(res);
                //     this.tableData = res.list;
                //     this.pageTotal = res.pageTotal || 50;
                // });
            },
            // 触发搜索按钮
            handleSearch() {
                this.$set(this.query, 'pageIndex', 1);
                this.tableData = []
                for(let i in this.tableDataCopy){
                    // console.log(this.query.name);
                    // console.log(this.tableDataCopy[i].name);
                    if(this.tableDataCopy[i].traveller_name.indexOf(this.query.traveller_name) != -1){
                        this.tableData.push(this.tableDataCopy[i]);
                    }
                }
                this.sliceList();
            },
            // 分页导航
            sliceList () {
                this.currentList = this.tableData.slice((this.query.pageIndex-1)*this.query.pageSize, this.query.pageIndex*this.query.pageSize);
                this.query.total = this.tableData.length;
                console.log(this.currentList);
            },
            // 分页导航
            handlePageChange(val) {
                this.$set(this.query, 'pageIndex', val);
                this.sliceList();
            },

            // 多选操作
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            delAllSelection() {
                const length = this.multipleSelection.length;
                let str = '';
                this.delList = this.delList.concat(this.multipleSelection);
                for (let i = 0; i < length; i++) {
                    str += this.multipleSelection[i].name + ' ';
                }
                this.$message.error(`删除了${str}`);
                this.multipleSelection = [];
            },
            // 编辑操作
            handleEdit(index, row) {
                this.idx = index;
                this.form = clone(row);
                this.editVisible = true;
            },
            // 保存编辑
            saveEdit() {
                this.editVisible = false;
                this.$message.success(`修改第 ${this.idx + 1} 行成功`);
                this.$set(this.tableData, this.idx, this.form);
                let patchData = this.$qs.stringify({
                    food_id: this.form.food_id,
                    name: this.form.name,
                    description: this.form.description,
                });
                this.axios.patch(this.GLOBAL.food_update, patchData).then((res)=>{
                    console.log(res);
                }).catch((res)=>{
                    console.log(res);
                });
                this.form = {};
            },
            // 分页导航
            handlePageChange(val) {
                this.$set(this.query, 'pageIndex', val);
                this.getData();
            }
        },
    };
</script>

<style scoped>
    .handle-box {
        margin-bottom: 20px;
    }

    .handle-select {
        width: 120px;
    }

    .handle-input {
        width: 300px;
        display: inline-block;
        float: right;
    }
    .table {
        width: 100%;
        font-size: 14px;
    }
    .red {
        color: #ff0000;
    }
    .mr10 {
        margin-right: 10px;
    }
    .table-td-thumb {
        display: block;
        margin: auto;
        width: 40px;
        height: 40px;
    }
</style>
