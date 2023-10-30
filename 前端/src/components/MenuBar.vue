<template>
    <div>
        <el-container style="    position: absolute;
    left: 20px;
    right: 0px;
    bottom: 0px;
    top: 62px;
    overflow: hidden;">
            <el-aside width="180px" style="overflow: hidden;">
                <el-row style="height: 100%;">
                    <transition name="el-fade-in">
                        <el-menu :default-active="$route.path" style="height: 100%;" router>
                            <template v-for="(item, index) in menus">
                                <el-submenu :index="index + ''">
                                    <template slot="title">
                                        <i :class="item.iconCls"></i>
                                        <span style="font-weight:bold;" slot="title">{{ item.meta.title }}</span>
                                    </template>
                                    <el-menu-item v-for="child in item.children" :index="child.path" :key="child.path"
                                        :popper-class="popperClass">
                                        <i :class="child.iconCls"></i>
                                        <span>{{ child.meta.title }}</span>
                                    </el-menu-item>
                                </el-submenu>
                            </template>
                        </el-menu>
                    </transition>
                </el-row>
            </el-aside>
            <el-main>
                <div style="height:20px;padding-bottom: 10px;">
                    <el-breadcrumb separator-class="el-icon-arrow-right">
                        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                        <el-breadcrumb-item>{{ $route.matched[1].meta.title }}
                        </el-breadcrumb-item>
                        <el-breadcrumb-item>{{ $route.meta.title }}</el-breadcrumb-item>
                    </el-breadcrumb>
                </div>
                <el-card style="height:calc(100%+1px)">
                    <router-view></router-view>
                </el-card>
            </el-main>
        </el-container>
    </div>
</template>
<script>
import HeaderBar from './HeaderBar'
export default {
    name: 'Base',
    components: {
        HeaderBar,
    },
    data: function () {
        return {
            isCollapse: false,
            showPath: '',
            popperClass: '{  font-size: 15px;}',
            menus: this.$router.options.routes[9].children.filter(i => i.meta && i.meta.roleId && i.meta.roleId.includes(this.$store.state.store.roleId)),
        }
    },
    methods: {
        //判断session中是否存在数据，存在将showname置为true，否则false
        state() {
            console.log(this.$store.state)
            if (this.$store.state.store.username) {
                this.showname = true
                this.username = this.$store.state.store.username
            } else {
                this.showname = false
            }
        },
    },
}
</script>
<style scoped>
.main {
    position: absolute;
    width: 100%;
    height: 100%;
}
</style>