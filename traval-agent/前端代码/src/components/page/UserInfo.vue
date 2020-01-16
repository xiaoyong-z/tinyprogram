<template>
    <div>
    <el-row class = "user_info">
        <img src="../../assets/img/img.jpg" class="user_img"/>        
    </el-row>
    <el-row :gutter="20">
            <div class="user_info">
                <h2 class="user_h2">个人信息 </h2>
                <div class="user_info_form" v-if="!editable">
                    <el-form label-position="right" label-width="100px" :model="personalInfo" >
                        <el-form-item label="昵称">
                            <el-input v-model="personalInfo.account" disabled></el-input>
                        </el-form-item>
                        <el-form-item label="真实姓名">
                            <el-input v-model="personalInfo.name" disabled></el-input>
                        </el-form-item>
                        <el-form-item label="年龄">
                            <el-input v-model="personalInfo.age" disabled></el-input>
                        </el-form-item>
                        <el-form-item label="性别">
                             <el-input v-model="personalInfo.gender" disabled></el-input>
                            <!-- <el-radio-group v-model="personalInfo.gender">
                                <el-radio label="男">男</el-radio>
                                <el-radio label="女">女</el-radio>
                            </el-radio-group> -->
                        </el-form-item>
                        <el-form-item label="地址" >
                            <el-input v-model="personalInfo.address" disabled></el-input>
                        </el-form-item>
                        <el-form-item label="身份证号" >
                            <el-input v-model="personalInfo.id_number" disabled></el-input>
                        </el-form-item>
                        <el-form-item label="电话号码" >
                            <el-input v-model="personalInfo.phone_number" disabled></el-input>
                        </el-form-item>
                        <el-button type="primary" v-if="!editable" @click="editable = true">编辑</el-button>
                    </el-form>
                </div>

                <div class="user_info_text" v-else>
                    <el-form label-position="right" label-width="100px" :model="personalInfo">

                        <el-form-item label="昵称" disabled>
                            <el-input v-model="personalInfo.account"></el-input>
                        </el-form-item>
                        <el-form-item label="真实姓名">
                            <el-input v-model="personalInfo.name"></el-input>
                        </el-form-item>
                        <el-form-item label="年龄">
                            <el-input v-model="personalInfo.age"></el-input>
                        </el-form-item>
                        <el-form-item label="性别" align="left">
                            <el-radio-group v-model="personalInfo.gender">
                                <el-radio label="男">男</el-radio>
                                <el-radio label="女">女</el-radio>
                            </el-radio-group>
                        </el-form-item>
                        <el-form-item label="地址" >
                            <el-input v-model="personalInfo.address" ></el-input>
                        </el-form-item>
                        <el-form-item label="身份证号" >
                            <el-input v-model="personalInfo.id_number" ></el-input>
                        </el-form-item>
                        <el-form-item label="电话号码" >
                            <el-input v-model="personalInfo.phone_number" ></el-input>
                        </el-form-item>

                        <el-form-item>
                            <el-button type="primary" @click="submitForm()">提交</el-button>
                            <el-button @click="resetForm()">取消</el-button>
                        </el-form-item>


                    </el-form>
                </div>
            </div>        
    </el-row>
    </div>
</template>

<script>
    export default{
        name:"qq",
        data(){
            return{
                editable : false,
                personalInfo: {},
            }
        },
        created(){
            this.getUserInfo();
        },
        methods:{
            getUserInfo(){
                this.personalInfo = JSON.parse(localStorage.getItem("user_info"));
                this.personalInfo.gender = this.personalInfo.gender ? "男" : "女";
                console.log(this.personalInfo);
            },
            submitForm() {
                this.editable = false;
                let patchData = this.$qs.stringify({
                    salesman_id: this.personalInfo.salesman_id,
                    id_number: this.personalInfo.id_number,
                    name: this.personalInfo.name,
                    phone_number: this.personalInfo.phone_number,
                    address: this.personalInfo.address,
                    age: this.personalInfo.age,
                    gender: this.personalInfo.gender === '男'?1:0,
                    account: this.personalInfo.account,
                    password: this.personalInfo.password,
                    company_id: this.personalInfo.company_id,
                    is_manager: this.personalInfo.is_manager === '是'?1:0,
                });
                this.axios.patch(this.GLOBAL.salesman_update, patchData).then((res)=>{
                    console.log(res);
                    localStorage.setItem("user_info", patchData);
                    this.$message.success("修改成功");
                }).catch((res)=>{
                    console.log(res);
                });
            },
            //重置表单
            resetForm() {
                this.editable = false;
                this.personalInfo = JSON.parse(localStorage.getItem("user_info"));
                this.personalInfo.gender = this.personalInfo.gender ? "男" : "女";
            },
        }
    }
</script>

<style scoped>
    .user_info{
        /*width: 80%;*/
        padding: 20px;
        text-align: center;
        border: 1px solid #dee1e2;
    }
    .user_img{
        max-width: 300px;
        max-height: 300px;
        border-radius: 50%;
    }
    .user_h2{
        margin: 0;
        font-weight: normal;
        padding: 10px 20px;
        border-bottom: 1px solid #dee1e2;
    }

    .user_h2_edit{
        font-size: 24px;
        color: #7ab8ed;
    }

    .user_info_form,.user_info_text{
        padding-top: 3%;
        padding-left: 30%;
        padding-right: 30%;
    }
</style>