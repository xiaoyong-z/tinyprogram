<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 路线管理
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button type="primary" icon="el-icon-plus" @click="handleAdd">添加</el-button>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch" style="float:right">搜索</el-button>
                <el-input v-model="query.name" placeholder="景点名" class="handle-input mr10"></el-input>
            </div>
            <el-table
                    :data="currentList"
                    border
                    class="table"
                    ref="multipleTable"
                    header-cell-class-name="table-header"
                    @selection-change="handleSelectionChange"
            >
                <el-table-column prop="name" label="路线名" align="center"></el-table-column>
                <el-table-column prop="start_address" label="出发地" align="center" width="250px"></el-table-column>
                <el-table-column prop="end_address" label="目的地" align="center"></el-table-column>
                <el-table-column prop="start_flight_id" label="启程航班" align="center"></el-table-column>
                <el-table-column prop="end_flight_id" label="返程航班" align="center"></el-table-column>

                <el-table-column label="操作" width="180" align="center">
                    <template slot-scope="scope">
                        <el-button
                                type="text"
                                icon="el-icon-more"
                                @click="handleDetail(scope.$index, scope.row)"
                        >详情</el-button>
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
        <!-- 详情弹出框 -->
        <el-dialog title="详情" :visible.sync="detailVisible" width="35%">
            <el-form ref="form" :model="form" label-width="100px" :disabled="true">
                <el-form-item label="路线名">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="路线起点">
                    <el-input v-model="form.start_address"></el-input>
                </el-form-item>
                <el-form-item label="路线终点">
                    <el-input v-model="form.end_address"></el-input>
                </el-form-item>
                <el-form-item label="启程航班">
                    <el-select v-model="form.start_flight_id" placeholder="请选择">
                        <el-option
                                v-for="item in FlightOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="返程航班">
                    <el-select v-model="form.end_flight_id" placeholder="请选择">
                        <el-option
                                v-for="item in FlightOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="酒店">
                    <el-select v-model="form.hotels" placeholder="请选择" multiple>
                        <el-option
                                v-for="item in HotelOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="美食">
                    <el-select v-model="form.foods" placeholder="请选择" multiple>
                        <el-option
                                v-for="item in FoodOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="景色">
                    <el-select v-model="form.scenics" placeholder="请选择" multiple>
                        <el-option
                                v-for="item in ScenicOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
        </el-dialog>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="30%">
            <el-form ref="form" :model="form" label-width="70px">
                <el-form-item label="路线名">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="路线起点">
                    <el-input v-model="form.start_address"></el-input>
                </el-form-item>
                <el-form-item label="路线终点">
                    <el-input v-model="form.end_address"></el-input>
                </el-form-item>
                <el-form-item label="启程航班">
                    <el-select v-model="form.start_flight_id" placeholder="请选择">
                        <el-option
                                v-for="item in FlightOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="返程航班">
                    <el-select v-model="form.end_flight_id" placeholder="请选择">
                        <el-option
                                v-for="item in FlightOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="酒店">
                    <el-select v-model="form.hotels" placeholder="请选择" multiple>
                        <el-option
                                v-for="item in HotelOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="美食">
                    <el-select v-model="form.foods" placeholder="请选择" multiple>
                        <el-option
                                v-for="item in FoodOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="景色">
                    <el-select v-model="form.scenics" placeholder="请选择" multiple>
                        <el-option
                                v-for="item in ScenicOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
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
            <el-form-item label="路线名">
                <el-input v-model="form.name"></el-input>
            </el-form-item>
            <el-form-item label="路线起点">
                <el-input v-model="form.start_address"></el-input>
            </el-form-item>
            <el-form-item label="路线终点">
                <el-input v-model="form.end_address"></el-input>
            </el-form-item>
            <el-form-item label="启程航班">
                <el-select v-model="form.start_flight_id" placeholder="请选择">
                    <el-option
                            v-for="item in FlightOptions"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="返程航班">
                <el-select v-model="form.end_flight_id" placeholder="请选择">
                    <el-option
                            v-for="item in FlightOptions"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="酒店">
                <el-select v-model="form.hotels" placeholder="请选择" multiple>
                    <el-option
                            v-for="item in HotelOptions"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="美食">
                <el-select v-model="form.foods" placeholder="请选择" multiple>
                    <el-option
                            v-for="item in FoodOptions"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="景色">
                <el-select v-model="form.scenics" placeholder="请选择" multiple>
                    <el-option
                            v-for="item in ScenicOptions"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                    </el-option>
                </el-select>
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
                detailVisible: false,
                pageTotal: 0,
                form: {},
                raw_form: {},
                idx: -1,
                id: -1,

                FoodData: [],
                FoodDist: {},
                FoodOptions: [],

                HotelData: [],
                HotelDist: {},
                HotelOptions: [],

                FlightData: [],
                FlightDist: {},
                FlightOptions: [],

                ScenicData: [],
                ScenicDist: {},
                ScenicOptions: [],

                currentList: []
            };
        },
        created() {
            this.getData();
        },
        methods: {
            subSet(arr1, arr2){
                var set1 = new Set(arr1);
                var set2 = new Set(arr2);

                var subset = [];

                for (let item of set1) {
                    if (!set2.has(item)) {
                        subset.push(item);
                    }
                }
                return subset;
            },
            async getDataHelp(){
                await this.axios.get(this.GLOBAL.food_get).then((res)=>{
                    this.FoodData = res.data;
                    for(let index in this.FoodData){
                        this.FoodDist[this.FoodData[index].food_id] = this.FoodData[index].name;
                        this.FoodOptions.push({value:this.FoodData[index].food_id,label:this.FoodData[index].name})
                    }
                });

                await this.axios.get(this.GLOBAL.hotel_get).then((res)=>{
                    this.HotelData = res.data;
                    for(let index in this.HotelData){
                        this.HotelDist[this.HotelData[index].hotel_id] = this.HotelData[index].name;
                        this.HotelOptions.push({value:this.HotelData[index].hotel_id,label:this.HotelData[index].name})
                    }
                });

                await this.axios.get(this.GLOBAL.flight_get).then((res)=>{
                    this.FlightData = res.data;
                    for(let index in this.FlightData){
                        this.FlightDist[this.FlightData[index].flight_id] = this.FlightData[index].name;
                        this.FlightOptions.push({value:this.FlightData[index].flight_id,label:this.FlightData[index].flight_id})
                    }
                });

                await this.axios.get(this.GLOBAL.scenic_get).then((res)=>{
                    this.ScenicData = res.data;
                    for(let index in this.ScenicData){
                        this.ScenicDist[this.ScenicData[index].scenic_id] = this.ScenicData[index].name;
                        this.ScenicOptions.push({value:this.ScenicData[index].scenic_id,label:this.ScenicData[index].name})
                    }
                });
            },

            async getData() {
                await this.getDataHelp();
                this.axios.get(this.GLOBAL.route_get).then((res)=>{
                    console.log(res.data);

                    for(let i in res.data){
                        let temp_row = {};
                        temp_row.route_id= res.data[i].base_route.route_id;
                        temp_row.name = res.data[i].base_route.name;
                        temp_row.start_address = res.data[i].base_route.start_address;
                        temp_row.end_address = res.data[i].base_route.end_address;
                        temp_row.start_flight_id = res.data[i].start_flight_id;
                        temp_row.end_flight_id = res.data[i].end_flight_id;
                        temp_row.foods = [];
                        temp_row.hotels = [];
                        temp_row.scenics = [];
                        for(let j in res.data[i].route_food){
                            temp_row.foods.push(res.data[i].route_food[j].food_id);
                        }
                        for(let j in res.data[i].route_hotel){
                            temp_row.hotels.push(res.data[i].route_hotel[j].hotel_id);
                        }
                        for(let j in res.data[i].route_scenic){
                            temp_row.scenics.push(res.data[i].route_scenic[j].scenic_id);
                        }
                        // console.log(this.query.name);
                        // console.log(this.tableDataCopy[i].name);
                        this.tableData.push(temp_row);
                    }
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
            handleDetail(index, row){
                this.form = clone(row);
                this.detailVisible = true;
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
                            route_id: row.route_id,
                        });
                        this.axios.delete(this.GLOBAL.route_delete,
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
                this.raw_form = clone(row);
                this.editVisible = true;
            },

            // 保存编辑
            saveEdit() {
                this.editVisible = false;
                this.$message.success(`修改第 ${this.idx + 1} 行成功`);
                this.$set(this.tableData, this.idx, this.form);
                let start_flight_change = true;
                let end_flight_change = true;
                if(this.raw_form.start_flight_id === this.form.start_flight_id){
                    start_flight_change = false;
                    console.log("start not change")
                }
                if(this.raw_form.end_flight_id === this.form.end_flight_id){
                    end_flight_change = false;
                    console.log("end not change")
                }
                let patchData = this.$qs.stringify({
                    route_id: this.form.route_id,
                    name: this.form.name,
                    start_address: this.form.start_address,
                    end_address: this.form.end_address,
                    start_flight_id: this.form.start_flight_id,
                    end_flight_id: this.form.end_flight_id,
                    start_flight_change: start_flight_change,
                    end_flight_change: end_flight_change,
                    delete_foods: JSON.stringify(this.subSet(this.raw_form.foods, this.form.foods)),
                    delete_hotels: JSON.stringify(this.subSet(this.raw_form.hotels, this.form.hotels)),
                    delete_scenics: JSON.stringify(this.subSet(this.raw_form.scenics, this.form.scenics)),
                    add_foods: JSON.stringify(this.subSet(this.form.foods, this.raw_form.foods)),
                    add_hotels: JSON.stringify(this.subSet(this.form.hotels, this.raw_form.hotels)),
                    add_scenics: JSON.stringify(this.subSet(this.form.scenics, this.raw_form.scenics))
                });
                this.axios.patch(this.GLOBAL.route_update, patchData).then((res)=>{
                    console.log(res);
                }).catch((res)=>{
                    console.log(res);
                });
                this.form = {};
            },
            // 保存添加
            async saveAdd(){
                console.log(this.form);
                this.addVisible = false;
                this.$message.success('添加成功');
                let postData = this.$qs.stringify({
                    name: this.form.name,
                    start_address: this.form.start_address,
                    end_address: this.form.end_address,
                    start_flight_id: this.form.start_flight_id,
                    end_flight_id: this.form.end_flight_id,
                    foods: JSON.stringify(this.form.foods),
                    hotels: JSON.stringify(this.form.hotels),
                    scenics: JSON.stringify(this.form.scenics)
                });
                await this.axios.post(this.GLOBAL.route_add, postData).then((res)=>{
                    console.log(res);
                }).catch((res)=>{
                    console.log(res);
                });
                this.form = {};
                this.tableData = [];
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
