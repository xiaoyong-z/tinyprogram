<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 活动管理
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button type="primary" icon="el-icon-plus" @click="handleAdd">添加</el-button>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch" style="float:right">搜索</el-button>
                <el-input v-model="query.name" placeholder="活动名" class="handle-input mr10"></el-input>
            </div>
            <el-table
                    :data="currentList"
                    border
                    class="table"
                    ref="multipleTable"
                    header-cell-class-name="table-header"
                    @selection-change="handleSelectionChange"
            >
                <el-table-column prop="name" label="活动名" align="center"></el-table-column>
                <el-table-column prop="adult_price" label="成人价格" align="center"></el-table-column>
                <el-table-column prop="child_price" label="儿童价格" align="center"></el-table-column>
                <el-table-column prop="description" label="活动描述" align="center" width="300"></el-table-column>
                <el-table-column prop="route_name" label="路线名" align="center"></el-table-column>
                <el-table-column prop="company_name" label="公司名" align="center"></el-table-column>
                <el-table-column prop="days" label="活动时长" align="center" width="50"></el-table-column>
                <el-table-column prop="new_start_time" label="启程时间" align="center"></el-table-column>
                <el-table-column prop="new_end_time" label="返程时间" align="center"></el-table-column>


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
                <el-form-item label="活动名">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="成人价格">
                    <el-input v-model="form.adult_price"></el-input>
                </el-form-item>
                <el-form-item label="儿童价格">
                    <el-input v-model="form.child_price"></el-input>
                </el-form-item>
                <el-form-item label="活动描述">
                    <el-input v-model="form.description"></el-input>
                </el-form-item>
                <el-form-item label="所属公司">
                    <el-select v-model="form.company_id" placeholder="请选择">
                        <el-option
                                v-for="item in companyOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="活动路线">
                    <el-select v-model="form.route_id" placeholder="请选择">
                        <el-option
                                v-for="item in routeOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="启程时间">
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
                <el-form-item label="返程时间">
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
                <el-form-item label="活动天数">
                    <el-input v-model="form.days"></el-input>
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
                <el-form-item label="活动名">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="成人价格">
                    <el-input v-model="form.adult_price"></el-input>
                </el-form-item>
                <el-form-item label="儿童价格">
                    <el-input v-model="form.child_price"></el-input>
                </el-form-item>
                <el-form-item label="活动描述">
                    <el-input v-model="form.description"></el-input>
                </el-form-item>
                <el-form-item label="所属公司">
                    <el-select v-model="form.company_id" placeholder="请选择">
                        <el-option
                                v-for="item in companyOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="活动路线">
                    <el-select v-model="form.route_id" placeholder="请选择">
                        <el-option
                                v-for="item in routeOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="启程时间">
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
                <el-form-item label="返程时间">
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
                <el-form-item label="活动天数">
                    <el-input v-model="form.days"></el-input>
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
                    pageSize: 8,
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
                
                companyData: [],
                companyDist: {},
                companyOptions: [],

                routeData: [],
                routeDist: {},
                routeOptions: [],

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
                await this.axios.get(this.GLOBAL.route_get2).then((res)=>{
                    this.routeData = res.data;
                    for(let index in this.routeData){
                        this.routeDist[this.routeData[index].route_id] = this.routeData[index].name;
                        this.routeOptions.push({value:this.routeData[index].route_id,label:this.routeData[index].name})
                    }
                });

                await this.axios.get(this.GLOBAL.company_get).then((res)=>{
                    this.companyData = res.data;
                    for(let index in this.companyData){
                        this.companyDist[this.companyData[index].company_id] = this.companyData[index].name;
                        this.companyOptions.push({value:this.companyData[index].company_id,label:this.companyData[index].name})
                    }
                });
            },
            // 获取 easy-mock 的模拟数据
            async getData() {
                await this.getDataHelp();
                await this.axios.get(this.GLOBAL.activity_get).then((res)=>{
                    this.tableData = res.data;
                    for(let index in this.tableData){
                        this.tableData[index].new_start_time = this.myFormatDate(this.tableData[index].start_time);
                        this.tableData[index].new_end_time = this.myFormatDate(this.tableData[index].end_time);
                        this.tableData[index].company_name = this.companyDist[this.tableData[index].company_id];
                        this.tableData[index].route_name = this.routeDist[this.tableData[index].route_id];
                    }
                    this.query.total = this.tableData.length;
                    this.sliceList();
                    this.tableDataCopy = clone(this.tableData);
                });

            },
            // 触发搜索按钮
            handleSearch() {
                this.$set(this.query, 'pageIndex', 1);
                this.tableData = [];
                for(let i in this.tableDataCopy){
                    // console.log(this.query.name);
                    // console.log(this.tableDataCopy[i].name);
                    if(this.tableDataCopy[i].name.indexOf(this.query.name) != -1){
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
                            activity_id: row.activity_id,
                        });
                        this.axios.delete(this.GLOBAL.activity_delete,
                            // {traveller_id: row.traveller_id}
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
                this.form.company_name = this.companyDist[this.form.company_id];
                this.form.route_name = this.routeDist[this.form.route_id];

                let patchData = this.$qs.stringify({
                    activity_id: this.form.activity_id,
                    name: this.form.name,
                    adult_price: this.form.adult_price,
                    child_price: this.form.child_price,
                    start_time: getTimestamp(this.form.new_start_time),
                    end_time: getTimestamp(this.form.new_end_time),
                    days: this.form.days,
                    description: this.form.description,
                    route_id: this.form.route_id,
                    company_id: this.form.company_id,
                });
                this.axios.patch(this.GLOBAL.activity_update, patchData).then((res)=>{
                    console.log(res);
                }).catch((res)=>{
                    console.log(res);
                });
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
                    name: this.form.name,
                    adult_price: this.form.adult_price,
                    child_price: this.form.child_price,
                    start_time: getTimestamp(this.form.new_start_time),
                    end_time: getTimestamp(this.form.new_end_time),
                    days: this.form.days,
                    description: this.form.description,
                    route_id: this.form.route_id,
                    company_id: this.form.company_id,
                });
                await this.axios.post(this.GLOBAL.activity_add, postData).then((res)=>{
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
