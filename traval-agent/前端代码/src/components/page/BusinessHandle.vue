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
                <el-date-picker
                        v-model="filter_time"
                        type="datetimerange"
                        range-separator="至"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期"
                >
                </el-date-picker>

                <el-button
                    type="primary"
                    icon="el-icon-search"
                    @click="handleSearch"
                    style="float:right"
                >搜索</el-button>
                <el-input v-model="query.name" placeholder="活动名" class="handle-input mr10"></el-input>
            </div>
            <el-table
                :data="currentList"
                border
                class="table"
                ref="multipleTable"
                header-cell-class-name="table-header"
            >
                <el-table-column prop="name" label="活动名" align="center"></el-table-column>
                <el-table-column prop="adult_price" label="成人价格" align="center" sortable></el-table-column>
                <el-table-column prop="child_price" label="儿童价格" align="center" sortable></el-table-column>
                <el-table-column prop="route_name" label="路线名" align="center"></el-table-column>
                <el-table-column prop="days" label="活动时长" align="center"></el-table-column>
                <el-table-column prop="new_start_time" label="启程时间" align="center" sortable></el-table-column>
                <el-table-column prop="new_end_time" label="返程时间" align="center" sortable></el-table-column>

                <el-table-column label="操作" width="180" align="center">
                    <template slot-scope="scope">
                        <el-button
                            type="text"
                            icon="el-icon-plus"
                            @click="handleEdit(scope.$index, scope.row)"
                        >详情</el-button>
                        <el-button
                            type="text"
                            icon="el-icon-tickets"
                            @click="handleRegister(scope.$index, scope.row)"
                        >登记</el-button>
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

        <el-dialog title="登记" @close="()=>this.active=0" :visible.sync="registerVisible" width="30%" align="center">
            <el-steps :active="active" simple>
                <el-step
                    :title="item.title"
                    :icon="item.icon"
                    @click.native="stepClick(item.index)"
                    v-for="item in stepData"
                    :key="item.index"
                ></el-step>
            </el-steps>
            <el-row v-show="active === 0">
                <el-form class="active" ref="form" :model="form" label-width="70px">
                    <el-form-item label="旅客选择">
                        <el-select v-model="form.traveller_id" placeholder="请选择">
                            <el-option
                                    v-for="item in travellerOptions"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="活动选择">
                        <el-select v-model="form.activity_id" placeholder="请选择">
                            <el-option
                                    v-for="item in activityOptions"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-form>
            </el-row>
            <el-row v-show="active === 1">
                <el-form class="active" ref="registerForm" :model="registerForm" label-width="70px" disabled>
                    <el-form-item label="旅客名">
                        <el-input v-model="registerForm.traveller_name"></el-input>
                    </el-form-item>
                    <el-form-item label="身份证号">
                        <el-input v-model="registerForm.traveller_id_number"></el-input>
                    </el-form-item>
                    <el-form-item label="性别">
                        <el-input v-model="registerForm.gender"></el-input>
                    </el-form-item>
                    <el-form-item label="活动名">
                        <el-input v-model="registerForm.activity_name"></el-input>
                    </el-form-item>
                    <el-form-item label="活动描述">
                        <el-input v-model="registerForm.activity_description"></el-input>
                    </el-form-item>
<!--                    <img src="../../assets/img/zhifubao.jpg" /> 可选属性-->
                </el-form>
            </el-row>

            <el-row v-show="active === 2">
                <el-form class="active" ref="billForm" :model="billForm" label-width="70px" disabled>
                    <el-form-item label="旅客名">
                        <el-input v-model="this.registerForm.traveller_name"></el-input>
                    </el-form-item>
                    <el-form-item label="活动名">
                        <el-input v-model="this.registerForm.activity_name"></el-input>
                    </el-form-item>
                    <el-form-item label="业务员名">
                        <el-input v-model="this.billForm.salesman_name"></el-input>
                    </el-form-item>
                    <el-form-item label="付款日期">
                        <el-input v-model="this.billForm.pay_date"></el-input>
                    </el-form-item>
                    <el-form-item label="付款金额">
                        <el-input v-model="this.billForm.pay_money"></el-input>
                    </el-form-item>
                    <!--                    <img src="../../assets/img/zhifubao.jpg" /> 可选属性-->
                </el-form>
            </el-row>

<!--            <el-button plain size="medium" @click="prevClick()" v-show="active != 0">{{prevTile}}</el-button>-->
            <el-button type="primary" plain size="medium" @click="submitRegister()" v-show="active === 0">登记</el-button>
            <el-button type="primary" plain size="medium" @click="pay()" v-show="active === 1">付款</el-button>
            <el-button type="primary" plain size="medium" @click="finish()" v-show="active === 2">完成</el-button>
        </el-dialog>

        <!-- 详情弹出框 -->
        <el-dialog title="详情" :visible.sync="editVisible" width="30%">
            <el-form ref="form" :model="form" label-width="70px" disabled>
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
                <el-form-item label="出发地">
                    <el-input v-model="form.start_address"></el-input>
                </el-form-item>
                <el-form-item label="目的地">
                    <el-input v-model="form.end_address"></el-input>
                </el-form-item>
                <el-form-item label="出发航班">
                    <el-input v-model="form.start_flight_id"></el-input>
                </el-form-item>
                <el-form-item label="返程航班">
                    <el-input v-model="form.end_flight_id"></el-input>
                </el-form-item>
                <el-form-item label="沿途美食">
                    <el-input v-model="form.foods"></el-input>
                </el-form-item>
                <el-form-item label="沿途风景">
                    <el-input v-model="form.scenics"></el-input>
                </el-form-item>
                <el-form-item label="沿途酒店">
                    <el-input v-model="form.hotels"></el-input>
                </el-form-item>
            </el-form>
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
            filter_time:{},
            tableData: [],
            tableDataCopy: [],
            multipleSelection: [],
            delList: [],
            editVisible: false,
            addVisible: false,
            registerVisible: false,
            pageTotal: 0,
            form: {},
            idx: -1,
            id: -1,

            companyData: [],
            companyDist: {},
            companyOptions: [],

            routeData: [],
            routeDist: {},

            active: 0, // 控制步骤条及填写条目的显示
            stepData: [
                { index: 0, title: '登记', icon: 'el-icon-edit' },
                { index: 1, title: '付款', icon: 'el-icon-money' },
                { index: 2, title: '完成', icon: 'el-icon-check' }
            ],
            nextTile: '下一步',
            prevTile: '上一步',

            travellerData: [],
            travellerDist: {},
            travellerOptions: [],

            activityData: [],
            activityDist: {},
            activityOptions: [],

            registerForm: {},

            billForm:{},
            
            currentList: []
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

        finish(){
            this.registerVisible = false;
            this.active = 0;
        },

        async getDataHelp() {
            await this.axios.get(this.GLOBAL.route_get).then(res => {
                // console.log(res.data);
                console.log(res.data);
                this.routeData = res.data;
                for (let index in this.routeData) {
                    this.routeDist[this.routeData[index].base_route.route_id] = this.routeData[index];
                }
                // console.log(this.routeDist);
            });

            await this.axios.get(this.GLOBAL.company_get).then(res => {
                this.companyData = res.data;
                for (let index in this.companyData) {
                    this.companyDist[this.companyData[index].company_id] = this.companyData[index].name;
                    this.companyOptions.push({ value: this.companyData[index].company_id, label: this.companyData[index].name });
                }
            });

            await this.axios.get(this.GLOBAL.traveller_get).then(res => {
                this.travellerData = res.data;
                for (let index in this.travellerData) {
                    this.travellerDist[this.travellerData[index].traveller_id] = this.travellerData[index].name;
                    this.travellerOptions.push({ value: this.travellerData[index].traveller_id, label: this.travellerData[index].name });
                }
            });
        },
        // 获取 easy-mock 的模拟数据
        async getData() {
            await this.getDataHelp();
            await this.axios.get(this.GLOBAL.activity_get).then(res => {
                let user_company = JSON.parse(localStorage.getItem('user_info')).company_id;
                this.tableData = [];
                for (let index in res.data) {
                    if (res.data[index].company_id === user_company) {
                         
                        res.data[index].company_name = this.companyDist[res.data[index].company_id];
                        res.data[index].route_name = this.routeDist[res.data[index].route_id].base_route.name;
                        res.data[index].end_address = this.routeDist[res.data[index].route_id].base_route.end_address;
                        res.data[index].start_address = this.routeDist[res.data[index].route_id].base_route.start_address;
                        res.data[index].start_flight_id = this.routeDist[res.data[index].route_id].start_flight_id;
                        res.data[index].end_flight_id = this.routeDist[res.data[index].route_id].end_flight_id;
                        res.data[index].foods = [];
                        res.data[index].hotels = [];
                        res.data[index].scenics = [];
                        for (let j in this.routeDist[res.data[index].route_id].route_food) {
                            res.data[index].foods.push(this.routeDist[res.data[index].route_id].route_food[j].name);
                        }
                        for (let j in this.routeDist[res.data[index].route_id].route_hotel) {
                            res.data[index].hotels.push(this.routeDist[res.data[index].route_id].route_hotel[j].name);
                        }
                        for (let j in this.routeDist[res.data[index].route_id].route_scenic) {
                            res.data[index].scenics.push(this.routeDist[res.data[index].route_id].route_scenic[j].name);
                        }
                        res.data[index].new_start_time = this.myFormatDate(res.data[index].start_time);
                        res.data[index].new_end_time = this.myFormatDate(res.data[index].end_time);
                        this.tableData.push(res.data[index]);
                    }
                }
                
                for (let index in this.tableData) {
                    this.activityDist[this.tableData[index].activity_id] = this.tableData[index].name;
                    this.activityOptions.push({ value: this.tableData[index].activity_id, label: this.tableData[index].name });
                }
                
                this.query.total = this.tableData.length;
                this.sliceList();
                this.tableDataCopy = clone(this.tableData);
            });
        },
        // 触发搜索按钮
        handleSearch() {
            console.log(this.filter_time);
            this.$set(this.query, 'pageIndex', 1);
            this.tableData = [];
            for (let i in this.tableDataCopy) {
                // console.log(this.query.name);
                // console.log(this.tableDataCopy[i].name);
                if (this.tableDataCopy[i].name.indexOf(this.query.name) != -1) {
                    if(Array.isArray(this.filter_time)) {
                        if(this.filter_time[0].getTime() < new Date(this.tableDataCopy[i].start_time).getTime()) {
                            if(this.filter_time[1].getTime() > new Date(this.tableDataCopy[i].end_time).getTime()) {
                                this.tableData.push(this.tableDataCopy[i]);
                            }
                        }
                    }else{
                        this.tableData.push(this.tableDataCopy[i]);
                    }
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
        submitRegister(){
            let postData = this.$qs.stringify({
                traveller_id: this.form.traveller_id,
                activity_id: this.form.activity_id,
                register_date: new Date().getTime(),
            });
            this.axios
                .post(this.GLOBAL.register_add, postData)
                .then(res => {
                    this.registerForm = res.data;
                    this.registerForm.gender = this.registerForm.gender === 1?"男":"女";
                })
                .catch(res => {
                    console.log(res);});
            if (this.active < 2) {
                this.active++;
            }
        },
        pay() {
            let traveller_age = 0;
            let pay_money = 0;
            let adult_price = 0;
            let child_price = 0;
            for(let index in this.travellerData) {
                if (this.travellerData[index].traveller_id === this.form.traveller_id) {
                    traveller_age = this.travellerData[index].age;
                }
            }
            for(let index in this.tableDataCopy){
                if (this.tableDataCopy[index].activity_id === this.form.activity_id) {
                    adult_price = this.tableDataCopy[index].adult_price;
                    child_price = this.tableDataCopy[index].child_price;
                }
            }
            if(traveller_age < 15){
                pay_money = child_price
            }else{
                pay_money = adult_price;
            }
            let salesman_id = JSON.parse(localStorage.getItem('user_info')).salesman_id;
            let salesman_name = JSON.parse(localStorage.getItem('user_info')).name;
            console.log(JSON.parse(localStorage.getItem('user_info')));
            let postData = this.$qs.stringify({
                traveller_id: this.form.traveller_id,
                activity_id: this.form.activity_id,
                salesman_id: salesman_id,
                pay_money: pay_money,
                pay_date:new Date().getTime(),
            });
            this.axios.post(this.GLOBAL.bill_add, postData)
                .then(res => {
                    console.log(res);
                })
                .catch(res => {
                    console.log(res);
                });

            if (this.active < 2) {
                this.active++;
            }
            this.billForm.salesman_name = salesman_name;
            this.billForm.pay_money = pay_money;
            this.billForm.pay_date = this.myFormatDate(new Date());
        },
        prevClick() {
            if (this.active > 0) {
                this.active--;
            }
        },
        handleEdit(index, row) {
            this.idx = index;
            this.form = clone(row);
            this.editVisible = true;
        },
        handleRegister(index, row) {
            this.idx = index;
            this.form = clone(row);
            this.registerVisible = true;
        },
        // 保存编辑
        saveEdit() {
            this.editVisible = false;
            this.$message.success(`修改第 ${this.idx + 1} 行成功`);
            this.form.company_name = this.companyDist[this.form.company_id];
            this.form.route_name = this.routeDist[this.form.route_id];
            this.$set(this.tableData, this.idx, this.form);

            let patchData = this.$qs.stringify({
                activity_id: this.form.activity_id,
                name: this.form.name,
                adult_price: this.form.adult_price,
                child_price: this.form.child_price,
                description: this.form.description,
                route_id: this.form.route_id,
                company_id: this.form.company_id
            });
            this.axios.patch(this.GLOBAL.activity_update, patchData)
                .then(res => {
                    console.log(res);
                })
                .catch(res => {
                    console.log(res);
                });
            this.form = {};
        },
    }
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
.active{
    padding-top: 25px;
}

</style>