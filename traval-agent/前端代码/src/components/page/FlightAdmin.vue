<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 航班管理
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button type="primary" icon="el-icon-plus" @click="handleAdd">添加</el-button>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch" style="float:right">搜索</el-button>
                <el-input v-model="query.flight_id" placeholder="航班号" class="handle-input mr10"></el-input>
            </div>
            <el-table
                    :data="currentList"
                    border
                    class="table"
                    ref="multipleTable"
                    header-cell-class-name="table-header"
                    @selection-change="handleSelectionChange"
            >
                <el-table-column prop="flight_id" label="ID"  align="center"></el-table-column>
                <el-table-column prop="flight_company" label="航空公司" align="center"></el-table-column>
                <el-table-column prop="new_start_time" label="起飞时间" align="center"></el-table-column>
                <el-table-column prop="new_end_time" label="降落时间" align="center"></el-table-column>
                <el-table-column prop="start_address" label="起飞地点" align="center"></el-table-column>
                <el-table-column prop="end_address" label="降落地点" align="center"></el-table-column>

                <el-table-column label="操作" width="180" align="center">
                    <template slot-scope="scope">
                        <el-button
                                type="text"
                                icon="el-icon-edit"
                                @click="handleEdit(scope.$index, scope.row)"
                        >编辑</el-button>
                        <el-button
                                type="text"
                                icon="el-icon-delete"
                                class="red"
                                @click="handleDelete(scope.$index, scope.row)"
                        >删除</el-button>
                    </template>
                </el-table-column>
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
                <el-form-item label="航空公司">
                    <el-input v-model="form.flight_company"></el-input>
                </el-form-item>
                <el-form-item label="起飞时间">
                    <div class="block">
                        <el-date-picker
                                v-model="form.new_start_time"
                                type="datetime"
                                format="yyyy-MM-dd HH:mm:ss"
                                value-format
                                placeholder="选择日期时间">
                        </el-date-picker>
                    </div>
                </el-form-item>
                <el-form-item label="降落时间">
                    <div class="block">
                        <el-date-picker
                                v-model="form.new_end_time"
                                type="datetime"
                                format="yyyy-MM-dd HH:mm:ss"
                                value-format
                                placeholder="选择日期时间">
                        </el-date-picker>
                    </div>
                </el-form-item>
                <el-form-item label="起飞地点">
                    <el-input v-model="form.start_address"></el-input>
                </el-form-item>
                <el-form-item label="降落地点">
                    <el-input v-model="form.end_address"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 添加弹出框 -->
        <el-dialog title="添加" :visible.sync="addVisible" width="30%">
            <el-form ref="form" :model="form" label-width="70px">
                <el-form-item label="航班号">
                    <el-input v-model="form.flight_id"></el-input>
                </el-form-item>
                <el-form-item label="航空公司">
                    <el-input v-model="form.flight_company"></el-input>
                </el-form-item>
                <el-form-item label="起飞时间">
                    <div class="block">
                        <el-date-picker
                                v-model="form.new_start_time"
                                type="datetime"
                                format="yyyy-MM-dd HH:mm:ss"
                                value-format
                                placeholder="选择日期时间">
                        </el-date-picker>
                    </div>
                </el-form-item>
                <el-form-item label="降落时间">
                    <div class="block">
                        <el-date-picker
                                v-model="form.new_end_time"
                                type="datetime"
                                format="yyyy-MM-dd HH:mm:ss"
                                value-format
                                placeholder="选择日期时间">
                        </el-date-picker>
                    </div>
                </el-form-item>
                <el-form-item label="起飞地点">
                    <el-input v-model="form.start_address"></el-input>
                </el-form-item>
                <el-form-item label="降落地点">
                    <el-input v-model="form.end_address"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveAdd">确 定</el-button>
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
                    pageSize: 10,
                    total: 0
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
            // 获取 easy-mock 的模拟数据
            getData() {
                this.axios.get(this.GLOBAL.flight_get).then((res)=>{
                    this.tableData = res.data;
                    for(let index in this.tableData){
                        this.tableData[index].new_start_time = this.myFormatDate(this.tableData[index].start_time);
                        this.tableData[index].new_end_time = this.myFormatDate(this.tableData[index].end_time);
                    }
                    console.log(this.tableData);
                    this.query.total = this.tableData.length;
                    this.sliceList();
                    this.tableDataCopy = clone(this.tableData);
                });
            },
            // 触发搜索按钮
            handleSearch() {
                this.$set(this.query, 'pageIndex', 1);
                this.tableData = []
                for(let i in this.tableDataCopy){
                    // console.log(this.query.name);
                    // console.log(this.tableDataCopy[i].name);
                    if(this.tableDataCopy[i].flight_id.indexOf(this.query.flight_id) != -1){
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
            // 触发添加按钮
            handleAdd(){
                this.addVisible = true;
            },
            // 删除操作
            handleDelete(index, row) {
                // 二次确认删除
                this.$confirm('确定要删除吗？', '提示', {
                    type: 'warning'
                })
                    .then(() => {
                        this.tableData.splice((this.query.pageIndex-1)*this.query.pageSize + index, 1);
                        this.sliceList();
                        let deleteData = this.$qs.stringify({
                            flight_id: row.flight_id,
                        });
                        this.axios.delete(this.GLOBAL.flight_delete,
                            {
                                data: deleteData,
                                headers: {
                                    'Content-Type': 'application/x-www-form-urlencoded'
                                }
                            }).catch((res)=>{});
                    })
                    .catch(() => {});
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

                let patchData = this.$qs.stringify({
                    flight_id: this.form.flight_id,
                    flight_company: this.form.flight_company,
                    start_time: getTimestamp(this.form.new_start_time),
                    end_time: getTimestamp(this.form.new_end_time),
                    start_address: this.form.start_address,
                    end_address: this.form.end_address
                });
                this.axios.patch(this.GLOBAL.flight_update, patchData).then((res)=>{
                    console.log(res);
                }).catch((res)=>{
                    console.log(res);
                });
                let mydata = new Date(this.form.new_start_time)
                console.log(mydata.getFullYear())
                if(typeof(this.form.new_start_time) === "object"){
                    this.form.new_start_time = this.myFormatDate(this.form.new_start_time);
                }
                if(typeof(this.form.new_end_time) === "object"){
                    this.form.new_end_time = this.myFormatDate(this.form.new_end_time);
                }
                this.$set(this.tableData, this.idx, this.form);
                this.form = {};
            },
            // 保存添加
            async saveAdd(){
                this.addVisible = false;
                this.$message.success('添加成功');
                let postData = this.$qs.stringify({
                    flight_id: this.form.flight_id,
                    flight_company: this.form.flight_company,
                    start_time: getTimestamp(this.form.new_start_time),
                    end_time: getTimestamp(this.form.new_end_time),
                    start_address: this.form.start_address,
                    end_address: this.form.end_address
                });
                await this.axios.post(this.GLOBAL.flight_add, postData).then((res)=>{
                    console.log(res);
                }).catch((res)=>{
                    console.log(res);
                });
                this.form = {};
                this.getData();
            },
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
