<template>
    <div class="sidebar">
        <el-menu
            class="sidebar-el-menu"
            :default-active="onRoutes"
            :collapse="collapse"
            background-color="#324157"
            text-color="#bfcbd9"
            active-text-color="#20a0ff"
            unique-opened
            router
        >
            <template v-for="item in items">
                <template v-if="item.subs">
                    <el-submenu :index="item.index" :key="item.index">
                        <template slot="title">
                            <i :class="item.icon"></i>
                            <span slot="title">{{ item.title }}</span>
                        </template>
                        <template v-for="subItem in item.subs">
                            <el-submenu
                                v-if="subItem.subs"
                                :index="subItem.index"
                                :key="subItem.index"
                            >
                                <template slot="title">{{ subItem.title }}</template>
                                <el-menu-item
                                    v-for="(threeItem,i) in subItem.subs"
                                    :key="i"
                                    :index="threeItem.index"
                                >{{ threeItem.title }}</el-menu-item>
                            </el-submenu>
                            <el-menu-item
                                v-else
                                :index="subItem.index"
                                :key="subItem.index"
                            >{{ subItem.title }}</el-menu-item>
                        </template>
                    </el-submenu>
                </template>
                <template v-else>
                    <el-menu-item :index="item.index" :key="item.index">
                        <i :class="item.icon"></i>
                        <span slot="title">{{ item.title }}</span>
                    </el-menu-item>
                </template>
            </template>
        </el-menu>
    </div>
</template>

<script>
import bus from '../common/bus';
export default {
    data() {
        return {
            collapse: false,
            // is_manager: false,
        };
    },
    computed: {
        items: ()=>{
            let rawDynamicItem = [
                {
                    icon: 'el-icon-lx-home',
                    index: 'dashboard',
                    title: '系统首页'
                },
                {
                    icon: 'el-icon-user',
                    index: 'user_info',
                    title: '个人信息'
                },
                {
                    icon: 'el-icon-lx-copy',
                    index: 'TravellerAdmin1',
                    title: '旅客管理',
                },
                {
                    icon: 'el-icon-lx-cascades',
                    index: 'business_handle',
                    title: '业务办理'
                },
                {
                    icon: 'el-icon-pie-chart',
                    index: 'bill_search',
                    title: '账单查询'
                },
                {
                    icon: 'el-icon-lx-calendar',
                    index: '3',
                    title: '用户信息管理',
                    auth: true,
                    subs: [
                        {
                            index: 'TravellerAdmin2',
                            title: '旅客管理',
                            auth: true
                        },
                        {
                            index: 'SalesmanAdmin',
                            title: '业务员管理',
                            auth: true
                        },
                        {
                            index: 'CompanyAdmin',
                            title: '分公司管理',
                            auth: true
                        }
                    ]
                },
                {
                    icon: 'el-icon-pie-chart',
                    index: '4',
                    title: '活动路线管理',
                    auth: true,
                    subs: [
                        {
                            index: 'FlightAdmin',
                            title: '航班管理',
                            auth: true
                        },
                        {
                            index: 'HotelAdmin',
                            title: '酒店管理',
                            auth: true
                        },
                        {
                            index: 'FoodAdmin',
                            title: '美食管理',
                            auth: true
                        },
                        {
                            index: 'ScenicAdmin',
                            title: '景点管理',
                            auth: true
                        },
                        {
                            index: 'RouteAdmin',
                            title: '路线管理',
                            auth: true
                        },
                        {
                            index: 'ActivityAdmin',
                            title: '活动管理',
                            auth: true
                        },
                    ]
                },
            ]
            let is_manager = localStorage.getItem("is_manager") === "true";
            let dynamicItem = []
            for(let index in rawDynamicItem){
                if(rawDynamicItem[index].auth){
                    if(is_manager){
                        dynamicItem.push(rawDynamicItem[index]);
                    }
                }else{
                    dynamicItem.push(rawDynamicItem[index]);
                }
            }
            return dynamicItem;
        },
        onRoutes() {
            return this.$route.path.replace('/', '');
        }
    },
    created() {
        // 通过 Event Bus 进行组件间通信，来折叠侧边栏
        bus.$on('collapse', msg => {
            this.collapse = msg;
            bus.$emit('collapse-content', msg);
        });
        // this.is_manager = localStorage.getItem("is_manager") === "true";
        // console.log(this.is_manager);
    }
};
</script>

<style scoped>
.sidebar {
    display: block;
    position: absolute;
    left: 0;
    top: 70px;
    bottom: 0;
    overflow-y: scroll;
}
.sidebar::-webkit-scrollbar {
    width: 0;
}
.sidebar-el-menu:not(.el-menu--collapse) {
    width: 250px;
}
.sidebar > ul {
    height: 100%;
}
</style>
